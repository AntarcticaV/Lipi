package com.example.vidoechat.NewWork
import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.ImageFormat
import android.graphics.SurfaceTexture
import android.hardware.camera2.*
import android.media.AudioFormat
import android.media.AudioRecord
import android.media.MediaRecorder
import android.media.Image
import android.media.ImageReader
import android.util.Size
import android.view.Surface
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.core.app.ActivityCompat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.*
import okhttp3.Request
import okio.ByteString
import java.nio.ByteBuffer
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class TestWebClass(private val context: Context) {
    private var webSocket: WebSocket? = null
    private val executor: ExecutorService = Executors.newSingleThreadExecutor()
    private var isStreaming: Boolean = false
    private var cameraDevice: CameraDevice? = null
    private lateinit var imageReader: ImageReader
    private lateinit var cameraCaptureSession: CameraCaptureSession
    private lateinit var captureRequestBuilder: CaptureRequest.Builder
    private lateinit var audioRecord: AudioRecord
    private var audioBufferSize: Int = 0
    private var audioBuffer: ByteArray? = null
    var isStreamingStarted: MutableState<Boolean> = mutableStateOf(false)

    fun connect(url: String, context: Context) {
        val client = OkHttpClient()
        val request = Request.Builder().url(url).build()

        webSocket = client.newWebSocket(request, object : WebSocketListener() {
            override fun onOpen(webSocket: WebSocket, response: Response) {
                // Захват видео и звука и отправка потокового видео со звуком начинается при успешном подключении WebSocket
                startStreaming(context)
            }

            override fun onMessage(webSocket: WebSocket, text: String) {
                // Обработка полученных текстовых сообщений (если нужно)
            }

            override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
                // Пропускаем получение аудио данных, так как мы передаем видео и аудио
            }

            override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {

            }

            override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
                stopStreaming()
                webSocket.close(1000, null)

            }
        })
    }

    @SuppressLint("MissingPermission")
    public fun startStreaming(context: Context) {
        isStreaming = true
        isStreamingStarted.value = true

        // Используем CameraManager для получения доступа к камере
        val cameraManager = context.getSystemService(Context.CAMERA_SERVICE) as CameraManager
        val cameraId = cameraManager.cameraIdList[0] // Первая доступная камера

        // Установка ImageReader для получения видеофреймов
        val cameraCharacteristics = cameraManager.getCameraCharacteristics(cameraId)
        val videoSize = chooseVideoSize(cameraCharacteristics)
        imageReader = ImageReader.newInstance(
            videoSize.width, videoSize.height,
            ImageFormat.YUV_420_888, 2
        )

        // Открытие камеры и создание сеанса захвата
        cameraManager.openCamera(cameraId, object : CameraDevice.StateCallback() {
            override fun onOpened(camera: CameraDevice) {
                cameraDevice = camera

                val surfaces = listOf(
                    imageReader.surface
                )

                camera.createCaptureSession(surfaces, object : CameraCaptureSession.StateCallback() {
                    override fun onConfigured(session: CameraCaptureSession) {
                        cameraCaptureSession = session
                        startCaptureRequest()
                    }

                    override fun onConfigureFailed(session: CameraCaptureSession) {

                    }
                }, null)
            }

            override fun onDisconnected(camera: CameraDevice) {
                stopStreaming()
                camera.close()
                cameraDevice = null
            }

            override fun onError(camera: CameraDevice, error: Int) {
                stopStreaming()
                camera.close()
                cameraDevice = null

            }
        }, null)

        // Запуск захвата звука с микрофона
        startAudioCapture(context)
    }

    private fun startCaptureRequest() {
        val captureRequestBuilder =
            cameraDevice?.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW)?.apply {
                addTarget(imageReader.surface)
                set(CaptureRequest.CONTROL_MODE, CameraMetadata.CONTROL_MODE_AUTO)
            }

        this.captureRequestBuilder = captureRequestBuilder!!
        imageReader.setOnImageAvailableListener(
            { reader ->
                val image: Image = reader.acquireLatestImage()
                val buffer: ByteBuffer = image.planes[0].buffer
                val data = ByteArray(buffer.remaining())
                buffer.get(data)
                image.close()

                if (isStreaming) {
                    webSocket?.send(ByteString.of(*data))
                }
            },
            null
        )

        cameraCaptureSession.setRepeatingRequest(
            captureRequestBuilder.build(),
            null,
            null
        )
    }

    @SuppressLint("UnsafeExperimentalUsageError")
    private fun startAudioCapture(context: Context) {
        val audioSource = MediaRecorder.AudioSource.MIC
        val sampleRate = 44100
        val channelConfig = AudioFormat.CHANNEL_IN_MONO
        val audioFormat = AudioFormat.ENCODING_PCM_16BIT
        audioBufferSize = AudioRecord.getMinBufferSize(sampleRate, channelConfig, audioFormat)
        audioBuffer = ByteArray(audioBufferSize)

        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.RECORD_AUDIO
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        audioRecord = AudioRecord(audioSource, sampleRate, channelConfig, audioFormat, audioBufferSize)

        audioRecord.startRecording()

        executor.submit {
            while (isStreaming) {
                val bytesRead = audioRecord.read(audioBuffer!!, 0, audioBufferSize)

                if (bytesRead > 0) {
                    val audioData = audioBuffer?.copyOf(bytesRead)
                    webSocket?.send(ByteString.of(*audioData!!))
                }
            }
        }
    }

    fun stopStreaming() {
        isStreaming = false
        isStreamingStarted.value = false

        audioRecord.stop()
        audioRecord.release()

        cameraCaptureSession.stopRepeating()
        cameraCaptureSession.close()
        cameraDevice?.close()
    }

    fun disconnect() {
        webSocket?.close(1000, null)
        executor.shutdown()
    }

    private fun chooseVideoSize(characteristics: CameraCharacteristics): Size {
        val map = characteristics.get(
            CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP
        ) ?: throw IllegalStateException("Cannot get available preview/video sizes")
        val sizes = map.getOutputSizes(SurfaceTexture::class.java)

        // Выбираем подходящий размер видео (в данном случае выбирается наибольший доступный размер)
        return sizes.maxByOrNull { it.width * it.height }
            ?: throw IllegalStateException("No available preview/video sizes")
    }
}

interface VideoAudioWebSocketListener {
    fun onWebSocketFailure(t: Throwable)
    fun onWebSocketClosed()
    fun onCameraFailure()
}
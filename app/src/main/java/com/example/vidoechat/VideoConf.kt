package com.example.vidoechat

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.media.AudioFormat
import android.media.AudioManager
import android.media.AudioRecord
import android.media.AudioTrack
import android.media.MediaRecorder
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.lifecycle.lifecycleScope
import com.example.vidoechat.NewWork.TestWebClass
import com.example.vidoechat.NewWork.VideoAudioWebSocketListener
import com.example.vidoechat.NewWork.websocket_audio
import com.example.vidoechat.ui.theme.VidoeChatTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class VideoConf : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

//
            }
        lifecycleScope.launch(Dispatchers.IO) {
            val webSocket: websocket_audio =
                websocket_audio(("ws://109.174.29.40:8000/user/ws_audio"))
            webSocket.connect()
            toggleRecording(this@VideoConf, webSocket, true)
            togglePlayback(this@VideoConf, webSocket,true)
        }
    }

private fun toggleRecording(context: Context, webSocketClient: websocket_audio, isRecording: Boolean) {
    val audioRecord: AudioRecord
    val bufferSize: Int =1024
    val sampleRateInHz = 44100
    val channelConfig = AudioFormat.CHANNEL_IN_MONO
    val audioFormat = AudioFormat.ENCODING_PCM_16BIT


    if (ActivityCompat.checkSelfPermission(
            this@VideoConf,
            Manifest.permission.RECORD_AUDIO
        ) != PackageManager.PERMISSION_GRANTED
    ) {
        return
    }
    audioRecord = AudioRecord(
        MediaRecorder.AudioSource.MIC,
        sampleRateInHz,
        channelConfig,
        audioFormat,
        bufferSize
    )

        // Настройка параметров записи аудио


        // Запуск записи аудио
    audioRecord.startRecording()

    // Отправка записанного звука через WebSocket
    lifecycleScope.launch(Dispatchers.IO) {

        while (isRecording) {
            val buffer = ByteArray(bufferSize)
            val bytesRead = audioRecord.read(buffer, 0, bufferSize)

            webSocketClient.sendMessage(buffer.copyOfRange(0, bytesRead))

        }
    }
}

    private suspend fun togglePlayback(context: Context, webSocketClient: websocket_audio, isPlaying: Boolean) {

        val bufferSize: Int
        val sampleRateInHz = 44100
        val channelConfig = AudioFormat.CHANNEL_OUT_MONO
        val audioFormat = AudioFormat.ENCODING_PCM_16BIT
        bufferSize = AudioTrack.getMinBufferSize(
            sampleRateInHz,
            channelConfig,
            audioFormat
        )
        val audioTrack: AudioTrack = AudioTrack(
            AudioManager.STREAM_MUSIC,
            sampleRateInHz,
            channelConfig,
            audioFormat,
            bufferSize,
            AudioTrack.MODE_STREAM
        )
        if (isPlaying) {
            // Настройка параметров воспроизведения аудио



            audioTrack.play()
            // Воспроизведение звука, полученного через WebSocket
            lifecycleScope.launch(Dispatchers.IO) {
                val byteArray = webSocketClient.getByteArrayState()
//                Log.d("GET", byteArray.toString())
            if (byteArray != null)
                withContext(Dispatchers.Main){
                    audioTrack.write(byteArray, 0, byteArray.size)
            }
            }


        } else {
            // Остановка воспроизведения аудио
            audioTrack.stop()
            audioTrack.release()
        }
    }
}


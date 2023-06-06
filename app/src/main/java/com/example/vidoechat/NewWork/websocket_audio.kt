package com.example.vidoechat.NewWork

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.*
import okhttp3.Request
import okio.ByteString
import okio.ByteString.Companion.toByteString

class websocket_audio(private val url: String) {
    private var webSocket: WebSocket? = null
    private var messageState: MutableState<String> = mutableStateOf("")
    private var byteArrayState: MutableState<ByteArray?> = mutableStateOf(null)

    suspend fun connect() {
        val request = Request.Builder().url(url).build()
        val listener = object : WebSocketListener() {
            override fun onOpen(webSocket: WebSocket, response: Response) {
                // WebSocket connection opened
            }

            override fun onMessage(webSocket: WebSocket, text: String) {
                // Received a text message
//                messageState.value = text
            }

            override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
                // Received a binary message
                val byteArray = bytes.toByteArray()
                Log.d("OFF2", bytes.toString())
                byteArrayState.value = byteArray
            }

            override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
                // WebSocket connection closed
                Log.d("Close", reason)
            }

            override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
                // WebSocket connection failure
                Log.d("GGWP", t.toString()+";"+response.toString())
            }
        }

        val client = OkHttpClient()

        webSocket = client.newWebSocket(request, listener)
    }

    fun sendMessage(message: String) {
        webSocket?.send(message)
    }

    suspend fun sendMessage(byteArray: ByteArray) {
        webSocket?.send(byteArray.toByteString())
    }

    fun getMessageState(): MutableState<String> {
        return messageState
    }

    suspend fun getByteArrayState(): ByteArray? {
        return byteArrayState.value
    }


    suspend fun disconnect() {
        webSocket?.cancel()
        webSocket = null
    }
}
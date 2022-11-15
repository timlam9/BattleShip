package com.timgt.battleship

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket
import okhttp3.WebSocketListener

interface BattleshipClient {

    val messages: SharedFlow<GameState>

    suspend fun findOpponent()

    suspend fun sendMessage(message: String)
}

class RemoteBattleshipClient : BattleshipClient {

    private var coroutineScope: CoroutineScope? = null
    private val client: OkHttpClient = OkHttpClient()
    private var webSocket: WebSocket? = null

    private val _messages = MutableSharedFlow<GameState>()
    override val messages: SharedFlow<GameState> = _messages

    override suspend fun findOpponent() {
        coroutineScope = CoroutineScope(Dispatchers.IO)

        val request: Request = Request.Builder().url(WEB_SOCKET_URL).build()
        webSocket = client.newWebSocket(request, object : WebSocketListener() {
            override fun onMessage(webSocket: WebSocket, text: String) {
                coroutineScope?.launch {
                    Log.d("TAGARA", text)
                    val gameState = Json.decodeFromString<GameState>(text)
                    _messages.emit(gameState)
                }
                super.onMessage(webSocket, text)
            }
        })
        client.dispatcher.executorService.shutdown()

        sendMessage("Connect player with id: 1")
    }

    override suspend fun sendMessage(message: String) {
        webSocket?.send(message)
    }

    companion object {

        private const val WEB_SOCKET_URL = "ws://192.168.1.101:8080/battleship"
        private const val WEB_SOCKET_CLOSE_CODE = 1000
    }
}

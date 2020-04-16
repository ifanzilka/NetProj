package ru.fanzil.networking

import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.net.ServerSocket

class Server {
    private val ss: ServerSocket

    init {
        ss = ServerSocket(5703)//port
        start()

    }

    private fun start() {
        val s = ss.accept()//listen port (GET SOCKET)  val  s-SOCKET

        val br = BufferedReader(
            InputStreamReader(s.getInputStream())
        )
        println(br.readLine())
        print("Сщттусештщ")
        s.close()
    }
}

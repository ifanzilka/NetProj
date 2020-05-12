package ru.fanzil.networking

import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.ServerSocket
import java.net.Socket
import kotlin.concurrent.thread

class Server private constructor(){

    private val ss: ServerSocket
    private var stop = false
    private val connectedClient = mutableListOf<ConnectedClient>()

    companion object {                          //static
        private val PORT = 5703
        private val srv: Server = Server()

        fun getInstance(): Server {
            return srv
        }
    }

    inner class ConnectedClient(
        val connectedClients: MutableList<ConnectedClient>,
        skt: Socket,
        val id: Int){

        private val cmn: Communicator

        init{
            cmn = Communicator(skt)
            cmn.addDataRecievedListener(::dataReceived)
            cmn.start()
            sendStatus(id%2==0)
        }

        private fun dataReceived(data: String){
            //Формат сообщений:  команда=данные
            val vls = data.split("=", limit = 2)
            if (vls.size == 2){
                when (vls[0]){
                    "pos" -> setPosition(data)
                }
            }
        }

        private fun setPosition(data: String){
            var partner: ConnectedClient? = null
            val thisInd = connectedClients.indexOf(this)
            if (id%2==0){
                if (
                    connectedClients.size>=thisInd+2 &&
                    connectedClients[thisInd+1].id%2 != 0
                )
                    partner = connectedClients[thisInd+1]
            } else {
                if (connectedClients[thisInd-1].id%2 == 0)
                    partner = connectedClients[thisInd-1]
            }
            partner?.sendPosition(data)
        }

        private fun sendPosition(data: String){
            if (cmn.isAlive){
                cmn.sendData(data)
            }
        }

        private fun sendStatus(status: Boolean) {
            if (cmn.isAlive){
                cmn.sendData("status=$status")
            }
        }
    }

    init{
        ss = ServerSocket(PORT)
        while (!stop) {
            acceptClient()
        }
    }

    private fun acceptClient() {
        println("Waiting client")
        val s = ss.accept()
        println("New client connected")
        if (s!=null) {
            val id =
                if (connectedClient.isEmpty()) 0 else connectedClient.last().id+1
            connectedClient.add(ConnectedClient(connectedClient, s, id))
        }
    }
}
package ru.fanzil.networking

import GameData
import ru.fanzil.components.Status
import java.net.Socket

class Client {

    val s: Socket
    private var cmn: Communicator
    var stop: Boolean = false
    val gameData = GameData.getInstance()
    val isAlive
        get() = !stop && s.isConnected

    companion object{
        var port = 5703
        var serverAddress = "localhost"
    }

    init{
        s = Socket(serverAddress, port)
        cmn = Communicator(s)
        cmn.addDataRecievedListener(::dataReceived)
        cmn.start()
        gameData.addSetPositionListener(::sendAction)
    }

    private fun dataReceived(data: String){
        val vls = data.split("=", limit=2)
        if (vls.size == 2){
            when (vls[0]){
                "status" -> acceptStatus(vls[1])
                "pos" -> acceptPosition(vls[1])
            }
        }
    }

    private fun acceptStatus(v: String){
        if (v=="true"){
            gameData.clickRole = Status.X
            gameData.clickable = true
        } else {
            gameData.clickRole = Status.O
            gameData.clickable = false
        }
    }

    private fun acceptPosition(pos: String){
        val rc = pos.split(";", limit = 2).map {
            try{
                it.toInt()
            } catch (e: NumberFormatException) {-1}
        }
        if (rc.size == 2){
            if (rc[0]!=-1 && rc[1]!=-1){
                gameData.lastGotPos = Pair(rc[0], rc[1])
            }
        }
    }

    private fun sendAction(row: Int, col: Int) {
        if (cmn.isAlive){
            val v = "pos=$row;$col"
            cmn.sendData(v)
        }
    }

}
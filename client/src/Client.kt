package ru.fanzil.networking


import ru.fanzil.MainWindow
import java.io.PrintWriter
import java.net.Socket

class Client {
  //  private val s: Socket
    init{
    //    s = Socket("localhost", 5703)
      //  start()

      val wnd=MainWindow()
      wnd.isVisible=true
    }

    /*private fun start() {
        val pw = PrintWriter(s.getOutputStream())
        pw.println("Привет!!!")
        pw.flush()
        s.close()
    }*/


}
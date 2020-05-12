package ru.fanzil

import ru.fanzil.components.GameField
import ru.fanzil.networking.Client
import java.awt.Color
import java.awt.Dimension
import javax.swing.GroupLayout
import javax.swing.JButton
import javax.swing.JFrame
import javax.swing.WindowConstants

class MainWindow : JFrame(){
    val field: GameField                        //class Game
    val btnStart: JButton
    var client: Client? = null                  //class client
    val gameData = GameData.getInstance()

    init{
        defaultCloseOperation = WindowConstants.EXIT_ON_CLOSE
        minimumSize = Dimension(500, 500)           //size
        field = GameField(gameData)
        field.background = Color.WHITE
        btnStart = JButton()
        btnStart.text = "START"
        btnStart.addActionListener {
            client = Client()
            //if (client!=null) field.addOnClickListener(::cellClicked)
        }

        val gl = GroupLayout(contentPane)
        layout = gl
        gl.setHorizontalGroup(
            gl.createSequentialGroup()
                .addGap(8)
                .addGroup(
                    gl.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addComponent(field, 400, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE)
                        .addComponent(btnStart, 150, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
                )
                .addGap(8)
        )
        gl.setVerticalGroup(
            gl.createSequentialGroup()
                .addGap(8)
                .addComponent(field, 400, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE)
                .addGap(8)
                .addComponent(btnStart, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
                .addGap(8)
        )
        pack()
    }


}

fun main() {
    val wnd = MainWindow()
    wnd.isVisible = true
}
package ru.fanzil.components

import java.awt.Graphics
import java.awt.event.ComponentAdapter
import java.awt.event.ComponentEvent
import javax.swing.JPanel

class GameField : JPanel(){

    val cells: Array<GameCell>

    init{
        GameCell.fieldHeight = height
        GameCell.fieldWidth = width

        addComponentListener(object : ComponentAdapter(){
            override fun componentShown(e: ComponentEvent?) {
                super.componentShown(e)
                GameCell.g = graphics
                GameCell.fieldHeight = height
                GameCell.fieldWidth = width
            }

            override fun componentResized(e: ComponentEvent?) {
                GameCell.fieldHeight = height
                GameCell.fieldWidth = width
            }
        })

        cells = Array<GameCell>(GameCell.ROW_COUNT * GameCell.COL_COUNT)
        {
            GameCell(
                it / GameCell.COL_COUNT,
                it % GameCell.COL_COUNT
            )
        }
    }

    override fun paint(g: Graphics?) {
        super.paint(g)
        GameCell.g = g
        cells.forEach { it.show() }
    }

}

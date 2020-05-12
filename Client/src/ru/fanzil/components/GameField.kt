package ru.fanzil.components

import GameData
import java.awt.Graphics
import java.awt.event.ComponentAdapter
import java.awt.event.ComponentEvent
import javax.swing.JPanel

class GameField(val gameData: GameData) : JPanel(){

    /*fun addOnClickListener(l: (Int, Int)->Unit) =
        GameCell.addOnClickListener(l)

    fun removeOnClickListener(l: (Int, Int)->Unit) =
        GameCell.removeOnClickListener(l)*/

    val cells: Array<GameCell>

    init{
        layout = null
        GameCell.fieldHeight = height
        GameCell.fieldWidth = width
        GameData.getInstance().addGotPositionListener(::onExternalClick)
        cells = Array(GameCell.ROW_COUNT * GameCell.COL_COUNT)
                               {
                                   GameCell(
                                       it / GameCell.COL_COUNT,
                                       it % GameCell.COL_COUNT
                                   )
                               }
        cells.forEach { add(it) }

        addComponentListener(object : ComponentAdapter(){
            override fun componentShown(e: ComponentEvent?) {
                super.componentShown(e)
                GameCell.fieldHeight = height
                GameCell.fieldWidth = width
                cells.forEach { it.updateBounds() }
            }

            override fun componentResized(e: ComponentEvent?) {
                GameCell.fieldHeight = height
                GameCell.fieldWidth = width
                cells.forEach { it.updateBounds() }
            }
        })

    }

    private fun onExternalClick(row: Int, col: Int) {
        val cid = getCellId(row, col)
        if (cid >= 0 && cid < cells.size && cells[cid].status == Status.NONE){
            cells[cid].status =
                if (GameData.getInstance().clickRole == Status.X) Status.O
                else Status.X
            GameData.getInstance().clickable = true
        }
    }

    private fun getCellId(row: Int, col: Int): Int =
        row * GameCell.COL_COUNT + col
}
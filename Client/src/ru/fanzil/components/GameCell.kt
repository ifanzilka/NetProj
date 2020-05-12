package ru.fanzil.components

import java.awt.Color
import java.awt.Graphics
import java.awt.event.*
import javax.swing.JPanel
import kotlin.math.min


class GameCell(val row: Int, val col: Int) : JPanel(){
    companion object{
        var fieldWidth: Int = 0
        var fieldHeight: Int = 0
        val ROW_COUNT = 3
        val COL_COUNT = 3
        val FOREGROUND = Color.DARK_GRAY

        /*private val onClick: MutableList<(Int, Int)->Unit> = mutableListOf()

        fun addOnClickListener(l: (Int, Int)->Unit){
            onClick.add(l)
        }
        fun removeOnClickListener(l: (Int, Int)->Unit){
            onClick.remove(l)
        }*/
    }

    private val gd = GameData.getInstance()

    var status: Status = Status.NONE
        set(value){
            field = value
            repaint()
        }

    val size: Int
        get() = min(fieldWidth / COL_COUNT, fieldHeight / ROW_COUNT) - 2

    val xShift: Int
        get() = (fieldWidth - COL_COUNT*size)/2 + size*col
    val yShift: Int
        get() = (fieldHeight - ROW_COUNT*size)/2 + size*row

    init{
        addMouseListener(object: MouseAdapter(){
            override fun mouseClicked(e: MouseEvent?) {
                super.mouseClicked(e)
                if (gd.clickable && status == Status.NONE) {
                    gd.clickable = false
                    status = gd.clickRole
                    gd.lastSetPos = Pair(row, col)
                    //onClick.forEach { it.invoke(row, col) }
                }
            }
        })
    }

    fun updateBounds(){
        setBounds(xShift, yShift, size+2, size+2)
    }

    private fun draw(g: Graphics){
        if (fieldHeight==0 || fieldWidth == 0) return
        g.color = FOREGROUND
        g.drawRect(0, 0, size, size)
        g.color = status.color
        when (status){
            Status.X -> {
                g.drawLine(1, 1, size, size)
                g.drawLine(size, 1, 1, size)
            }
            Status.O -> {
                g.drawOval(1, 1, size, size)
            }
            Status.NONE -> {
                g.fillRect(2, 2, size-2, size-2)
            }
        }
    }

    override fun paint(g: Graphics){
        super.paint(g)
        draw(g)
    }

}

enum class Status(val color: Color) {
    X(Color.BLUE),
    O(Color.RED),
    NONE(Color.GRAY)
}

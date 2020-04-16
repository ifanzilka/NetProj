package ru.fanzil.components

import java.awt.Color
import java.awt.Graphics
import kotlin.math.min


class GameCell(val row: Int, val col: Int) {
    companion object{   //static (one object!)
        var g: Graphics? = null
        var fieldWidth: Int = 0
        var fieldHeight: Int = 0
        val ROW_COUNT = 3
        val COL_COUNT = 3
        val FOREGROUND = Color.DARK_GRAY

    }

    var status: Status = Status.NONE
        set(value){
            field = value
            show()
        }

    val size: Int
        get() = min(fieldWidth / COL_COUNT, fieldHeight / ROW_COUNT)

    val xShift: Int
        get() = (fieldWidth - COL_COUNT*size)/2 + size*col
    val yShift: Int
        get() = (fieldHeight - ROW_COUNT*size)/2 + size*row

    fun show(){
        if (fieldHeight==0 || fieldWidth == 0) return
        g?.color = FOREGROUND
        g?.drawRect(xShift, yShift, size, size)
        g?.color = status.color
        when (status){
            Status.X -> {
                g?.drawLine(xShift, yShift, xShift+size, yShift+size)
                g?.drawLine(xShift+size, yShift, xShift, yShift+size)
            }
            Status.O -> {
                g?.drawOval(xShift, yShift, size, size)
            }
            Status.NONE -> {
                g?.fillRect(xShift+2, yShift+2, size-3, size-3)
            }
        }
    }
}

enum class Status(val color: Color) {
    X(Color.BLUE),
    O(Color.RED),
    NONE(Color.GRAY)
}
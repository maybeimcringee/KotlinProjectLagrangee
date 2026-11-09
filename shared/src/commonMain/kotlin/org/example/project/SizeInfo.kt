package org.example.project

import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.drawscope.DrawScope

class SizeInfo(
     xMin: Double,  xMax: Double,
     yMin: Double,  yMax: Double,
     width: Float,  height: Float
)
{
    var xMin by mutableStateOf(xMin)
    var xMax by mutableStateOf(xMax)
    var yMin by mutableStateOf(yMin)
    var yMax by mutableStateOf(yMax)
    var width by mutableStateOf(width)
    var height by mutableStateOf(height)

    constructor(
        xMin: Double, xMax: Double,
        yMin: Double, yMax: Double,
        drawScope: DrawScope
    ):
            this(xMin, xMax, yMin, yMax,
                drawScope.size.width, drawScope.size.height
            )


    constructor(
        xMin: Double, xMax: Double,
        yMin: Double, yMax: Double):
        this(xMin, xMax, yMin, yMax,
              0f, 0f )

    fun toCartesianPoint(sp:ScreenPoint): CartesianPoint {
        var xcount = width / (xMax - xMin)
        var ycount = height / (yMax - yMin)
        return CartesianPoint(sp.x / xcount +xMin, yMax - sp.y /ycount)
    }
    fun toScreenPoint(cp: CartesianPoint): ScreenPoint {
        var countx = (xMax - xMin) / width
        var county = (yMax - yMin) / height
        return ScreenPoint( ((cp.x - xMin)/countx).toFloat(), ((yMax - cp.y)/county).toFloat())
    }
    fun changeX(){
        val newDifference = width* (yMax - yMin)/height
        val needToCutOut = ((xMax- xMin) - newDifference) /2
        xMin += needToCutOut
        xMax -= needToCutOut
    }
    fun changeY(){
        val newDifference = height* (xMax - xMin)/width
        val needToCutOut = ((yMax- yMin) - newDifference) /2
        yMin += needToCutOut
        yMax -= needToCutOut
    }
}
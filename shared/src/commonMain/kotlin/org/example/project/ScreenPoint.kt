package org.example.project

import androidx.compose.ui.geometry.Offset

class ScreenPoint{val x: Float
                     val y: Float
                     constructor(cp: CartesianPoint, sizeInfo: SizeInfo) {
                         x = sizeInfo.toScreenPoint(cp).x
                         y = sizeInfo.toScreenPoint(cp).y
                     }
    constructor(point: Offset) {
        x = point.x
        y = point.y
    }
    constructor(x: Float, y: Float) {
        this.x = x
        this.y = y
    }
    fun toCartesianPoint(sizeInfo: SizeInfo) =
        sizeInfo.toCartesianPoint(this)
    fun toOffset(): Offset {
        return Offset(x,y)
    }
}
package org.example.project

import kotlinx.serialization.descriptors.PrimitiveKind

class CartesianPoint{val x:Double
                     val y:Double
                     constructor(sp: ScreenPoint, sizeInfo: SizeInfo) {
                         x = sizeInfo.toCartesianPoint(sp).x
                         y = sizeInfo.toCartesianPoint(sp).y
                     }
    constructor(x: Double, y: Double){
     this.x = x
     this.y = y
    }
    fun toScreenPoint(sizeInfo: SizeInfo): ScreenPoint {
        return sizeInfo.toScreenPoint(this)
    }
}
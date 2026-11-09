package org.example.project

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.onPointerEvent
import kotlin.math.cos
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.ui.tooling.preview.Preview
@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
@Preview

fun IntegrationApp() {
    val xMin: Double = -20.0
    val xMax: Double = 2.0
    val yMin: Double = -2.0
    val yMax: Double = 2.0
    val sizeInfo = remember{ SizeInfo(xMin, xMax, yMin, yMax,)}
    Row {
        Column( modifier = Modifier.weight(10f)){
            Canvas(modifier = Modifier.fillMaxSize()
                .background(Color.White))
            {
                sizeInfo.width = this.size.width
                sizeInfo.height = this.size.height
                screenPointFunction(sizeInfo, 0f)
                drawIntegrationAxis(this, sizeInfo)
                drawFunction(this, sizeInfo)
                //drawLeftRectangles(this, sizeInfo)
                //drawRightRectangles(this, sizeInfo)
                //drawCenteredRectangles(this, sizeInfo)
                //drawTrapeze(this, sizeInfo)
            }

        }
        Column (modifier = Modifier.weight(3f)){
            resizeIntegration(sizeInfo)
        }
    }
}

fun drawIntegrationAxis(drawScope: DrawScope, sizeInfo: SizeInfo) {
    val dp0sp = CartesianPoint(0.0, 0.0).toScreenPoint(sizeInfo)
    drawScope.drawLine(
        color = Color.Black,
        start = Offset(0f, dp0sp.y),
        end = Offset(sizeInfo.width, dp0sp.y),
    )
    drawScope.drawLine(
        color = Color.Black,
        start = Offset( dp0sp.x, 0f),
        end = Offset(dp0sp.x, sizeInfo.height),
    )

}


fun drawFunction(drawScope: DrawScope, sizeInfo: SizeInfo) {
    for (i in 1 until drawScope.size.width.toInt()) {
        drawScope.drawLine(
            color = Color.Black,
            start = screenPointFunction(sizeInfo, i.toFloat()),
            end = screenPointFunction(sizeInfo, (i+1).toFloat()),

            )

    }
}

fun f(x: Double)= cos(x)
fun screenPointFunction(sizeInfo: SizeInfo, spX: Float): Offset {
    val dpX = ScreenPoint(spX, 0f).toCartesianPoint(sizeInfo).x
    val dpY = f(dpX)
    val spY = CartesianPoint(dpX, dpY).toScreenPoint(sizeInfo).y
    return ScreenPoint(spX, spY).toOffset()
}

fun drawLeftRectangles(drawScope: DrawScope, sizeInfo: SizeInfo) {
    val s = 30
    val dp0sp = CartesianPoint(0.0, 0.0).toScreenPoint(sizeInfo)
    for (i in 0 until drawScope.size.width.toInt() step s) {
        val functionPoint = screenPointFunction(sizeInfo, i.toFloat())
        drawScope.drawRect(
            color = Color(red = 0xFF, green = 0x00, blue = 0x00, alpha = 0x88),
            topLeft = functionPoint,
            size = Size(width = s.toFloat(), height = dp0sp.y - functionPoint.y),
        )
    }
}
fun drawRightRectangles(drawScope: DrawScope, sizeInfo: SizeInfo) {
    val s = 30
    val dp0sp = CartesianPoint(0.0, 0.0).toScreenPoint(sizeInfo)
    for (i in 0 until drawScope.size.width.toInt() step s) {
        val functionPoint = screenPointFunction(sizeInfo, i.toFloat())
        drawScope.drawRect(
            color = Color(red = 0xFF, green = 0x00, blue = 0x00, alpha = 0x88),
            topLeft = functionPoint,
            size = Size(width = -s.toFloat(), height = dp0sp.y - functionPoint.y),
        )
    }
}
fun drawCenteredRectangles(drawScope: DrawScope, sizeInfo: SizeInfo) {
    val s = 30
    val dp0sp = CartesianPoint(0.0, 0.0).toScreenPoint(sizeInfo)
    for (i in 0 until drawScope.size.width.toInt() step s) {
        val functionPoint = screenPointFunction(sizeInfo, i.toFloat())
        val centerPoint = ScreenPoint(functionPoint.x-s/2, functionPoint.y)
        drawScope.drawRect(
            color = Color(red = 0xFF, green = 0x00, blue = 0x00, alpha = 0x88),
            topLeft = centerPoint.toOffset(),
            size = Size(width = s.toFloat(), height = dp0sp.y - functionPoint.y),
        )
    }
}
fun drawTrapeze(drawScope: DrawScope, sizeInfo: SizeInfo) {
    val s = 30
    val dp0sp = CartesianPoint(0.0, 0.0).toScreenPoint(sizeInfo)
    for (i in 0 until drawScope.size.width.toInt() step s) {
        val functionPoint = screenPointFunction(sizeInfo, i.toFloat())
        val path = Path().apply {
            moveTo(dp0sp.x, dp0sp.y)
            lineTo(0f, dp0sp.y)
            lineTo(0f, dp0sp.x)
            close()
        }
    }
}


@Composable
fun resizeIntegration(sizeInfo: SizeInfo){
    var lastChangeXorY by remember{ mutableStateOf("X") }
    Row{
        OutlinedTextField(
            value = sizeInfo.xMin.toString(),
            onValueChange = {sizeInfo.xMin = it.toDoubleOrNull() ?: sizeInfo.xMin; lastChangeXorY = "X"},
            modifier = Modifier.weight(1f),

            )
        Text("<= X <= ",
            modifier = Modifier.weight(1f),
        )
        OutlinedTextField(
            value = sizeInfo.xMax.toString(),
            onValueChange = {sizeInfo.xMax = it.toDoubleOrNull() ?: sizeInfo.xMax; lastChangeXorY = "X"},
            modifier = Modifier.weight(1f)
        )
    }
    Row{
        OutlinedTextField(
            value = sizeInfo.yMin.toString(),
            onValueChange = {sizeInfo.yMin = it.toDoubleOrNull() ?: sizeInfo.yMin; lastChangeXorY = "Y"},
            modifier = Modifier.weight(1f),

            )
        Text("<= Y <= ",
            modifier = Modifier.weight(1f),
        )
        OutlinedTextField(
            value = sizeInfo.yMax.toString(),
            onValueChange = {sizeInfo.yMax = it.toDoubleOrNull() ?: sizeInfo.yMax; lastChangeXorY = "Y"},
            modifier = Modifier.weight(1f)
        )
    }
    Row {
        Button(onClick = {
            if (lastChangeXorY == "X") {
                sizeInfo.changeY()
            }
            else{
                sizeInfo.changeX()
            }
        }){
            Text("Масштабировать")
        }
    }
}
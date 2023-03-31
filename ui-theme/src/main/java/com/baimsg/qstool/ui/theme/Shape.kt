package com.baimsg.qstool.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp


val Shapes = QstoolShape()


data class QstoolShape(
    val search: Shape = RoundedCornerShape(12.dp),
    val small: Shape = RoundedCornerShape(4.dp),
    val medium: Shape = RoundedCornerShape(8.dp),
    val large: Shape = RoundedCornerShape(0.dp)
)
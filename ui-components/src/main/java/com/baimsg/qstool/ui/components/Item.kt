package com.baimsg.qstool.ui.components

import androidx.compose.foundation.Indication
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.baimsg.qstool.ui.defaultCommonHorSpace
import com.baimsg.qstool.ui.defaultIndicationColor
import com.baimsg.qstool.ui.defaultTextDescColor
import com.baimsg.qstool.ui.defaultTextMainColor

/**
 * Create by Baimsg on 2023/4/2
 * 对话框常用项目
 * @param title 标题
 * @param detail 描述
 * @param alpha 背景透明度
 * @param background 背景颜色
 * @param indication 按下涟漪颜色
 * @param titleFontSize 标题字体大小
 * @param titleOnlyFontSize 仅显示标题时字体大小
 * @param titleColor 标题颜色
 * @param titleFontWeight 标题字体
 * @param titleFontFamily 标题字体风格
 * @param titleLineHeight 标题行高
 * @param detailFontSize 描述字体大小
 * @param detailColor 描述颜色
 * @param detailFontWeight 描述字体
 * @param detailFontFamily 描述字体风格
 * @param detailLineHeight 描述行高
 * @param minHeight 最小高度
 * @param paddingHor 水平距离
 * @param paddingVer 垂直距离
 * @param gapBetweenTitleAndDetail 标题和描述之间的距离
 * @param accessory 配饰
 * @param drawBehind 绘制在后面
 * @param onClick 点击事件
 **/
@Composable
fun Item(
    title: String,
    detail: String = "",
    alpha: Float = 1f,
    background: Color = Color.Transparent,
    indication: Indication = rememberRipple(color = defaultIndicationColor),
    titleFontSize: TextUnit = 16.sp,
    titleOnlyFontSize: TextUnit = 17.sp,
    titleColor: Color = defaultTextMainColor,
    titleFontWeight: FontWeight = FontWeight.Medium,
    titleFontFamily: FontFamily? = null,
    titleLineHeight: TextUnit = 20.sp,
    detailFontSize: TextUnit = 12.sp,
    detailColor: Color = defaultTextDescColor,
    detailFontWeight: FontWeight = FontWeight.Normal,
    detailFontFamily: FontFamily? = null,
    detailLineHeight: TextUnit = 17.sp,
    minHeight: Dp = 56.dp,
    paddingHor: Dp = defaultCommonHorSpace,
    paddingVer: Dp = 12.dp,
    gapBetweenTitleAndDetail: Dp = 4.dp,
    accessory: @Composable (RowScope.() -> Unit)? = null,
    drawBehind: (DrawScope.() -> Unit)? = null,
    onClick: (() -> Unit)? = null
) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .defaultMinSize(minHeight = minHeight)
        .alpha(alpha)
        .background(background)
        .drawBehind {
            drawBehind?.invoke(this)
        }
        .clickable(
            enabled = onClick != null,
            interactionSource = remember { MutableInteractionSource() },
            indication = indication
        ) {
            onClick?.invoke()
        }
        .padding(horizontal = paddingHor, vertical = paddingVer),
        verticalAlignment = Alignment.CenterVertically) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                color = titleColor,
                modifier = Modifier.fillMaxWidth(),
                fontSize = if (detail.isNotBlank()) titleFontSize else titleOnlyFontSize,
                fontWeight = titleFontWeight,
                fontFamily = titleFontFamily,
                lineHeight = titleLineHeight
            )
            if (detail.isNotBlank()) {
                Text(
                    text = detail,
                    color = detailColor,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = gapBetweenTitleAndDetail),
                    fontSize = detailFontSize,
                    fontWeight = detailFontWeight,
                    fontFamily = detailFontFamily,
                    lineHeight = detailLineHeight
                )
            }

        }
        accessory?.invoke(this)
    }
}
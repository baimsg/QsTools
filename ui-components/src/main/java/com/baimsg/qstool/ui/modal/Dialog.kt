package com.baimsg.qstool.ui.modal

import android.view.View
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.Indication
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.baimsg.qstool.ui.*
import com.baimsg.qstool.ui.components.Item
import com.baimsg.qstool.ui.resources.R

/**
 * Create by Baimsg on 2023/4/2
 */
val DefaultDialogPaddingHor = 20.dp


/**
 * 对话框
 * @param modal 模态
 * @param horEdge 水平距离
 * @param verEdge 垂直距离
 * @param widthLimit 宽度限制
 * @param radius 圆角弧度
 * @param background 背景颜色
 * @param content 内容
 **/
@Composable
fun Dialog(
    modal: IModal,
    horEdge: Dp = defaultCommonHorSpace,
    verEdge: Dp = defaultDialogVerEdgeProtectionMargin,
    widthLimit: Dp = 360.dp,
    radius: Dp = 2.dp,
    background: Color = Color.White,
    content: @Composable (IModal) -> Unit
) {
    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = horEdge, vertical = verEdge),
        contentAlignment = Alignment.Center
    ) {
        var modifier = if (widthLimit < maxWidth) {
            Modifier.width(widthLimit)
        } else {
            Modifier.fillMaxWidth()
        }
        if (radius > 0.dp) {
            modifier = modifier.clip(RoundedCornerShape(radius))
        }
        modifier = modifier
            .background(background)
            .clickable(
                interactionSource = remember { MutableInteractionSource() }, indication = null
            ) { }
        Box(modifier = modifier) {
            content(modal)
        }
    }
}

/**
 * 对话框行为（多个）
 * @param modal 模态
 * @param actions 行为列表
 */
@Composable
fun DialogActions(
    modal: IModal, actions: List<ModalAction>
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 6.dp, end = 6.dp, bottom = 8.dp),
        horizontalArrangement = Arrangement.End
    ) {
        actions.forEach {
            DialogAction(text = it.text, color = it.color, enabled = it.enabled) {
                it.onClick(modal)
            }
        }
    }
}

/**
 * 对话框行为
 * @param text 文本
 * @param fontSize 字体大小
 * @param color 字体颜色
 * @param fontWeight 字体
 * @param fontFamily 字体风格
 * @param paddingVer 垂直距离
 * @param paddingHor 水平距离
 * @param enabled 启用
 * @param onClick 点击事件
 */
@Composable
fun DialogAction(
    text: String,
    fontSize: TextUnit = 14.sp,
    color: Color = defaultPrimaryColor,
    fontWeight: FontWeight? = FontWeight.Bold,
    fontFamily: FontFamily? = null,
    paddingVer: Dp = 9.dp,
    paddingHor: Dp = 14.dp,
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed = interactionSource.collectIsPressedAsState()
    Text(
        text = text,
        modifier = Modifier
            .padding(horizontal = paddingHor, vertical = paddingVer)
            .alpha(if (isPressed.value) 0.5f else 1f)
            .clickable(
                enabled = enabled, interactionSource = interactionSource, indication = null
            ) {
                onClick.invoke()
            },
        color = color,
        fontSize = fontSize,
        fontWeight = fontWeight,
        fontFamily = fontFamily
    )
}

/**
 * 对话框文字提示
 * @param modal
 * @param title
 * @param content
 * @param actions
 */
@Composable
fun DialogMsg(
    modal: IModal, title: String, content: String, actions: List<ModalAction>
) {
    Column {
        DialogTitle(text = title)
        DialogMsgContent(text = content)
        DialogActions(modal = modal, actions = actions)
    }
}

/**
 * 对话框标题
 * @param text 标题
 * @param fontSize 字体大小
 * @param textAlign 文字位置
 * @param color 文字颜色
 * @param fontWeight 文字字体
 * @param fontFamily 文字风格
 * @param maxLines 最大行数
 * @param lineHeight 行高
 */
@Composable
fun DialogTitle(
    text: String,
    fontSize: TextUnit = 16.sp,
    textAlign: TextAlign? = null,
    color: Color = Color.Black,
    fontWeight: FontWeight? = FontWeight.Bold,
    fontFamily: FontFamily? = null,
    maxLines: Int = Int.MAX_VALUE,
    lineHeight: TextUnit = 20.sp,
) {
    Text(
        text = text,
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = 24.dp,
                start = DefaultDialogPaddingHor,
                end = DefaultDialogPaddingHor,
            ),
        textAlign = textAlign,
        color = color,
        fontSize = fontSize,
        fontWeight = fontWeight,
        fontFamily = fontFamily,
        maxLines = maxLines,
        lineHeight = lineHeight
    )
}

/**
 * 对话框文本内容
 * @param text 标题
 * @param fontSize 字体大小
 * @param textAlign 文字位置
 * @param color 文字颜色
 * @param fontWeight 文字字体
 * @param fontFamily 文字风格
 * @param maxLines 最大行数
 * @param lineHeight 行高
 */
@Composable
fun DialogMsgContent(
    text: String,
    fontSize: TextUnit = 14.sp,
    textAlign: TextAlign? = null,
    color: Color = Color.Black,
    fontWeight: FontWeight? = FontWeight.Normal,
    fontFamily: FontFamily? = null,
    maxLines: Int = Int.MAX_VALUE,
    lineHeight: TextUnit = 16.sp,
) {
    Text(
        text = text,
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = DefaultDialogPaddingHor,
                end = DefaultDialogPaddingHor,
                top = 16.dp,
                bottom = 24.dp
            ),
        textAlign = textAlign,
        color = color,
        fontSize = fontSize,
        fontWeight = fontWeight,
        fontFamily = fontFamily,
        maxLines = maxLines,
        lineHeight = lineHeight
    )
}

/**
 * 列表对话框
 * @param modal 模态
 * @param maxHeight 最大高度
 * @param state 列表状态
 * @param contentPadding 内容边距
 * @param children 列表项
 */
@Composable
fun DialogList(
    modal: IModal,
    maxHeight: Dp = Dp.Unspecified,
    state: LazyListState = rememberLazyListState(),
    contentPadding: PaddingValues = PaddingValues(vertical = 8.dp),
    children: LazyListScope.(IModal) -> Unit
) {
    LazyColumn(
        state = state,
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(0.dp, maxHeight),
        contentPadding = contentPadding
    ) {
        children(modal)
    }
}

/**
 * 单选对话框
 * @param modal
 * @param list
 * @param markIndex
 * @param state
 * @param maxHeight
 * @param itemIndication
 * @param itemTextSize
 * @param itemTextColor
 * @param itemTextFontWeight
 * @param itemTextFontFamily
 * @param itemMarkTintColor
 * @param contentPadding
 * @param onItemClick
 */
@Composable
fun DialogMarkList(
    modal: IModal,
    list: List<String>,
    markIndex: Int,
    state: LazyListState = rememberLazyListState(markIndex),
    maxHeight: Dp = Dp.Unspecified,
    itemIndication: Indication = rememberRipple(color = defaultIndicationColor),
    itemTextSize: TextUnit = 17.sp,
    itemTextColor: Color = defaultTextMainColor,
    itemTextFontWeight: FontWeight = FontWeight.Medium,
    itemTextFontFamily: FontFamily? = null,
    itemMarkTintColor: Color = defaultPrimaryColor,
    contentPadding: PaddingValues = PaddingValues(vertical = 8.dp),
    onItemClick: (modal: IModal, index: Int) -> Unit
) {
    DialogList(
        modal = modal, maxHeight = maxHeight, state = state, contentPadding = contentPadding
    ) {
        itemsIndexed(list) { index, s ->
            Item(title = s,
                indication = itemIndication,
                titleOnlyFontSize = itemTextSize,
                titleColor = itemTextColor,
                titleFontSize = itemTextSize,
                titleFontWeight = itemTextFontWeight,
                titleFontFamily = itemTextFontFamily,
                accessory = {
                    if (markIndex == index) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_mark),
                            contentDescription = "",
                            colorFilter = ColorFilter.tint(itemMarkTintColor)
                        )
                    }
                }) {
                onItemClick(modal, index)
            }
        }

    }
}

/**
 * 多选对话框
 */
@Composable
fun DialogMultipleCheckList(
    modal: IModal,
    list: List<String>,
    checked: Set<Int>,
    disabled: Set<Int> = emptySet(),
    disableAlpha: Float = 0.5f,
    state: LazyListState = rememberLazyListState(0),
    maxHeight: Dp = Dp.Unspecified,
    itemIndication: Indication = rememberRipple(color = defaultIndicationColor),
    itemTextSize: TextUnit = 17.sp,
    itemTextColor: Color = defaultTextMainColor,
    itemTextFontWeight: FontWeight = FontWeight.Medium,
    itemTextFontFamily: FontFamily? = null,
    itemCheckNormalTint: Color = defaultSeparatorColor,
    itemCheckCheckedTint: Color = defaultPrimaryColor,
    contentPadding: PaddingValues = PaddingValues(vertical = 8.dp),
    onItemClick: (modal: IModal, index: Int) -> Unit
) {
    DialogList(
        modal = modal, maxHeight = maxHeight, state = state, contentPadding = contentPadding
    ) {
        itemsIndexed(list) { index, s ->
            val isDisabled = disabled.contains(index)
            val onClick: (() -> Unit)? = if (isDisabled) null else {
                {
                    onItemClick(modal, index)
                }
            }
            Item(
                title = s,
                indication = itemIndication,
                titleOnlyFontSize = itemTextSize,
                titleColor = itemTextColor,
                titleFontSize = itemTextSize,
                titleFontWeight = itemTextFontWeight,
                titleFontFamily = itemTextFontFamily,
                alpha = if (isDisabled) disableAlpha else 1f,
                accessory = {
                    if (checked.contains(index)) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_checkbox_checked),
                            contentDescription = "",
                            colorFilter = ColorFilter.tint(itemCheckCheckedTint)
                        )
                    } else {
                        Image(
                            painter = painterResource(id = R.drawable.ic_checkbox_normal),
                            contentDescription = "",
                            colorFilter = ColorFilter.tint(itemCheckNormalTint)
                        )
                    }
                },
                onClick = onClick
            )
        }
    }
}

fun View.dialog(
    mask: Color = DefaultMaskColor,
    systemCancellable: Boolean = true,
    maskTouchBehavior: MaskTouchBehavior = MaskTouchBehavior.dismiss,
    modalHostProvider: ModalHostProvider = DefaultModalHostProvider,
    enter: EnterTransition = fadeIn(tween(), 0f),
    exit: ExitTransition = fadeOut(tween(), 0f),
    horEdge: Dp = defaultCommonHorSpace,
    verEdge: Dp = defaultDialogVerEdgeProtectionMargin,
    widthLimit: Dp = 360.dp,
    radius: Dp = 12.dp,
    background: Color = Color.White,
    content: @Composable (IModal) -> Unit
): IModal {
    return animateModal(
        mask,
        systemCancellable,
        maskTouchBehavior,
        modalHostProvider = modalHostProvider,
        enter = enter,
        exit = exit
    ) { modal ->
        Dialog(modal, horEdge, verEdge, widthLimit, radius, background, content)
    }
}

fun View.stillDialog(
    mask: Color = DefaultMaskColor,
    systemCancellable: Boolean = true,
    maskTouchBehavior: MaskTouchBehavior = MaskTouchBehavior.dismiss,
    modalHostProvider: ModalHostProvider = DefaultModalHostProvider,
    horEdge: Dp = 20.dp,
    verEdge: Dp = 20.dp,
    widthLimit: Dp = 360.dp,
    radius: Dp = 12.dp,
    background: Color = Color.White,
    content: @Composable (IModal) -> Unit
): IModal {
    return stillModal(
        mask, systemCancellable, maskTouchBehavior, modalHostProvider = modalHostProvider
    ) { modal ->
        Dialog(modal, horEdge, verEdge, widthLimit, radius, background, content)
    }
}
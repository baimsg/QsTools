package com.baimsg.qstool.ui.components

/**
 * Create by Baimsg on 2023/4/1
 *
 **/

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.*
import androidx.compose.ui.platform.InspectorInfo
import androidx.compose.ui.platform.InspectorValueInfo
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.debugInspectorInfo
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.*
import androidx.compose.ui.zIndex
import androidx.core.view.WindowInsetsCompat
import com.baimsg.qstool.base.provider.dp
import com.baimsg.qstool.base.provider.localQstoolWindowInsets
import com.baimsg.qstool.ui.defaultScrollAlphaChangeMaxOffset
import com.baimsg.qstool.ui.defaultTopBarZIndex
import com.baimsg.qstool.ui.layout.PressWithAlphaBox
import com.baimsg.qstool.ui.defaultTopBarHeight
import com.baimsg.qstool.ui.resources.R
import com.baimsg.qstool.ui.theme.QstoolComposeThem

/**
 * 定义顶部栏接口
 */
interface TopBarItem {
    /**
     * 绘制方法
     * @param topBarHeight 顶部栏高度
     */
    @Composable
    fun Compose(topBarHeight: Dp)
}

/**
 * 定义顶部栏标题布局接口
 */
interface TopBarTitleLayout {
    /**
     * 绘制方法
     * @param title 标题
     * @param subTitle 副标题
     * @param alignTitleCenter 标题剧中
     */
    @Composable
    fun Compose(title: CharSequence, subTitle: CharSequence, alignTitleCenter: Boolean)
}

/**
 * 默认顶部栏标题布局
 * @param titleColor 标题颜色
 * @param titleFontWeight 标题文字字体
 * @param titleFontFamily 标题字体风格
 * @param titleFontSize 标题字体大小
 * @param titleOnlyFontSize 标题字体唯一大小
 * @param subTitleColor 副标题颜色
 * @param subTitleFontWeight 副标题字体颜色
 * @param subTitleFontFamily 副标题字体风格
 * @param subTitleFontSize 副标题字体大小
 */
class DefaultTopBarTitleLayout(
    val titleColor: Color = Color.White,
    val titleFontWeight: FontWeight = FontWeight.Bold,
    val titleFontFamily: FontFamily? = null,
    val titleFontSize: TextUnit = 16.sp,
    val titleOnlyFontSize: TextUnit = 17.sp,
    val subTitleColor: Color = titleColor.copy(alpha = 0.8f),
    val subTitleFontWeight: FontWeight = FontWeight.Normal,
    val subTitleFontFamily: FontFamily? = null,
    val subTitleFontSize: TextUnit = 11.sp

) : TopBarTitleLayout {
    @Composable
    override fun Compose(title: CharSequence, subTitle: CharSequence, alignTitleCenter: Boolean) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = if (alignTitleCenter) Alignment.CenterHorizontally else Alignment.Start
        ) {
            Text(
                text = title.toString(),
                color = titleColor,
                fontWeight = titleFontWeight,
                fontFamily = titleFontFamily,
                fontSize = if (subTitle.isNotEmpty()) titleFontSize else titleOnlyFontSize,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
            if (subTitle.isNotEmpty()) {
                Text(
                    text = subTitle.toString(),
                    color = subTitleColor,
                    fontWeight = subTitleFontWeight,
                    fontFamily = subTitleFontFamily,
                    fontSize = subTitleFontSize,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
            }
        }
    }
}

/**
 * 顶部栏返回图标项
 * @param tint 着色
 * @param pressAlpha 按压透明度
 * @param disableAlpha 禁用透明度
 * @param enable 启用
 * @param onClick 点击事件
 */
open class TopBarBackIconItem(
    tint: Color = Color.White,
    pressAlpha: Float = 0.5f,
    disableAlpha: Float = 0.5f,
    enable: Boolean = true,
    onClick: () -> Unit
) : TopBarIconItem(
    R.drawable.ic_topbar_back, "返回", tint, pressAlpha, disableAlpha, enable, onClick
)

/**
 * 顶部栏图标项
 * @param icon 图标资源
 * @param contentDescription 描述
 * @param tint 着色
 * @param pressAlpha 按压透明度
 * @param disableAlpha 禁用透明度
 * @param enable 启用
 * @param onClick 点击事件
 */
open class TopBarIconItem(
    @DrawableRes val icon: Int,
    val contentDescription: String = "",
    val tint: Color = Color.White,
    val pressAlpha: Float = 0.5f,
    val disableAlpha: Float = 0.5f,
    val enable: Boolean = true,
    val onClick: () -> Unit
) : TopBarItem {

    @Composable
    override fun Compose(topBarHeight: Dp) {
        PressWithAlphaBox(
            modifier = Modifier.size(topBarHeight),
            enable = enable,
            pressAlpha = pressAlpha,
            disableAlpha = disableAlpha,
            onClick = onClick
        ) {
            Image(
                modifier = Modifier.fillMaxSize(),
                painter = painterResource(icon),
                contentDescription = contentDescription,
                colorFilter = ColorFilter.tint(tint),
                contentScale = ContentScale.Inside
            )
        }
    }

}

/**
 * 顶部栏文本项
 * @param text 文本
 * @param paddingHorizontal 水平内边距
 * @param fontSize 文字大小
 * @param fontWeight 文字风格
 * @param color 文本颜色
 * @param pressAlpha 按压透明度
 * @param disableAlpha 禁用透明度
 * @param enable 启用
 * @param onClick 点击事件
 *
 */
open class TopBarTextItem(
    val text: String,
    val paddingHorizontal: Dp = 12.dp,
    val fontSize: TextUnit = 14.sp,
    val fontWeight: FontWeight = FontWeight.Medium,
    val color: Color = Color.White,
    val pressAlpha: Float = 0.5f,
    val disableAlpha: Float = 0.5f,
    val enable: Boolean = true,
    val onClick: () -> Unit
) : TopBarItem {

    @Composable
    override fun Compose(topBarHeight: Dp) {
        PressWithAlphaBox(
            modifier = Modifier
                .height(topBarHeight)
                .padding(horizontal = paddingHorizontal),
            enable = enable,
            pressAlpha = pressAlpha,
            disableAlpha = disableAlpha,
            onClick = onClick
        ) {
            Text(
                text = text,
                modifier = Modifier.align(Alignment.Center),
                color = color,
                fontSize = fontSize,
                fontWeight = fontWeight
            )
        }
    }

}

/**
 * 具有惰性滚动状态的顶部栏
 * @param scrollState 滚动状态
 * @param title 标题
 * @param titleColor 标题颜色
 * @param subTitle 副标题
 * @param alignTitleCenter 对齐标题中心
 * @param height 高度
 * @param zIndex z轴高度
 * @param backgroundColor 背景颜色
 * @param changeWithBackground 改变背景
 * @param scrollAlphaChangeMaxOffset 滚动透明度变化最大偏移量
 * @param shadowElevation 阴影高程
 * @param shadowAlpha 阴影透明度
 * @param separatorHeight 分隔符高度
 * @param separatorColor 分隔符颜色
 * @param paddingStart 左边填充量
 * @param paddingEnd 右边填充量
 * @param titleBoxPaddingHor 标题框水平填充量
 * @param leftItems 左边项
 * @param rightItems 右边项
 */
@Composable
fun TopBarWithLazyScrollState(
    scrollState: LazyListState,
    title: CharSequence = "",
    titleColor: Color = QstoolComposeThem.colors.topBarText,
    subTitle: CharSequence = "",
    alignTitleCenter: Boolean = true,
    height: Dp = defaultTopBarHeight,
    zIndex: Float = defaultTopBarZIndex,
    backgroundColor: Color = QstoolComposeThem.colors.topBarBackground,
    changeWithBackground: Boolean = false,
    scrollAlphaChangeMaxOffset: Dp = defaultScrollAlphaChangeMaxOffset,
    shadowElevation: Dp = 16.dp,
    shadowAlpha: Float = 0.6f,
    separatorHeight: Dp = 0.dp,
    separatorColor: Color = QstoolComposeThem.colors.background.copy(0.8f),
    paddingStart: Dp = 4.dp,
    paddingEnd: Dp = 4.dp,
    titleBoxPaddingHor: Dp = 8.dp,
    leftItems: List<TopBarItem> = emptyList(),
    rightItems: List<TopBarItem> = emptyList()
) {
    val percent = with(LocalDensity.current) {
        if (scrollState.firstVisibleItemIndex > 0 || scrollState.firstVisibleItemScrollOffset.toDp() > scrollAlphaChangeMaxOffset) {
            1f
        } else scrollState.firstVisibleItemScrollOffset.toDp() / scrollAlphaChangeMaxOffset
    }
    TopBar(
        title,
        titleColor.copy(percent),
        subTitle = subTitle,
        alignTitleCenter,
        height,
        zIndex,
        if (changeWithBackground) backgroundColor.copy(backgroundColor.alpha * percent) else backgroundColor,
        shadowElevation,
        shadowAlpha * percent,
        separatorHeight,
        separatorColor.copy(separatorColor.alpha * percent),
        paddingStart,
        paddingEnd,
        titleBoxPaddingHor,
        leftItems,
        rightItems
    )
}

/**
 * 顶部栏
 * @param title 标题
 * @param titleColor 标题颜色
 * @param subTitle 副标题
 * @param alignTitleCenter 对齐标题中心
 * @param height 高度
 * @param zIndex z轴高度
 * @param backgroundColor 背景颜色
 * @param shadowElevation 阴影高程
 * @param shadowAlpha 阴影透明度
 * @param separatorHeight 分隔符高度
 * @param separatorColor 分隔符颜色
 * @param paddingStart 左边填充量
 * @param paddingEnd 右边填充量
 * @param titleBoxPaddingHor 标题框水平填充量
 * @param leftItems 左边项
 * @param rightItems 右边项
 * @param titleLayout 标题布局
 */
@Composable
fun TopBar(
    title: CharSequence,
    titleColor: Color = QstoolComposeThem.colors.topBarText,
    subTitle: CharSequence = "",
    alignTitleCenter: Boolean = true,
    height: Dp = defaultTopBarHeight,
    zIndex: Float = defaultTopBarZIndex,
    backgroundColor: Color = QstoolComposeThem.colors.topBarBackground,
    shadowElevation: Dp = 16.dp,
    shadowAlpha: Float = 0.4f,
    separatorHeight: Dp = 0.dp,
    separatorColor: Color = QstoolComposeThem.colors.background.copy(0.8f),
    paddingStart: Dp = 4.dp,
    paddingEnd: Dp = 4.dp,
    titleBoxPaddingHor: Dp = 8.dp,
    leftItems: List<TopBarItem> = emptyList(),
    rightItems: List<TopBarItem> = emptyList(),
    titleLayout: TopBarTitleLayout = DefaultTopBarTitleLayout(titleColor = titleColor)
) {
    val insets = localQstoolWindowInsets.current.getInsetsIgnoringVisibility(
        WindowInsetsCompat.Type.statusBars() or WindowInsetsCompat.Type.displayCutout()
    ).dp()
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Max)
            .zIndex(zIndex)
    ) {
        Box(modifier = Modifier
            .fillMaxSize()
            .graphicsLayer {
                this.alpha = shadowAlpha
                this.shadowElevation = shadowElevation.toPx()
                this.shape = RectangleShape
                this.clip = shadowElevation > 0.dp
            })
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(backgroundColor)
                .padding(top = insets.top)
                .height(height)
        ) {
            TopBarContent(
                title,
                subTitle,
                alignTitleCenter,
                height,
                paddingStart,
                paddingEnd,
                titleBoxPaddingHor,
                leftItems,
                rightItems,
                titleLayout
            )
            if (separatorHeight > 0.dp && separatorColor != Color.Transparent) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(separatorHeight)
                        .align(Alignment.BottomStart)
                        .background(separatorColor)
                )
            }
        }
    }

}

/**
 * 顶部栏内容
 * @param title 标题
 * @param subTitle 副标题
 * @param alignTitleCenter 对齐标题中心
 * @param height 高度
 * @param paddingStart 左填充量
 * @param paddingEnd 右填充量
 * @param titleBoxPaddingHor 标题框水平填充量
 * @param leftItems 左边项列表
 * @param rightItems 右边项列表
 * @param titleLayout 标题布局
 */
@Composable
fun TopBarContent(
    title: CharSequence,
    subTitle: CharSequence,
    alignTitleCenter: Boolean,
    height: Dp = defaultTopBarHeight,
    paddingStart: Dp = 4.dp,
    paddingEnd: Dp = 4.dp,
    titleBoxPaddingHor: Dp = 8.dp,
    leftItems: List<TopBarItem> = emptyList(),
    rightItems: List<TopBarItem> = emptyList(),
    titleLayout: TopBarTitleLayout = remember { DefaultTopBarTitleLayout() }
) {
    val measurePolicy = remember(alignTitleCenter) {
        MeasurePolicy { measurableList, constraints ->
            var centerMeasurable: Measurable? = null
            var leftPlaceable: Placeable? = null
            var rightPlaceable: Placeable? = null
            var centerPlaceable: Placeable? = null
            val usedConstraints = constraints.copy(minWidth = 0)
            measurableList.forEach {
                when ((it.parentData as? TopBarAreaParentData)?.area ?: TopBarArea.Left) {
                    TopBarArea.Left -> {
                        leftPlaceable = it.measure(usedConstraints)
                    }
                    TopBarArea.Right -> {
                        rightPlaceable = it.measure(usedConstraints)
                    }
                    TopBarArea.Center -> {
                        centerMeasurable = it
                    }
                }
            }
            val leftItemsWidth = leftPlaceable?.measuredWidth ?: 0
            val rightItemsWidth = rightPlaceable?.measuredWidth ?: 0
            val itemsWidthMax = maxOf(leftItemsWidth, rightItemsWidth)
            val titleContainerWidth = if (alignTitleCenter) {
                constraints.maxWidth - itemsWidthMax * 2
            } else {
                constraints.maxWidth - leftItemsWidth - rightItemsWidth
            }
            if (titleContainerWidth > 0) {
                centerPlaceable = centerMeasurable?.measure(
                    constraints.copy(
                        minWidth = 0, maxWidth = titleContainerWidth
                    )
                )
            }
            layout(constraints.maxWidth, constraints.maxHeight) {
                leftPlaceable?.place(0, 0, 0f)
                rightPlaceable?.let {
                    it.place(constraints.maxWidth - it.measuredWidth, 0, 1f)
                }
                centerPlaceable?.let {
                    if (alignTitleCenter) {
                        it.place(itemsWidthMax, 0, 2f)
                    } else {
                        it.place(leftItemsWidth, 0, 2f)
                    }
                }
            }
        }
    }
    Layout(
        content = {
            Row(
                modifier = Modifier
                    .fillMaxHeight()
                    .topBarArea(TopBarArea.Left),
                verticalAlignment = Alignment.CenterVertically
            ) {
                leftItems.forEach {
                    it.Compose(height)
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .topBarArea(TopBarArea.Center)
                    .padding(horizontal = titleBoxPaddingHor),
                contentAlignment = Alignment.CenterStart
            ) {
                titleLayout.Compose(title, subTitle, alignTitleCenter)
            }

            Row(
                modifier = Modifier
                    .fillMaxHeight()
                    .topBarArea(TopBarArea.Right),
                verticalAlignment = Alignment.CenterVertically
            ) {
                rightItems.forEach {
                    it.Compose(height)
                }
            }

        },
        measurePolicy = measurePolicy,
        modifier = Modifier
            .fillMaxWidth()
            .height(height)
            .padding(start = paddingStart, end = paddingEnd)
    )
}

/**
 * 顶部栏区域类型
 */
internal enum class TopBarArea { Left, Center, Right }

/**
 * 顶部栏区域父数据
 * @param area 区域类型
 */
internal data class TopBarAreaParentData(
    var area: TopBarArea = TopBarArea.Left
)

internal fun Modifier.topBarArea(area: TopBarArea) =
    this.then(TopBarAreaModifier(area = area, inspectorInfo = debugInspectorInfo {
        name = "area"
        value = area.name
    }))

/**
 * 顶部栏区域修饰符类
 * @param area 区域类型
 * @param inspectorInfo 检查员信息
 */
internal class TopBarAreaModifier(
    val area: TopBarArea, inspectorInfo: InspectorInfo.() -> Unit
) : ParentDataModifier, InspectorValueInfo(inspectorInfo) {
    override fun Density.modifyParentData(parentData: Any?): TopBarAreaParentData {
        return ((parentData as? TopBarAreaParentData) ?: TopBarAreaParentData()).also {
            it.area = area
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        val otherModifier = other as? TopBarAreaParentData ?: return false
        return area == otherModifier.area
    }

    override fun hashCode(): Int {
        return area.hashCode()
    }

    override fun toString(): String = "TopBarAreaModifier(area=$area)"
}
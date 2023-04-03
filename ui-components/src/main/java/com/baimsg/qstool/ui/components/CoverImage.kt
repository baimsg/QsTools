package com.baimsg.qstool.ui.components

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.isSpecified
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.transform.Transformation
import com.baimsg.qstool.ui.resources.R
import com.baimsg.qstool.ui.theme.QstoolComposeThem
import com.baimsg.qstool.ui.theme.Shapes
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.placeholder
import com.google.accompanist.placeholder.shimmer

/**
 * Create by Baimsg on 2023/4/3
 *
 **/

/**
 * Create by Baimsg on 2022/3/29
 * 封面图片
 * @param data 图片地址
 * @param modifier 背景修饰
 * @param imageModifier 图片修饰
 * @param transformations 转换效果 比如：高斯模糊
 * @param isComics 是否漫画
 * @param size 大小「正方形」
 * @param backgroundColor 背景颜色
 * @param contentColor 内容颜色
 * @param contentScale 内容显示方式
 * @param shape 形状设置
 **/
@Composable
fun CoverImage(
    data: Any?,
    modifier: Modifier = Modifier,
    imageModifier: Modifier = Modifier,
    transformations: List<Transformation> = emptyList(),
    isComics: Boolean = false,
    size: Dp = Dp.Unspecified,
    backgroundColor: Color = QstoolComposeThem.colors.background,
    contentColor: Color = QstoolComposeThem.colors.iconCurrent,
    contentScale: ContentScale = ContentScale.Crop,
    shape: Shape = Shapes.small,
    icon: Painter = painterResource(R.drawable.img_default_loading),
    iconPadding: Dp = if (size != Dp.Unspecified) size * 0.29f else 28.dp,
    bitmapPlaceholder: Bitmap? = null,
    contentDescription: String? = null,
    elevation: Dp = 2.dp,
) {
    val sizeMod = if (size.isSpecified) Modifier.size(size) else Modifier
    Surface(
        elevation = elevation,
        shape = shape,
        color = backgroundColor,
        modifier = modifier.then(sizeMod)
    ) {
        SubcomposeAsyncImage(
            model = ImageRequest.Builder(LocalContext.current).data(data)
                .setParameter("comics", isComics).transformations(transformations).crossfade(true)
                .build(), contentDescription = contentDescription, contentScale = contentScale
        ) {
            val state = painter.state
            when (state) {
                is AsyncImagePainter.State.Error, AsyncImagePainter.State.Empty, is AsyncImagePainter.State.Loading -> {
                    Icon(
                        painter = icon,
                        tint = contentColor.copy(alpha = ContentAlpha.disabled),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxSize()
                            .background(backgroundColor)
                            .padding(iconPadding)
                    )
                    Box(
                        Modifier
                            .fillMaxSize()
                            .placeholder(
                                visible = state is AsyncImagePainter.State.Loading,
                                color = Color.Transparent,
                                shape = shape,
                                highlight = PlaceholderHighlight.shimmer(
                                    highlightColor = contentColor.copy(
                                        alpha = .15f
                                    )
                                ),
                            )
                    )
                }
                else -> SubcomposeAsyncImageContent(imageModifier.fillMaxSize())
            }

            if (bitmapPlaceholder != null && state is AsyncImagePainter.State.Loading) {
                Image(
                    painter = rememberAsyncImagePainter(bitmapPlaceholder),
                    contentDescription = null,
                    contentScale = contentScale,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(shape)
                )
            }
        }
    }
}
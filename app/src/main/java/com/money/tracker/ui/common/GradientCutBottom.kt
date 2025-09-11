package com.money.tracker.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex

@Composable
fun GradientCutBottom(
    modifier: Modifier = Modifier,
    height: Dp = 96.dp,
    alpha: Float = 1f,
    zIndex: Float? = null
) {
    Spacer(
        modifier = modifier
            .fillMaxWidth()
            .height(height)
            .then(if (zIndex != null) Modifier.zIndex(zIndex) else Modifier)
            .background(Brush.verticalGradient(listOf(Color.Transparent, Color.LightGray)))
            .alpha(alpha = alpha)
    )
}

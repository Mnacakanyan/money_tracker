package com.money.tracker.ui.common

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun OnboardingButton(
    modifier: Modifier = Modifier,
    text: String,
    textColor: Color,
    @DrawableRes iconStartRes: Int? = null,
    hasNext: Boolean = false,
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(50.dp))
            .background(
                brush = Brush.horizontalGradient(listOf(Color(0xFF6B4DFF), Color(0xFFAA99FF))),
                shape = RoundedCornerShape(50.dp)
            )
            .clickable(onClick = onClick, enabled = enabled),
        contentAlignment = Alignment.Center
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            if (iconStartRes != null) {
                Icon(Icons.Filled.Star, contentDescription = "iconStart", modifier = Modifier.padding(start=8.dp))
            }
            Text(
                modifier = Modifier.padding(vertical = 16.dp, horizontal = 24.dp),
                text = text,
                style = MaterialTheme.typography.bodyLarge.copy(
                    color = textColor,
                    fontWeight = FontWeight.Bold
                )
            )
            if (hasNext && enabled) {
                Icon(
                    imageVector = Icons.Filled.ArrowForward,
                    contentDescription = "next",
                    tint = textColor,
                    modifier = Modifier.padding(end=8.dp)
                )
            }
        }
    }
}

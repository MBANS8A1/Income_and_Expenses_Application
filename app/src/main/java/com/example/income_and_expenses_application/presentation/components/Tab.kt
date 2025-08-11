package com.example.income_and_expenses_application.presentation.components

import androidx.annotation.DrawableRes
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

//Constants used to define the tab(s)
private val TabHeight = 56.dp
private const val InactiveTabOpacity = 0.60f
private const val TabFadeInAnimationDuration = 150
private const val TabFadeInAnimationDelay = 100
private const val TabFadeOutAnimationDuration = 100

@Composable
fun IncExpRowTab(
    text: String,
    @DrawableRes icon:Int,
    onSelected:() -> Unit,
    selected: Boolean
) {
    val colour =  MaterialTheme.colorScheme.onSurface
    val duration = if(selected) TabFadeInAnimationDuration
                   else TabFadeOutAnimationDuration
    //animate the transition
    val animationSpec = remember{
        tween<Color>(
            durationMillis = duration,
            easing = LinearEasing,
            delayMillis = TabFadeInAnimationDelay
        )

    }
    //tab tint colour
    val tabTintColour by animateColorAsState(
        targetValue = if (selected) colour else colour.copy(
            alpha = InactiveTabOpacity
        ),
        animationSpec = animationSpec
    )
}
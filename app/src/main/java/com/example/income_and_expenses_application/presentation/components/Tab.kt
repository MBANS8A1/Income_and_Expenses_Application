package com.example.income_and_expenses_application.presentation.components

import androidx.annotation.DrawableRes
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import java.util.Locale

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
    //A Row to display the icons
    Row(
        modifier = Modifier
            .padding(16.dp)
            .animateContentSize()
            .height(TabHeight)
            .selectable( //selectable as a mutually exclusive group
                selected = selected,
                onClick = onSelected,
                interactionSource = remember{
                    MutableInteractionSource() //keeps track of interactions that happen
                },
                indication = ripple( //pond-like ripple on touch
                    bounded = false,
                    radius = Dp.Unspecified,
                    color = Color.Unspecified
                )
            )
            .clearAndSetSemantics {
                contentDescription = text
            }
    ){
        //content:icons
        Icon(
            painter = painterResource(id=icon),
            contentDescription = text,
            tint = tabTintColour
        )
        //tab should show its name if selected an others should be disabled
        if(selected){
            Spacer(modifier = Modifier.width(12.dp))
            Text(text=text.uppercase(Locale.getDefault()), color=tabTintColour)
        }
    }
}
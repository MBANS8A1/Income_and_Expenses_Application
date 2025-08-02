package com.example.income_and_expenses_application.presentation.components

import android.graphics.Color
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.rememberTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp

@Composable
fun <T> TransactionStatement(
    modifier: Modifier = Modifier,
    items: List<T>,
    colours: (T) -> Color,
    amount: Float,
    amountsTotal: Float,
    circleLabel: String,
    onItemSwiped: (T) -> Unit,
    key: (T) -> Int,
    rows:@Composable (T) -> Unit
) {
    LazyColumn(
        modifier = modifier
    ) {
        item{
            Box(modifier = Modifier.padding(16.dp)){

            }
        }
    }
}


@Composable
fun AnimateCircle(
    proportions: List<Float>,
    colours: List<Color>,
    modifier: Modifier = Modifier

) {
    val currentState = remember{
        MutableTransitionState(AnimatedCircleProgress.START)
            .apply{ targetState = AnimatedCircleProgress.END}
    } //transition state defined
    //gives the current density (stroke converted from dp to pixels)
    val stroke  = with(LocalDensity.current){
        Stroke(5.dp.toPx())
    }
    val transition = rememberTransition(transitionState = currentState, label = "Circle/Ring")
    //angle offset for offsetting the cycle
    val angleOffset by transition.animateFloat(
        transitionSpec ={ tween(
            durationMillis = 900,
            delayMillis = 500,
            easing = LinearOutSlowInEasing
        )},
        label = ""
    ){progress ->
        if(progress == AnimatedCircleProgress.START){
            0f
        }else{
            360f
        }
    }
}

private enum class AnimatedCircleProgress{
    START,
    END
}
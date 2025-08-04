package com.example.income_and_expenses_application.presentation.components

import android.graphics.Color
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.rememberTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
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
    colours: List<androidx.compose.ui.graphics.Color>,
    modifier: Modifier = Modifier

) {

    val currentState = remember{
        MutableTransitionState(AnimatedCircleProgress.START)
            .apply{ targetState = AnimatedCircleProgress.END}
    } //transition state defined from START to END
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
    ){progress ->               //targetValueByState
        if(progress == AnimatedCircleProgress.START){
            0f
        }else{
            360f
        }
    }

    val shift by transition.animateFloat(
        transitionSpec ={ tween(
            durationMillis = 900,
            delayMillis = 500,
            //custom easing (animation) curve
            easing = CubicBezierEasing(0f,0.75f,0.35f,0.85f)
        )},
        label = ""
    ){progress ->               //targetValueByState
        if(progress == AnimatedCircleProgress.START){
            0f
        }else{
            360f
        }
    }
    //Now creating a Canvas (with a DrawScope) to draw the arcs
    Canvas(modifier = modifier) { //size is part of the DrawScope
        val innerRadius = (size.minDimension - stroke.width)/2 //stay within bounding box
        val halfSize = size/2.0f
        val topLeft = Offset(
            x = halfSize.width - innerRadius,
            y = halfSize.height - innerRadius
        )
        //Get the full size
        val size  = Size(innerRadius*2,innerRadius*2)
        //start drawing from (0,innerRadius*2)
        var startAngle = shift - 90f //shift initially is 0f
        //Use the proportions to draw the arcs
        proportions.forEachIndexed{ index,proportion ->
            val sweep = proportion * angleOffset
            drawArc(
                color = colours[index],
                startAngle = startAngle +  DividerLengthInDegrees / 2,
                //we want to change the angle Offset based upon the sweep
                //arc accommodated with divider
                sweepAngle = sweep - DividerLengthInDegrees,
                topLeft = topLeft,
                size = size,
                //I want to draw an arc not a full circle
                useCenter = false,
                style = stroke
            )
            //as different arcs will be drawn
            startAngle += sweep
        }
    }
}

private enum class AnimatedCircleProgress{
    START,
    END
}

//divider length for the different incomes in the circle/arc in degrees
private const val DividerLengthInDegrees = 1.8f
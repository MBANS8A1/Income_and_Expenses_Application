package com.example.income_and_expenses_application.presentation.components

import android.graphics.Color
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.rememberTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxState
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.example.income_and_expenses_application.util.formatAmount

@Composable
fun <T> TransactionStatement(
    modifier: Modifier = Modifier,
    items: List<T>,
    colours: (T) -> androidx.compose.ui.graphics.Color,
    amounts: (T) -> Float,
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
                //extracting items
                val transProportions = items.extractProportions{amounts(it)}
                val circularRingColour = items.map{ colours(it)}

                AnimateCircle( //need extension function for proportions above
                   proportions = transProportions,
                    colours = circularRingColour,
                    modifier = Modifier
                        .height(300.dp)
                        .align(Alignment.Center)
                        .fillMaxWidth()
                )
                //Text with the <T> amount will be on top of the circular ring in a Box layout
                Column(
                    modifier = Modifier
                        .align(Alignment.Center)
                ){
                   Text(
                       text =  circleLabel,
                       style = MaterialTheme.typography.bodyLarge,
                       modifier = Modifier.align(Alignment.CenterHorizontally)
                   )
                    Text(
                        text = formatAmount(amountsTotal),
                        style = MaterialTheme.typography.headlineLarge,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                }
            }
            Spacer(modifier = Modifier
                .height(10.dp)
            )
        }
        //Now I need to render this list of Income/Expense items
        items(items,key = key){ item ->
            //create a dismiss state as I want to delete item on swipe
            var isDismissed by remember{ mutableStateOf(false)}
            var showDismissedBackground by remember { mutableStateOf(false) }
            //We have a Box
            val dismissState = rememberSwipeToDismissBoxState(
                confirmValueChange = { state ->
                    when(state){

                        SwipeToDismissBoxValue.StartToEnd -> {
                            isDismissed = true
                        }
                        else ->{
                            isDismissed = false
                            return@rememberSwipeToDismissBoxState false
                        }
                    }
                    return@rememberSwipeToDismissBoxState true
                }
            )

            LaunchedEffect(isDismissed) {
                if(isDismissed){
                    onItemSwiped.invoke(item)
                }
            } //1st LaunchedEffect

            LaunchedEffect(dismissState.progress) {
                showDismissedBackground = when{
                    dismissState.progress <0.5f -> {
                        true
                    }
                    else ->{
                        false
                    }
                }
            } //2nd LaunchedEffects


            SwipeToDismissBox(
                state = dismissState,
                backgroundContent = {
                    AnimatedVisibility(showDismissedBackground) {
                        Surface(
                            modifier = Modifier
                                .fillMaxWidth()
                                .align(Alignment.CenterVertically)
                                .height(68.dp),
                            color = MaterialTheme.colorScheme.error
                        ){
                            Box(
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = null
                                )
                            } //Box
                        }//Surface
                    }//AnimatedVisibility
                }, //backgroundContent
                content = {
                    rows(item)
                },
                enableDismissFromStartToEnd = true,
                enableDismissFromEndToStart = false
            )//SwipeToDismissBox
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

//create extension function to extract the proportions
fun <E> List<E>.extractProportions(selector: (E) -> Float): List<Float>{
    /*So if we have 10 Income items we need to extract them;
    next sum the incomes and calculate the proportion of each Income on the total
    */
    //this refers to the List
    var total = this.sumOf{selector(it).toDouble()}
    //Now we want to return a list with these particular proportions
    return this.map{(selector(it)/total).toFloat()}
}
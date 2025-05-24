package com.cvindosistem.simpeldesa.core.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun AppStepAnimatedContent(
    currentStep: Int,
    modifier: Modifier = Modifier,
    label: String = "step_transition",
    content: @Composable (Int) -> Unit
) {
    AnimatedContent(
        targetState = currentStep,
        transitionSpec = {
            (slideInHorizontally(
                initialOffsetX = { if (targetState > initialState) it else -it }
            ) + fadeIn()).togetherWith(
                slideOutHorizontally(
                    targetOffsetX = { if (targetState > initialState) -it else it }
                ) + fadeOut()
            )
        },
        label = label,
        modifier = modifier
    ) { step ->
        content(step)
    }
}

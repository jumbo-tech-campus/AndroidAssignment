package com.assignment.design_system.animations

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
import androidx.compose.ui.graphics.TransformOrigin

@OptIn(ExperimentalAnimationApi::class)
fun bottomLeftRevealScaleIn(): EnterTransition {
    return scaleIn(
        transformOrigin = TransformOrigin(1f, 1f),
        animationSpec = tween(durationMillis = 1000)
    ) + fadeIn(animationSpec = tween(durationMillis = 1000))
}

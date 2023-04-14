package com.assignment.design_system.components.buttons

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import com.assignment.design_system.components.texts.JumboButtonText

@Composable
fun CircularButton(text: String, onClick:() -> Unit, modifier: Modifier = Modifier) {
    Box(modifier = modifier
        .background(color = MaterialTheme.colorScheme.primary, shape = RoundedCornerShape(100))
            .clip(RoundedCornerShape(100)).clickable { onClick() },
        contentAlignment = Alignment.Center){
        JumboButtonText(text = text, color = MaterialTheme.colorScheme.onPrimary)
    }
}

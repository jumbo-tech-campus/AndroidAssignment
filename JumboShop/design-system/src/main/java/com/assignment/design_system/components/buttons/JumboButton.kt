package com.assignment.design_system.components.buttons

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.assignment.design_system.components.texts.JumboButtonText

@Composable
fun JumboButton(
    modifier: Modifier = Modifier,
    text: String,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    elevation: ButtonElevation? = ButtonDefaults.buttonElevation(),
    colors: ButtonColors = ButtonDefaults.buttonColors(),
    border: BorderStroke? = null,
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    onClick: () -> Unit
) {
    Button(modifier = modifier, onClick = { onClick()},
        enabled = enabled,
        interactionSource = interactionSource,
        elevation = elevation,
        colors = colors,
        border = border,
        contentPadding = contentPadding) {
        JumboButtonText(text = text)
    }
}

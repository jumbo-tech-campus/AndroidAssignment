package com.assignment.design_system.components.buttons

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun CircularIconButton(icon: ImageVector,onClick:() -> Unit, modifier: Modifier = Modifier) {
    IconButton(modifier = modifier
        .clip(RoundedCornerShape(100)),onClick = { onClick() }) {
        Icon(imageVector = icon, contentDescription = "")
    }
}

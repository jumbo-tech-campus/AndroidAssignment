package com.assignment.design_system.components.appbars

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.assignment.design_system.components.texts.JumboHeader

@Composable
fun JumboAppBar(title: String, modifier: Modifier = Modifier, searchBar: (@Composable () -> Unit)? = null) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(bottomStartPercent = 30, bottomEndPercent = 30)
            )
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Column {
            JumboHeader(
                text = title,
                color = MaterialTheme.colorScheme.onPrimary,
            )
            if(searchBar != null) {
                Spacer(modifier = Modifier.height(24.dp))
                searchBar()
            }
        }
    }
}

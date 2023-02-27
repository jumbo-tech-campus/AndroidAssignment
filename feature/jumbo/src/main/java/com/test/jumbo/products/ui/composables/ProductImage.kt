package com.test.jumbo.products.ui.composables

import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.jumbo.products.R

@Composable
fun ProductImage(url: String, modifier: Modifier) {
    SubcomposeAsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(url)
            .setHeader("User-Agent", "Mozilla/5.0")
            .build(),
        loading = {
            CircularProgressIndicator()
        },
        contentDescription = stringResource(R.string.app_name),
        modifier = modifier
    )
}
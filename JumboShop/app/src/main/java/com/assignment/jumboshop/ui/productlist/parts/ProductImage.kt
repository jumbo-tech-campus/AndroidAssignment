package com.assignment.jumboshop.ui.productlist.parts

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImage
import com.assignment.jumboshop.R

@Composable
fun ProductImage(url: String, contentDescription: String = "", contentScale: ContentScale = ContentScale.Crop, modifier: Modifier) {
    Log.d("PImage", "URL: $url")
    AsyncImage(
        modifier = modifier,
        model = url,
        contentDescription = contentDescription,
        contentScale = contentScale,
        placeholder = painterResource(id = R.drawable.placeholder),
        error = painterResource(id = R.drawable.placeholder),
    )
}

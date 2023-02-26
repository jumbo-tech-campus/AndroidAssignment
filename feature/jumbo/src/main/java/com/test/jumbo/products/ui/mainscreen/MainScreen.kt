package com.test.jumbo.products.ui.mainscreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.rememberPagerState
import com.test.jumbo.products.states.ProductsInfoState
import com.test.jumbo.products.viewmodel.ProductsViewModel

@OptIn(ExperimentalPagerApi::class)
@Composable
fun ProductsMainScreen(
    navController: NavController,
    viewModel: ProductsViewModel = hiltViewModel()
) {
    val state = viewModel.viewStateFlow.collectAsState(initial = ProductsInfoState())
    val tabs = listOf(TabItem.Products(state), TabItem.Cart(state))
    val pagerState = rememberPagerState()
    Scaffold(
        topBar = { TopBar() },
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            Tabs(tabs = tabs, pagerState = pagerState)
            TabsContent(tabs = tabs, pagerState = pagerState)
        }
    }
}
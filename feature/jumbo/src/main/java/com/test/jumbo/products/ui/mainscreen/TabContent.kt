package com.test.jumbo.products.ui.mainscreen

import androidx.compose.material.TabRow
import androidx.compose.material.Icon
import androidx.compose.material.TabRowDefaults
import androidx.compose.material.TopAppBar
import androidx.compose.material.Text
import androidx.compose.material.LeadingIconTab
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.launch
import com.jumbo.products.R
import kotlinx.coroutines.CoroutineScope

@Composable
fun TopBar() {
    val systemUiController = rememberSystemUiController()
    systemUiController.setStatusBarColor(
        color = colorResource(R.color.product_screen_main_color),
        darkIcons = true
    )

    TopAppBar(
        title = {
            Text(
                text = stringResource(id = R.string.app_name),
                color = colorResource(R.color.white)
            )
        },
        elevation = 0.dp,
        backgroundColor = colorResource(R.color.product_screen_main_color)
    )
}


@OptIn(ExperimentalPagerApi::class)
@Composable
fun Tabs(tabs: List<TabItem>, pagerState: PagerState, showCart: Boolean) {
    val scope = rememberCoroutineScope()
    NavigateToCartScreen(showCart, scope, pagerState)
    TabRow(
        selectedTabIndex = pagerState.currentPage,
        backgroundColor = colorResource(id = R.color.product_screen_main_color),
        contentColor = colorResource(id = R.color.white),
        indicator = { tabPositions ->
            TabRowDefaults.Indicator(
                Modifier.pagerTabIndicatorOffset(pagerState, tabPositions)
            )
        }) {
        tabs.forEachIndexed { index, tab ->
            LeadingIconTab(
                icon = { Icon(painter = painterResource(id = tab.icon), contentDescription = "") },
                text = { Text(text = stringResource(id = tab.title)) },
                selected = pagerState.currentPage == index,
                onClick = {
                    scope.launch {
                        pagerState.animateScrollToPage(index)
                    }
                },
            )
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
private fun NavigateToCartScreen(
    showCart: Boolean,
    scope: CoroutineScope,
    pagerState: PagerState
) {
    if (showCart) {
        LaunchedEffect(Unit) {
            scope.launch {
                pagerState.animateScrollToPage(1)
            }
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun TabsContent(tabs: List<TabItem>, pagerState: PagerState) {
    HorizontalPager(state = pagerState, count = tabs.size) { page ->
        tabs[page].screen()
    }
}
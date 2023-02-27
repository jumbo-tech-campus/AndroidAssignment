package com.test.jumbo.products.ui.mainscreen

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.padding
import androidx.compose.material.TabRow
import androidx.compose.material.Icon
import androidx.compose.material.Badge
import androidx.compose.material.TabRowDefaults
import androidx.compose.material.TopAppBar
import androidx.compose.material.BadgedBox
import androidx.compose.material.Text
import androidx.compose.material.LeadingIconTab
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.launch
import com.jumbo.products.R
import com.test.jumbo.products.states.ProductsInfoState
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
fun Tabs(tabs: List<TabItem>, pagerState: PagerState, state: ProductsInfoState) {
    val scope = rememberCoroutineScope()
    NavigateToCartScreen(state.showCart, scope, pagerState)
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
                icon = { BadgeIconTab(state, tab.icon, index) },
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

@Composable
private fun BadgeIconTab(state: ProductsInfoState, @DrawableRes tabIcon: Int, index: Int) {
    if (state.showCart && index == 1) {
        BadgedBox(badge = {
            Badge {
                Text(
                    state.cartItem.size.toString(),
                    style = TextStyle(color = colorResource(R.color.white)),
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(2.dp)
                )
            }
        }) {
            Icon(
                painter = painterResource(id = tabIcon),
                contentDescription = "Badge Image"
            )
        }
    } else {
        Icon(painter = painterResource(id = tabIcon), contentDescription = "Icon Image")
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
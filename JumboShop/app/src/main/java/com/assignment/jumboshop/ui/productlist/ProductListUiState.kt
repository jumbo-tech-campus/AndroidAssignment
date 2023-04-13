package com.assignment.jumboshop.ui.productlist

sealed class ProductListUiState<out T> {
    object Loading : ProductListUiState<Nothing>()
    data class Success<T>(val data: T) : ProductListUiState<T>()
    data class Error(val exception: Exception) : ProductListUiState<Nothing>()
}

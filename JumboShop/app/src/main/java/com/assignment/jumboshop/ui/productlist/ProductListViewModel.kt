package com.assignment.jumboshop.ui.productlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import arrow.core.Either
import com.assignment.domain.entities.Product
import com.assignment.domain.usecases.GetProductsUseCase
import com.assignment.jumboshop.di.IO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductListViewModel @Inject constructor(
    private val getProductsUseCase: GetProductsUseCase,
    @IO private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _productsState = MutableStateFlow<ProductListUiState<List<Product>>>(
        ProductListUiState.Loading,
    )
    val productsState: StateFlow<ProductListUiState<List<Product>>> = _productsState

    init {
        loadProducts()
    }

    fun loadProducts() {
        viewModelScope.launch(dispatcher) {
            _productsState.value = ProductListUiState.Loading
            getProductsUseCase.execute().collect { result ->
                _productsState.value = when (result) {
                    is Either.Right -> ProductListUiState.Success(result.value)
                    is Either.Left -> ProductListUiState.Error(result.value)
                }
            }
        }
    }

    fun getProductById(productId: String?): Product? {
        return if (productId != null) {
            val productListUiState = _productsState.value
            if(productListUiState is ProductListUiState.Success<List<Product>>) {
                productListUiState.data.firstOrNull { it.id == productId }
            }else null
        } else {
            null
        }
    }
}

package com.abhinash.jumboandroidassignment.ui.products

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abhinash.domain.functional.Failure
import com.abhinash.domain.functional.onFailure
import com.abhinash.domain.functional.onSuccess
import com.abhinash.domain.models.Product
import com.abhinash.domain.repository.ProductRepository
import com.abhinash.domain.usecase.AddToCartUseCase
import com.abhinash.domain.usecase.GetProductsUseCase
import com.abhinash.jumboandroidassignment.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductListViewModel @Inject constructor(
    private val getProductsUseCase: GetProductsUseCase,
    private val productRepository: ProductRepository,
    private val addToCartUseCase: AddToCartUseCase
) :
    ViewModel() {
    private val mutableProductList = MutableLiveData<ListViewState>()
    val productList: LiveData<ListViewState> = mutableProductList

    private val mutableCartProductsCount = MutableLiveData<Int>()
    val cartProductsCount: LiveData<Int> = mutableCartProductsCount

    private val mutableAddToCart = MutableLiveData<AddToCartState>()
    val addToCart: LiveData<AddToCartState> = mutableAddToCart


    fun loadProducts() {
        mutableProductList.value = ListViewState.Loading
        viewModelScope.launch {
            getProductsUseCase.execute(Unit)
                .onSuccess {
                    if (it.isEmpty()) {
                        mutableProductList.value = ListViewState.Error(ProductListFailure.EmptyList)
                        return@onSuccess
                    }

                    mutableProductList.value = ListViewState.Success(it)
                }
                .onFailure {
                    mutableProductList.value =
                        ListViewState.Error(ProductListFailure.ExceptionWhileLoading)
                }
        }
    }

    fun loadCartCount() {
        viewModelScope.launch {
            productRepository.getProductsForCart().collect {
                mutableCartProductsCount.value = it.count()
            }
        }
    }

    fun addToCart(product: Product) {
        viewModelScope.launch {
            addToCartUseCase.execute(product)
                .onSuccess { mutableAddToCart.value = AddToCartState.Success }
                .onFailure { mutableAddToCart.value = AddToCartState.Failed }
        }
    }
}

sealed class ListViewState {
    object Loading : ListViewState()

    data class Error(
        val failure: Failure
    ) : ListViewState()

    data class Success(
        val products: List<Product>
    ) : ListViewState()
}

sealed class AddToCartState {
    object Success : AddToCartState()
    object Failed : AddToCartState()
}

sealed class ProductListFailure(val failureReasonResourceId: Int) : Failure.FeatureFailure() {
    object EmptyList : ProductListFailure(R.string.no_result_found)
    object ExceptionWhileLoading : ProductListFailure(R.string.error_loading_product_list)
}
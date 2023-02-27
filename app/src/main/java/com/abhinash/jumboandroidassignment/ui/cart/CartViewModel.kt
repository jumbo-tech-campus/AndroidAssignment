package com.abhinash.jumboandroidassignment.ui.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abhinash.domain.functional.Failure
import com.abhinash.domain.functional.onFailure
import com.abhinash.domain.functional.onSuccess
import com.abhinash.domain.models.CartProduct
import com.abhinash.domain.repository.ProductRepository
import com.abhinash.domain.usecase.RemoveFromCartUseCase
import com.abhinash.jumboandroidassignment.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val productRepository: ProductRepository,
    private val removeFromCartUseCase: RemoveFromCartUseCase
) : ViewModel() {
    private val mutableCartProductList = MutableLiveData<ListViewState>()
    val cartProductList: LiveData<ListViewState> = mutableCartProductList

    private val mutableRemoveFromCart = MutableLiveData<RemoveFromCartState>()
    val removeFromCart: LiveData<RemoveFromCartState> = mutableRemoveFromCart

    fun loadCart() {
        viewModelScope.launch {
            productRepository.getProductsForCart().collect {
                mutableCartProductList.value = ListViewState.Loading
                if (it.isEmpty()) {
                    mutableCartProductList.value =
                        ListViewState.Error(CartProductListFailure.EmptyList)
                    return@collect
                }
                mutableCartProductList.value = ListViewState.Success(it)
            }
        }
    }

    fun removeFromCart(cartProduct: CartProduct) {
        viewModelScope.launch {
            removeFromCartUseCase.execute(cartProduct)
                .onSuccess { mutableRemoveFromCart.value = RemoveFromCartState.Success }
                .onFailure { mutableRemoveFromCart.value = RemoveFromCartState.Failed }
        }
    }
}

sealed class ListViewState {
    object Loading : ListViewState()

    data class Error(
        val failure: Failure
    ) : ListViewState()

    data class Success(
        val cartProducts: List<CartProduct>
    ) : ListViewState()
}

sealed class RemoveFromCartState {
    object Success : RemoveFromCartState()
    object Failed : RemoveFromCartState()
}

sealed class CartProductListFailure(val failureReasonResourceId: Int) : Failure.FeatureFailure() {
    object EmptyList : CartProductListFailure(R.string.no_result_found)
}
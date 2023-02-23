package com.abhinash.jumboandroidassignment.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abhinash.domain.functional.onSuccess
import com.abhinash.domain.models.Product
import com.abhinash.domain.usecase.GetProductsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductListViewModel @Inject constructor(private val getProductsUseCase: GetProductsUseCase) :
    ViewModel() {
    private lateinit var products: List<Product>
    init {
        viewModelScope.launch {
            getProductsUseCase.execute(Unit).onSuccess {
                products = it
            }
        }
    }
    fun initialize() {
        if(products.isEmpty()){

        }
    }
}

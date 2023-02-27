package com.abhinash.domain.usecase

import com.abhinash.domain.models.Product

interface GetProductsUseCase: BaseUseCase<List<Product>, Unit>

// If this use case had required multiple parameters, such as productType, showNotAvailableProducts, sortBy, etc.,
// then, instead of just Unit, I would have declared something like:
// data GetProductsUseCaseParameters(productType: Type, showNotAvailableProducts: Boolean, sortBy: SortingMethod).
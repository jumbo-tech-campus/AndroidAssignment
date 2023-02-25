package com.abhinash.domain.usecase

import com.abhinash.domain.models.CartProduct

interface GetCartProducts: BaseUseCase<List<CartProduct>, Unit>
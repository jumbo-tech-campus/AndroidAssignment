package com.assignment.domain.repository

import com.assignment.domain.entities.Product

interface ProductRepository {
    suspend fun getProducts(): List<Product>
    suspend fun getProductById(id: String): Product?
}

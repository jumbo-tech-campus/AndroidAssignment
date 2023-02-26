package com.abhinash.jumboandroidassignment

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import com.abhinash.domain.models.Product
import com.abhinash.jumboandroidassignment.provider.ImageLoaderProvider
import com.abhinash.jumboandroidassignment.ui.products.ProductClickListener
import com.abhinash.jumboandroidassignment.ui.products.ProductListRecyclerViewAdapter
import com.appmattus.kotlinfixture.kotlinFixture
import com.google.common.truth.Truth
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@SmallTest
@RunWith(MockitoJUnitRunner::class)
class ProductListRecyclerViewAdapterTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var adapter: ProductListRecyclerViewAdapter

    @Mock
    private lateinit var mockImageLoadProvider: ImageLoaderProvider
    @Mock
    private lateinit var mockProductClickListener : ProductClickListener

    @Before
    fun setUp() {
        adapter = ProductListRecyclerViewAdapter(mockProductClickListener, mockImageLoadProvider)
    }

    @Test
    fun `test empty adapter`() {
        Truth.assertThat(adapter.itemCount).isEqualTo(0)
    }

    @Test
    fun `test setData() with empty list`() {
        adapter.setData(emptyList())
        Truth.assertThat(adapter.itemCount).isEqualTo(0)
    }

    @Test
    fun `test setData() with non-empty list`() {
        val productList: List<Product> = kotlinFixture()()
        adapter.setData(productList)
        Truth.assertThat(adapter.itemCount).isEqualTo(productList.size)
    }
}

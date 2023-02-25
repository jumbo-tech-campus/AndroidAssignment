package com.abhinash.jumboandroidassignment.ui.products

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.abhinash.domain.models.Product
import com.abhinash.jumboandroidassignment.R
import com.abhinash.jumboandroidassignment.databinding.ProductListItemBinding
import com.abhinash.jumboandroidassignment.provider.ImageLoaderProvider

class ProductListRecyclerViewAdapter(
    private val listener: ProductClickListener,
    private val imageLoadProvider: ImageLoaderProvider
) :
    RecyclerView.Adapter<ProductListRecyclerViewAdapter.ProductListItemViewHolder>() {
    private var items = mutableListOf<Product>()

    fun setData(products: List<Product>) {
        items = products.toMutableList()
    }

    override fun getItemCount(): Int = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductListItemViewHolder {
        return ProductListItemViewHolder(
            ProductListItemViewHolder.binding(parent),
            imageLoadProvider
        )
    }

    override fun onBindViewHolder(holder: ProductListItemViewHolder, position: Int) {
        holder.bind(items[position], listener)
    }

    class ProductListItemViewHolder(
        private val binding: ProductListItemBinding,
        private val imageLoadProvider: ImageLoaderProvider
    ) : RecyclerView.ViewHolder(binding.root) {

        companion object {
            fun binding(parent: ViewGroup) = ProductListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        }

        fun bind(product: Product, listener: ProductClickListener) {
            with(binding) {
                imageLoadProvider.load(
                    productImageView,
                    product.productImage.url,
                    R.drawable.ic_add_to_cart
                )
                titleTextView.text = product.title
                descriptionTextView.text = product.quantity
                price.text = product.productPrices.price.amount.toString()
                unitPrice.text = root.context.getString(R.string.unit_price, product.productPrices.unitPrice.price.amount)
                addToCartButton.setOnClickListener { listener.onAddToCartClicked(product) }
            }
        }
    }

}

interface ProductClickListener {
    fun onAddToCartClicked(product: Product)
}
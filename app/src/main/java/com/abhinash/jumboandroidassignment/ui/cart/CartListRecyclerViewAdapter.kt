package com.abhinash.jumboandroidassignment.ui.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.abhinash.domain.models.CartProduct
import com.abhinash.jumboandroidassignment.R
import com.abhinash.jumboandroidassignment.databinding.CartItemBinding
import com.abhinash.jumboandroidassignment.provider.ImageLoaderProvider

class CartListRecyclerViewAdapter(
    private val listener: CartProductClickListener,
    private val imageLoadProvider: ImageLoaderProvider
) : RecyclerView.Adapter<CartListRecyclerViewAdapter.CartProductListItemViewHolder>() {
    private var items = mutableListOf<CartProduct>()
        private set(value) {
            field = value
            notifyDataSetChanged()
        }

    fun setData(cartProducts: List<CartProduct>) {
        items = cartProducts.toMutableList()
    }

    override fun getItemCount(): Int = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        CartProductListItemViewHolder(
            CartProductListItemViewHolder.binding(parent),
            imageLoadProvider
        )

    override fun onBindViewHolder(holder: CartProductListItemViewHolder, position: Int) {
        holder.bind(items[position], listener)
    }

    class CartProductListItemViewHolder(
        private val binding: CartItemBinding,
        private val imageLoadProvider: ImageLoaderProvider
    ) : RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun binding(parent: ViewGroup) = CartItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        }

        fun bind(cartProduct: CartProduct, listener: CartProductClickListener) {
            with(binding) {
                imageLoadProvider.load(
                    productImageView,
                    cartProduct.image,
                    R.drawable.ic_launcher_foreground
                )
                titleTextView.text = cartProduct.title
                quantityTextView.text =
                    root.context.getString(R.string.cart_quantity, cartProduct.quantity)
                totalPriceTextView.text =
                    root.context.getString(R.string.total_price, cartProduct.unitPriceInEur)
                removeFromCartButton.setOnClickListener {
                    listener.onRemoveFromCartClicked(
                        cartProduct
                    )
                }
            }
        }
    }
}

interface CartProductClickListener {
    fun onRemoveFromCartClicked(cartProduct: CartProduct)
}
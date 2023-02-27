package com.abhinash.designsystem

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.abhinash.designsystem.databinding.ViewCartBinding

class CartView
@JvmOverloads
constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    ConstraintLayout(context, attrs, defStyleAttr) {
    private var cartCount: Int = 0
    private val binding = ViewCartBinding.inflate(LayoutInflater.from(context), this)


    init {
        // Set the initial cart count to 0
        setCartCount(0)
    }

    fun setCartCount(count: Int) {
        cartCount = count
        binding.cartCountTextView.text = cartCount.toString()
    }
}

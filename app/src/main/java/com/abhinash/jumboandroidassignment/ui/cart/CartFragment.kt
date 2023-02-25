package com.abhinash.jumboandroidassignment.ui.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.abhinash.designsystem.extention.setVisibilityFromBoolean
import com.abhinash.domain.models.CartProduct
import com.abhinash.domain.models.Product
import com.abhinash.jumboandroidassignment.R
import com.abhinash.jumboandroidassignment.databinding.FragmentCartBinding
import com.abhinash.jumboandroidassignment.extentions.observe
import com.abhinash.jumboandroidassignment.provider.ImageLoaderProvider
import com.abhinash.jumboandroidassignment.ui.products.AddToCartState
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CartFragment : Fragment(), CartProductClickListener {
    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!
    private val viewModel: CartViewModel by viewModels()

    @Inject
    lateinit var imageLoaderProvider: ImageLoaderProvider
    private lateinit var listAdapter: CartListRecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCartBinding.inflate(inflater, container, false)
        listAdapter = CartListRecyclerViewAdapter(this, imageLoaderProvider)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerViewCartProductList.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewCartProductList.adapter = listAdapter
        observe(viewModel.cartProductList) {
            when (it) {
                ListViewState.Loading -> showLoadingState()
                is ListViewState.Error -> showError(it)
                is ListViewState.Success -> showProducts(it.cartProducts)
            }
        }
        observe(viewModel.removeFromCart) {
            when (it) {
                RemoveFromCartState.Success -> Toast.makeText(
                    requireContext(),
                    getString(R.string.removed_from_cart),
                    Toast.LENGTH_SHORT
                ).show()
                RemoveFromCartState.Failed -> Toast.makeText(
                    requireContext(),
                    getString(R.string.failed_to_remove_from_cart),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        viewModel.loadCart()
    }

    private fun showProducts(cartProducts: List<CartProduct>) = with(binding) {
        recyclerViewCartProductList.setVisibilityFromBoolean(true)
        progressBarList.setVisibilityFromBoolean(false)
        textViewListError.setVisibilityFromBoolean(false)
        listAdapter.setData(cartProducts)
        listAdapter.notifyItemRangeChanged(0, cartProducts.size)
    }

    private fun showError(state: ListViewState.Error) = with(binding) {
        recyclerViewCartProductList.setVisibilityFromBoolean(false)
        progressBarList.setVisibilityFromBoolean(false)
        val failure = state.failure as CartProductListFailure
        textViewListError.text = getString(failure.failureReasonResourceId)
        textViewListError.setVisibilityFromBoolean(true)
    }

    private fun showLoadingState() = with(binding) {
        recyclerViewCartProductList.setVisibilityFromBoolean(false)
        progressBarList.setVisibilityFromBoolean(true)
        textViewListError.setVisibilityFromBoolean(false)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onRemoveFromCartClicked(cartProduct: CartProduct) {
        viewModel.removeFromCart(cartProduct)
    }
}
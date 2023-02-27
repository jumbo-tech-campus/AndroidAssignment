package com.abhinash.jumboandroidassignment.ui.products

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.abhinash.designsystem.extention.setVisibilityFromBoolean
import com.abhinash.domain.models.Product
import com.abhinash.jumboandroidassignment.R
import com.abhinash.jumboandroidassignment.databinding.FragmentProductListBinding
import com.abhinash.jumboandroidassignment.extentions.observe
import com.abhinash.jumboandroidassignment.provider.ImageLoaderProvider
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ProductListFragment : Fragment(), ProductClickListener {
    private var _binding: FragmentProductListBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ProductListViewModel by viewModels()

    @Inject
    lateinit var imageLoaderProvider: ImageLoaderProvider
    private lateinit var listAdapter: ProductListRecyclerViewAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductListBinding.inflate(inflater, container, false)
        listAdapter = ProductListRecyclerViewAdapter(this, imageLoaderProvider)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerViewProductList.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewProductList.adapter = listAdapter
        binding.cartView.setOnClickListener {
            findNavController().navigate(
                ProductListFragmentDirections.actionFragmentProductListToFragmentCart()
            )
        }

        addObservers()
        viewModel.loadProducts()
        viewModel.loadCartCount()
    }

    private fun addObservers() {
        observe(viewModel.productList) {
            when (it) {
                ListViewState.Loading -> showLoadingState()
                is ListViewState.Error -> showError(it)
                is ListViewState.Success -> showProducts(it.products)
            }
        }
        observe(viewModel.cartProductsCount) {
            binding.cartView.setCartCount(it)
        }
        observe(viewModel.addToCart) {
            when (it) {
                AddToCartState.Success -> Toast.makeText(
                    requireContext(),
                    getString(R.string.added_to_cart),
                    Toast.LENGTH_SHORT
                ).show()
                AddToCartState.Failed -> Toast.makeText(
                    requireContext(),
                    getString(R.string.failed_to_add_to_cart),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun showProducts(products: List<Product>) = with(binding) {
        recyclerViewProductList.setVisibilityFromBoolean(true)
        progressBarList.setVisibilityFromBoolean(false)
        textViewListError.setVisibilityFromBoolean(false)
        listAdapter.setData(products)
        listAdapter.notifyItemRangeChanged(0, products.size)
    }

    private fun showError(state: ListViewState.Error) = with(binding) {
        recyclerViewProductList.setVisibilityFromBoolean(false)
        progressBarList.setVisibilityFromBoolean(false)
        val failure = state.failure as ProductListFailure
        textViewListError.text = getString(failure.failureReasonResourceId)
        textViewListError.setVisibilityFromBoolean(true)
    }

    private fun showLoadingState() = with(binding) {
        recyclerViewProductList.setVisibilityFromBoolean(false)
        progressBarList.setVisibilityFromBoolean(true)
        textViewListError.setVisibilityFromBoolean(false)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onAddToCartClicked(product: Product) {
        viewModel.addToCart(product)
    }
}
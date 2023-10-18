package com.seymasingin.e_commerceapp.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.seymasingin.e_commerceapp.R
import com.seymasingin.e_commerceapp.common.viewBinding
import com.seymasingin.e_commerceapp.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    private val binding by viewBinding(FragmentHomeBinding::bind)

    private val viewModel by viewModels<HomeViewModel>()

    private val productAdapter = ProductsAdapter(onFavClick = ::onFavClick, onProductClick = ::onProductClick)
    private val saleAdapter = SaleProductsAdapter(onSaleClick = ::onSaleClick)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getProducts()
        viewModel.getSaleProducts()

        with(binding) {
            rvAllProducts.adapter = productAdapter
            rvSaleProducts.adapter = saleAdapter
        }
        observeData()
        observeSaleData()
    }

    private fun observeData() {
        viewModel.productsList.observe(viewLifecycleOwner){ productList ->
            productAdapter.updateList(productList)
        }
    }

    private fun observeSaleData() {
        viewModel.productsList.observe(viewLifecycleOwner){ productList ->
            saleAdapter.updateList(productList)
        }
    }

    private fun onFavClick(id: Int) {
        null
    }

    private fun onSaleClick(id: Int) {
        findNavController().navigate(HomeFragmentDirections.homeToDetail(id))
    }

    private fun onProductClick(id: Int) {
        findNavController().navigate(HomeFragmentDirections.homeToDetail(id))
    }
}
package com.seymasingin.e_commerceapp.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.seymasingin.e_commerceapp.R
import com.seymasingin.e_commerceapp.common.Resource
import com.seymasingin.e_commerceapp.common.gone
import com.seymasingin.e_commerceapp.common.viewBinding
import com.seymasingin.e_commerceapp.common.visible
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
        viewModel.productsList.observe(viewLifecycleOwner) {
            when (it) {
                Resource.Loading -> binding.progressBarAll.visible()

                is Resource.Success -> {
                    binding.progressBarAll.gone()
                    productAdapter.updateList(it.data)
                }

                is Resource.Fail -> {
                    binding.progressBarAll.gone()
                    Snackbar.make(requireView(), it.failMessage, 1000).show()
                }

                is Resource.Error -> {
                    binding.progressBarAll.gone()
                    Snackbar.make(requireView(), it.errorMessage, 1000).show()
                }
            }
        }
    }

    private fun observeSaleData() {
        viewModel.saleProductsList.observe(viewLifecycleOwner) {
            when (it) {
                Resource.Loading -> binding.progressBarSale.visible()

                is Resource.Success -> {
                    binding.progressBarSale.gone()
                    saleAdapter.updateList(it.data)
                }

                is Resource.Fail -> {
                    binding.progressBarSale.gone()
                    Snackbar.make(requireView(), it.failMessage, 1000).show()
                }

                is Resource.Error -> {
                    binding.progressBarSale.gone()
                    Snackbar.make(requireView(), it.errorMessage, 1000).show()
                }
            }
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
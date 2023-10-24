package com.seymasingin.e_commerceapp.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.seymasingin.e_commerceapp.R
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
            logOut.setOnClickListener{
                viewModel.logOut()
                findNavController().navigate(R.id.homeToSignIn)
            }
        }
        observeData()
        observeSaleData()
    }

    private fun observeData() = with(binding) {
        viewModel.productState.observe(viewLifecycleOwner) { state ->
            when (state) {
                HomeState.Loading -> progressBarAll.visible()

                is HomeState.SuccessState -> {
                    progressBarAll.gone()
                    productAdapter.updateList(state.products)
                }

                is HomeState.EmptyScreen -> {
                    progressBarAll.gone()
                    emptyWarning.visible()
                    emptyWarnText.visible()
                    emptyWarnText.text = state.failMessage
                }

                is HomeState.ShowPopUp -> {
                    progressBarAll.gone()
                    Snackbar.make(requireView(), state.errorMessage, 1000).show()
                }
            }
        }
    }

    private fun observeSaleData() = with(binding) {
        viewModel.saleProductsState.observe(viewLifecycleOwner) { state ->
            when (state) {
                HomeState.Loading -> binding.progressBarSale.visible()

                is HomeState.SuccessState -> {
                    progressBarSale.gone()
                    saleAdapter.updateList(state.products)
                }

                is HomeState.EmptyScreen -> {
                    progressBarSale.gone()
                    ////
                }

                is HomeState.ShowPopUp -> {
                    progressBarSale.gone()
                    Snackbar.make(requireView(), state.errorMessage, 1000).show()
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
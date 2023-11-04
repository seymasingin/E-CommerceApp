package com.seymasingin.e_commerceapp.ui.cart

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
import com.seymasingin.e_commerceapp.databinding.FragmentCartBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartFragment : Fragment(R.layout.fragment_cart) {

    private val binding by viewBinding(FragmentCartBinding::bind)

    private val viewModel by viewModels<CartViewModel>()

    private val cartAdapter = CartAdapter(onDeleteFromBasket = ::onDeleteFromBasket, onProductClick = ::onProductClick)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val userId = viewModel.userId

        viewModel.getCartProducts(userId)

        with(binding) {
            rvCart.adapter = cartAdapter

            clearBasket.setOnClickListener {
                viewModel.clearCart(userId)
            }
        }

        observeData()
    }

    private fun observeData() = with(binding) {
        viewModel.cartState.observe(viewLifecycleOwner) { state ->
            when (state) {
                CartState.Loading -> progressBarCart.visible()

                is CartState.SuccessState -> {
                    progressBarCart.gone()
                    cartAdapter.submitList(state.products)

                    btnComplete.setOnClickListener {
                        if(state.products.isNotEmpty()) {
                            findNavController().navigate(R.id.cartToPayment)
                        }
                    }
                }

                is CartState.EmptyScreen -> {
                    progressBarCart.gone()
                    rvCart.gone()
                    icCartEmpty.visible()
                    tvCartEmpty.visible()
                    tvCartEmpty.text = state.failMessage
                }

                is CartState.ShowPopUp -> {
                    progressBarCart.gone()
                    Snackbar.make(requireView(), state.errorMessage, 1000).show()
                }
            }
        }

        viewModel.productDeleteState.observe(viewLifecycleOwner) { state ->
            when(state) {
                is DeleteState.DeleteSuccess -> {
                    Snackbar.make(requireView(), state.successMessage, 1000).show()
                }
            }
        }

        viewModel.clearCartState.observe(viewLifecycleOwner) { state ->
            when(state) {
                is DeleteState.DeleteSuccess -> {
                    Snackbar.make(requireView(), state.successMessage, 1000).show()
                }
            }
        }
    }

    private fun onDeleteFromBasket(id: Int){
        viewModel.deleteFromCart(id)
    }

    private fun onProductClick(id: Int) {
        findNavController().navigate(CartFragmentDirections.cartToDetail(id))
    }
}
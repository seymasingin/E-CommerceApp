package com.seymasingin.e_commerceapp.ui.cart

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.snackbar.Snackbar
import com.seymasingin.e_commerceapp.R
import com.seymasingin.e_commerceapp.common.Resource
import com.seymasingin.e_commerceapp.common.gone
import com.seymasingin.e_commerceapp.common.viewBinding
import com.seymasingin.e_commerceapp.common.visible
import com.seymasingin.e_commerceapp.databinding.FragmentCartBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartFragment : Fragment(R.layout.fragment_cart) {

    private val binding by viewBinding(FragmentCartBinding::bind)

    private val viewModel by viewModels<CartViewModel>()

    private val cartAdapter = CartAdapter(onDeleteFromBasket = ::onDeleteFromBasket)

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

    private fun observeData() {
        viewModel.cartList.observe(viewLifecycleOwner) {
            when (it) {
                Resource.Loading -> binding.progressBarCart.visible()

                is Resource.Success -> {
                    binding.progressBarCart.gone()
                    cartAdapter.updateList(it.data)
                }

                is Resource.Fail -> {
                    binding.progressBarCart.gone()
                    Snackbar.make(requireView(), it.failMessage, 1000).show()
                }

                is Resource.Error -> {
                    binding.progressBarCart.gone()
                    Snackbar.make(requireView(), it.errorMessage, 1000).show()
                }
            }
        }
    }

    fun onDeleteFromBasket(id: Int) {
        viewModel.deleteFromCart(id)
    }
}
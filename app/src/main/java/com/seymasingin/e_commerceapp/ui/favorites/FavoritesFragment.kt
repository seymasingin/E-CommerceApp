package com.seymasingin.e_commerceapp.ui.favorites

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
import com.seymasingin.e_commerceapp.data.model.response.ProductUI
import com.seymasingin.e_commerceapp.databinding.FragmentFavoritesBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoritesFragment : Fragment(R.layout.fragment_favorites) {

    private val binding by viewBinding(FragmentFavoritesBinding::bind)

    private val viewModel by viewModels<FavoritesViewModel>()

    private val favAdapter = FavAdapter(onDeleteFromFav = ::onDeleteFromFav, onProductClick = ::onProductClick)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getFavorites()

        with(binding) {
            rvFav.adapter = favAdapter
        }

        observeData()
    }

    private fun observeData() = with(binding) {
        viewModel.favoritesState.observe(viewLifecycleOwner) { state ->
            when (state) {
                FavoritesState.Loading -> progressBarFav.visible()

                is FavoritesState.SuccessState -> {
                    progressBarFav.gone()
                    favAdapter.updateList(state.products)
                }

                is FavoritesState.EmptyScreen -> {
                    progressBarFav.gone()
                    ivEmpty.visible()
                    tvEmpty.visible()
                    rvFav.gone()
                    tvEmpty.text = state.failMessage
                }

                is FavoritesState.ShowPopUp -> {
                    progressBarFav.gone()
                    Snackbar.make(requireView(), state.errorMessage, 1000).show()
                }
            }
        }
    }

    private fun onDeleteFromFav(product: ProductUI) {
        viewModel.deleteFromFavorites(product)
    }

    private fun onProductClick(id: Int) {
        findNavController().navigate(FavoritesFragmentDirections.favToDetail(id))
    }
}
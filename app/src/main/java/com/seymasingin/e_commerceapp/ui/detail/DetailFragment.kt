package com.seymasingin.e_commerceapp.ui.detail

import android.graphics.Paint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.seymasingin.e_commerceapp.R
import com.seymasingin.e_commerceapp.common.gone
import com.seymasingin.e_commerceapp.common.viewBinding
import com.seymasingin.e_commerceapp.common.visible
import com.seymasingin.e_commerceapp.databinding.FragmentDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment(R.layout.fragment_detail) {

    private val binding by viewBinding(FragmentDetailBinding::bind)

    private val args by navArgs<DetailFragmentArgs>()

    private val viewModel by viewModels<DetailViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getProductDetail(args.id)

        observeData()

        with(binding) {
            fabBack.setOnClickListener {
                findNavController().navigateUp()
            }

            btnAddCart.setOnClickListener {
                viewModel.addToCart(viewModel.userId, args.id)
            }
        }
    }

    private fun observeData() = with(binding) {
        viewModel.detailState.observe(viewLifecycleOwner){ state ->
            when (state) {
                DetailState.Loading -> progressBar.visible()

                is DetailState.SuccessState -> {
                    progressBar.gone()
                    titleDetail.text = state.product.title
                    descriptionDetail.text = state.product.description

                    detailFav.setOnClickListener{
                        viewModel.setFavoriteState(state.product)
                    }

                    detailFav.setBackgroundResource(
                        if(state.product.isFav) R.drawable.fav_selected
                        else R.drawable.fav_unselected
                    )

                    priceDetail.text = "${state.product.price} £"
                    if(state.product.saleState){
                        priceDetail.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                        salePriceDetail.text = "${state.product.salePrice} £"
                        salePriceDetail.visibility = View.VISIBLE
                    } else {
                        priceDetail.paintFlags = 0
                        salePriceDetail.visibility = View.GONE
                    }

                    count.text = "${state.product.count} pieces left"
                    category.text = state.product.category
                    ratingBar.rating = state.product.rate.toFloat()
                    starNumber.text = state.product.rate.toString()

                    val images = listOf(state.product.imageOne, state.product.imageTwo, state.product.imageThree)
                    val imageAdapter = ImageAdapter(images)
                    viewPager2.adapter = imageAdapter
                }

                is DetailState.EmptyScreen -> {
                    binding.progressBar.gone()
                    icDetailEmpty.visible()
                    tvDetailEmpty.visible()
                    tvDetailEmpty.text = state.failMessage
                }

                is DetailState.ShowPopUp -> {
                    binding.progressBar.gone()
                    Snackbar.make(requireView(), state.errorMessage, 1000).show()
                }
            }
        }

        viewModel.addCartState.observe(viewLifecycleOwner) { state ->
            when(state) {
                is AddCartState.CartAddSuccess -> {
                    Snackbar.make(requireView(), state.successMessage, 1000).show()
                }

                is AddCartState.CartAddFail -> {
                    Snackbar.make(requireView(), state.failMessage, 1000).show()
                }
            }
        }
    }
}

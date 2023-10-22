package com.seymasingin.e_commerceapp.ui.detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.seymasingin.e_commerceapp.R
import com.seymasingin.e_commerceapp.common.Resource
import com.seymasingin.e_commerceapp.common.gone
import com.seymasingin.e_commerceapp.common.viewBinding
import com.seymasingin.e_commerceapp.common.visible
import com.seymasingin.e_commerceapp.databinding.FragmentDetailBinding
import androidx.navigation.fragment.findNavController
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
            btnAddCart.setOnClickListener {
                val userId = viewModel.userId
                viewModel.addToCart(userId, args.id)
            }

            fabBack.setOnClickListener {
                findNavController().navigateUp()
            }
        }
    }

    private fun observeData() {
        viewModel.productDetail.observe(viewLifecycleOwner){
            when (it) {
                Resource.Loading -> binding.progressBar.visible()

                is Resource.Success -> {
                    val product = it.data
                    with(binding) {
                        progressBar.gone()
                        titleDetail.text = product.title
                        descriptionDetail.text = product.description
                        priceDetail.text = "${product.price} £"
                        count.text = "${product.count} pieces left"
                        ratingBar.rating = product.rate?.toFloat()!!
                        star.text = product.rate.toString()

                        val images = listOf(product.imageOne, product.imageTwo, product.imageThree)
                        val imageAdapter = ImageAdapter(images)
                        viewPager2.adapter = imageAdapter
                    }
                }

                is Resource.Fail -> {
                    binding.progressBar.gone()
                    Snackbar.make(requireView(), it.failMessage, 1000).show()
                }

                is Resource.Error -> {
                    binding.progressBar.gone()
                    Snackbar.make(requireView(), it.errorMessage, 1000).show()
                }
            }
        }
    }
}

package com.seymasingin.e_commerceapp.ui.detail

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.seymasingin.e_commerceapp.MainApplication
import com.seymasingin.e_commerceapp.R
import com.seymasingin.e_commerceapp.common.viewBinding
import com.seymasingin.e_commerceapp.data.model.GetProductDetailResponse
import com.seymasingin.e_commerceapp.databinding.FragmentDetailBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailFragment : Fragment(R.layout.fragment_detail) {

    private val binding by viewBinding(FragmentDetailBinding::bind)

    private val args by navArgs<DetailFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getProductDetail(args.id)

        with(binding) {
                titleDetail.text = product.title
                descriptionDetail.text = product.description
                priceDetail.text = "${product.price} £"
                count.text = "${product.count} pieces left"
                ratingBar.rating = product.rate?.toFloat()!!
                star.text = product.rate.toString()
                btnAddCart.setOnClickListener {
                }

                fabBack.setOnClickListener {
                    requireActivity().onBackPressedDispatcher.onBackPressed()
                }

                val images = listOf(product.imageOne, product.imageTwo, product.imageThree)
                val imageAdapter = ImageAdapter(images)
                viewPager2.adapter = imageAdapter

        }
    }
}
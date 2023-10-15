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
    }
    fun getProductDetail(id: Int) {
        MainApplication.productService?.getProductDetail(id)?.enqueue(object : Callback<GetProductDetailResponse>{
            override fun onResponse(call: Call<GetProductDetailResponse>, response: Response<GetProductDetailResponse>) {
                val result = response.body()

                if (result?.status == 200 && result.product != null) {
                    val product = result.product
                    binding.apply {
                        titleDetail.text = product.title
                        descriptionDetail.text = product.description
                        priceDetail.text = "${product.price} £"
                        btnAddCart.setOnClickListener {

                        }
                        fabBack.setOnClickListener {
                            requireActivity().onBackPressedDispatcher.onBackPressed()
                        }
                        star.text = product.rate.toString()
                        val images = listOf(product.imageOne, product.imageTwo, product.imageThree)
                        val imageAdapter = ImageAdapter(images)
                        binding.viewPager2.adapter = imageAdapter
                    }
                }
                else{
                    Toast.makeText(requireContext(), result?.message, Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<GetProductDetailResponse>, t: Throwable) {
                Log.e("GetProductDetail", t.message.orEmpty())
            }
        })
    }
}
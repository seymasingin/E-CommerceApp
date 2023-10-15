package com.seymasingin.e_commerceapp.ui.home

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.seymasingin.e_commerceapp.MainApplication
import com.seymasingin.e_commerceapp.R
import com.seymasingin.e_commerceapp.common.viewBinding
import com.seymasingin.e_commerceapp.data.model.GetProductsResponse
import com.seymasingin.e_commerceapp.databinding.FragmentHomeBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HomeFragment : Fragment(R.layout.fragment_home) {

    private val binding by viewBinding(FragmentHomeBinding::bind)

    private val productAdapter = ProductsAdapter(onFavClick = ::onFavClick, onProductClick = ::onProductClick)
    private val saleAdapter = SaleProductsAdapter(onSaleClick = ::onSaleClick)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getsaleProducts()
        getProducts()

        with(binding) {
            rvAllProducts.adapter = productAdapter
            rvSaleProducts.adapter = saleAdapter
        }
    }
    private fun getProducts() {
        MainApplication.productService?.getProducts()?.enqueue(object : Callback<GetProductsResponse>{
            override fun onResponse(call: Call<GetProductsResponse>, response: Response<GetProductsResponse>) {
                val result = response.body()
                if (result?.status == 200) {
                    result.products?.let {
                        productAdapter.updateList(it)
                    }
                } else {
                    Toast.makeText(requireContext(), result?.message, Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<GetProductsResponse>, t: Throwable) {
                Log.e("GetProducts", t.message.orEmpty())
            }
        })
    }
    private fun getsaleProducts() {
        MainApplication.productService?.getSaleProducts()?.enqueue(object : Callback<GetProductsResponse>{
            override fun onResponse(call: Call<GetProductsResponse>, response: Response<GetProductsResponse>) {
                val result = response.body()

                if (result?.status == 200) {
                    result.products?.let {
                        saleAdapter.updateList(it)
                    }
                }
                else {
                    Toast.makeText(requireContext(), result?.message, Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<GetProductsResponse>, t: Throwable) {
                Log.e("GetSaleProducts", t.message.orEmpty())
            }
        })
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
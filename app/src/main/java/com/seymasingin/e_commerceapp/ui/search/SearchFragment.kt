package com.seymasingin.e_commerceapp.ui.search


import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.seymasingin.e_commerceapp.MainApplication
import com.seymasingin.e_commerceapp.R
import com.seymasingin.e_commerceapp.common.viewBinding
import com.seymasingin.e_commerceapp.data.model.GetProductsResponse
import com.seymasingin.e_commerceapp.databinding.FragmentSearchBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SearchFragment : Fragment(R.layout.fragment_search) {

    private val binding by viewBinding(FragmentSearchBinding::bind)

    private val searchAdapter = SearchAdapter(onProductClick = ::onProductClick, onFavClick = ::onFavClick)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //getSearchProducts(query)

        with(binding) {
            rvSearch.adapter = searchAdapter
        }
    }
    private fun getSearchProducts(query: String) {
        MainApplication.productService?.getSearchProduct(query)?.enqueue(object : Callback<GetProductsResponse>{
            override fun onResponse(call: Call<GetProductsResponse>, response: Response<GetProductsResponse>) {
                val result = response.body()

                if(result?.status == 200) {
                    result.products?.let {
                        searchAdapter
                    }
                }
                else {
                }
            }
            override fun onFailure(call: Call<GetProductsResponse>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }
    private fun onFavClick(id: Int) {
        null
    }
    private fun onProductClick(id: Int) {
        findNavController().navigate(SearchFragmentDirections.searchToDetail(id))
    }
}
package com.seymasingin.e_commerceapp.ui.cart


import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.seymasingin.e_commerceapp.MainApplication
import com.seymasingin.e_commerceapp.R
import com.seymasingin.e_commerceapp.common.viewBinding
import com.seymasingin.e_commerceapp.data.model.GetProductsResponse
import com.seymasingin.e_commerceapp.databinding.FragmentCartBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class CartFragment : Fragment(R.layout.fragment_cart) {

    private val binding by viewBinding(FragmentCartBinding::bind)

    private val cartAdapter = CartAdapter(onDeleteFromBasket = ::onDeleteFromBasket)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //getCartProducts(userId)

        with(binding) {
            rvCart.adapter = cartAdapter
        }
    }

    fun getCartProducts(userId: Int) {
        MainApplication.productService?.getCartProducts(userId)?.enqueue(object : Callback<GetProductsResponse>{
            override fun onResponse(call: Call<GetProductsResponse>, response: Response<GetProductsResponse>) {
                val result = response.body()
                if (result?.status == 200) {
                    result.products?.let{
                        cartAdapter.updateList(it)
                    }
                }
                else{
                    Toast.makeText(requireContext(), result?.message, Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<GetProductsResponse>, t: Throwable) {
                Log.e("GetCartProducts", t.message.orEmpty())
            }

        })
    }

    fun onDeleteFromBasket(id: Int) {

    }
}
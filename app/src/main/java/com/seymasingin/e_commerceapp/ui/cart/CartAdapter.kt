package com.seymasingin.e_commerceapp.ui.cart

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.seymasingin.e_commerceapp.data.model.response.Product
import com.seymasingin.e_commerceapp.data.model.response.ProductListUI
import com.seymasingin.e_commerceapp.databinding.BasketCartBinding

class CartAdapter(private val onDeleteFromBasket: (Int) -> Unit, private val onProductClick: (Int) -> Unit) :
    RecyclerView.Adapter<CartAdapter.CartHolder>() {

    private val cartList = ArrayList<ProductListUI>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartHolder {
        val binding = BasketCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartHolder(binding, onDeleteFromBasket, onProductClick)
    }

    override fun onBindViewHolder(holder: CartHolder, position: Int) {
        holder.bind(cartList[position])
    }

    class CartHolder(
        private val binding: BasketCartBinding,
        private val onDeleteFromBasket: (Int) -> Unit,
        private val onProductClick: (Int) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(product: ProductListUI) {
            with(binding) {
                basketCartTitle.text = product.title
                basketCartPrice.text = "${product.price} £"
                if (product.saleState == true) {
                    basketCartPrice.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                    basketSalePrice.text = "${product.salePrice} £"
                    basketSalePrice.visibility = View.VISIBLE
                } else {
                    basketCartPrice.paintFlags = 0
                    basketSalePrice.visibility = View.GONE
                }
                Glide.with(basketCartImage).load(product.imageOne).into(basketCartImage)
                basketDeleteProduct.setOnClickListener {
                    onDeleteFromBasket(product.id ?: 1)
                }
                root.setOnClickListener {
                    onProductClick(product.id ?: 1)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return cartList.size
    }

    fun updateList(list: List<ProductListUI>) {
        cartList.clear()
        cartList.addAll(list)
        notifyItemRangeChanged(0, list.size)
    }
}
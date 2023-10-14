package com.seymasingin.e_commerceapp.ui.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.seymasingin.e_commerceapp.data.model.Product
import com.seymasingin.e_commerceapp.databinding.BasketCartBinding

class CartAdapter(private val onDeleteFromBasket: (Int) -> Unit): RecyclerView.Adapter<CartAdapter.CartHolder>() {

    private val cartList = ArrayList<Product>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartHolder {
        val binding = BasketCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartHolder(binding, onDeleteFromBasket)
    }

    override fun onBindViewHolder(holder: CartHolder, position: Int) {
        holder.bind(cartList[position])
    }

    class CartHolder(private val binding: BasketCartBinding,
                     private val onDeleteFromBasket: (Int) -> Unit):RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product) {
            with(binding) {
                basketCartTitle.text = product.title
                basketCartPrice.text="${product.price} £"
                Glide.with(basketCartImage).load(product.imageOne).into(basketCartImage)
                basketDeleteProduct.setOnClickListener {
                    onDeleteFromBasket(product.id ?:1)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return cartList.size
    }

    fun updateList(list:List<Product>) {
        cartList.clear()
        cartList.addAll(list)
        notifyItemRangeChanged(0, list.size)
    }
}
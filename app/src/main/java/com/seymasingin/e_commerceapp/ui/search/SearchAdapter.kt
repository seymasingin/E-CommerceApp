package com.seymasingin.e_commerceapp.ui.search

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.seymasingin.e_commerceapp.data.model.Product
import com.seymasingin.e_commerceapp.databinding.HomeCartBinding

class SearchAdapter(
    private val onFavClick: (Int) -> Unit,
    private val onProductClick: (Int) -> Unit
) : RecyclerView.Adapter<SearchAdapter.CardHolder>() {

    private val searchList = ArrayList<Product>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardHolder {
        val binding = HomeCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CardHolder(binding, onFavClick, onProductClick)
    }

    override fun onBindViewHolder(holder: CardHolder, position: Int) {
        holder.bind(searchList[position])
    }

    class CardHolder(
        private val binding: HomeCartBinding,
        private val onFavClick: (Int) -> Unit,
        private val onProductClick: (Int) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(product: Product) {
            with(binding) {
                productTitle.text = product.title
                productCat.text = product.category
                productPrice.text = "${product.price} £"
                if (product.saleState == true) {
                    productPrice.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                    productSale.text = "${product.salePrice} £"
                    productSale.visibility = View.VISIBLE
                }
                else{
                    productPrice.paintFlags = 0
                    productSale.visibility = View.GONE
                }
                Glide.with(productImg1).load(product.imageOne).into(productImg1)
                productFav.setOnClickListener {
                    onFavClick(product.id ?: 1)
                }
                root.setOnClickListener {
                    onProductClick(product.id ?: 1)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return searchList.size
    }

    fun updateList(list: List<Product>) {
        searchList.clear()
        searchList.addAll(list)
        notifyDataSetChanged()
    }
}
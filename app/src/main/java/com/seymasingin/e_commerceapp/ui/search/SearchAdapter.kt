package com.seymasingin.e_commerceapp.ui.search

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.seymasingin.e_commerceapp.common.gone
import com.seymasingin.e_commerceapp.common.visible
import com.seymasingin.e_commerceapp.data.model.response.ProductUI
import com.seymasingin.e_commerceapp.databinding.SearchCartBinding

class SearchAdapter(
    private val onProductClick: (Int) -> Unit
) : RecyclerView.Adapter<SearchAdapter.CardHolder>() {

    private val searchList = ArrayList<ProductUI>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardHolder {
        val binding = SearchCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CardHolder(binding, onProductClick)
    }

    override fun onBindViewHolder(holder: CardHolder, position: Int) {
        holder.bind(searchList[position])
    }

    class CardHolder(
        private val binding: SearchCartBinding,
        private val onProductClick: (Int) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(product: ProductUI) {
            with(binding) {
                productTitle.text = product.title
                productCat.text = product.category
                productPrice.text = "${product.price} £"
                if (product.saleState) {
                    productPrice.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                    productSale.text = "${product.salePrice} £"
                    productSale.visible()
                }
                else{
                    productPrice.paintFlags = 0
                    productSale.gone()
                }
                Glide.with(productImg1).load(product.imageOne).into(productImg1)

                root.setOnClickListener {
                    onProductClick(product.id)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return searchList.size
    }

    fun updateList(list: List<ProductUI>) {
        searchList.clear()
        searchList.addAll(list)
        notifyItemRangeChanged(0, list.size)
    }

    fun clearList() {
        searchList.clear()
        notifyDataSetChanged()
    }
}
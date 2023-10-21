package com.seymasingin.e_commerceapp.ui.home

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.seymasingin.e_commerceapp.data.model.Product
import com.seymasingin.e_commerceapp.databinding.HomeCartBinding

class ProductsAdapter(
    private val onFavClick: (Int) -> Unit,
    private val onProductClick: (Int) -> Unit
) : RecyclerView.Adapter<ProductsAdapter.ProductsHolder>() {

    private val productList = ArrayList<Product>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsHolder {
        val binding = HomeCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductsHolder(binding, onFavClick, onProductClick)
    }

    override fun onBindViewHolder(holder: ProductsHolder, position: Int) {
        holder.bind(productList[position])
    }

    class ProductsHolder(
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
                } else {
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
        return productList.size
    }

    fun updateList(list: List<Product>) {
        productList.clear()
        productList.addAll(list)
        notifyItemRangeChanged(0, list.size)
    }

}
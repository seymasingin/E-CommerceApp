package com.seymasingin.e_commerceapp.ui.home

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.seymasingin.e_commerceapp.R
import com.seymasingin.e_commerceapp.common.gone
import com.seymasingin.e_commerceapp.common.visible
import com.seymasingin.e_commerceapp.data.model.response.ProductUI
import com.seymasingin.e_commerceapp.databinding.HomeCartBinding

class ProductsAdapter(
    private val onFavClick: (ProductUI) -> Unit,
    private val onProductClick: (Int) -> Unit
) : ListAdapter<ProductUI, ProductsAdapter.ProductsHolder>(ProductDiffUtilCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsHolder {
        return ProductsHolder(
            HomeCartBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            onFavClick,
            onProductClick
        )
    }

    override fun onBindViewHolder(holder: ProductsHolder, position: Int) =holder.bind(getItem(position))

    class ProductsHolder(
        private val binding: HomeCartBinding,
        private val onFavClick: (ProductUI) -> Unit,
        private val onProductClick: (Int) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(product: ProductUI) {
            with(binding) {
                productTitle.text = product.title
                productCat.text = product.category

                productFav.setBackgroundResource(
                    if(product.isFav) R.drawable.fav_selected
                    else R.drawable.fav_unselected
                )

                productPrice.text = "${product.price} £"
                if (product.saleState) {
                    productPrice.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                    productSale.text = "${product.salePrice} £"
                    productSale.visible()
                } else {
                    productPrice.paintFlags = 0
                    productSale.gone()
                }

                Glide.with(productImg1).load(product.imageOne).into(productImg1)
                productFav.setOnClickListener {
                    onFavClick(product)
                }

                root.setOnClickListener {
                    onProductClick(product.id)
                }
            }
        }
    }

    class ProductDiffUtilCallBack : DiffUtil.ItemCallback<ProductUI>() {
        override fun areItemsTheSame(oldItem: ProductUI, newItem: ProductUI): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ProductUI, newItem: ProductUI): Boolean {
            return oldItem == newItem
        }
    }
}
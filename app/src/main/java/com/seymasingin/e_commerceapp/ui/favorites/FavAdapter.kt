package com.seymasingin.e_commerceapp.ui.favorites

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.seymasingin.e_commerceapp.common.gone
import com.seymasingin.e_commerceapp.common.visible
import com.seymasingin.e_commerceapp.data.model.response.ProductUI
import com.seymasingin.e_commerceapp.databinding.FavCartBinding


class FavAdapter(private val onDeleteFromFav: (ProductUI) -> Unit,
                 private val onProductClick: (Int) -> Unit) :
    ListAdapter<ProductUI, FavAdapter.FavHolder>(FavProductDiffUtilCallBack())  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavHolder {
        return FavHolder(
            FavCartBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            onDeleteFromFav,
            onProductClick
        )
    }

    override fun onBindViewHolder(holder: FavHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class FavHolder(
        private val binding: FavCartBinding,
        private val onDeleteFromFav: (ProductUI) -> Unit,
        private val onProductClick: (Int) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(favProduct: ProductUI) {
            with(binding) {
                favTitle.text = favProduct.title
                favPrice.text = "${favProduct.price}"

                favPrice.text = "${favProduct.price} £"
                if (favProduct.saleState) {
                    favPrice.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                    salePriceFav.text = "${favProduct.salePrice} £"
                    salePriceFav.visible()
                } else {
                    favPrice.paintFlags = 0
                    salePriceFav.gone()
                }

                Glide.with(favImage).load(favProduct.imageOne).into(favImage)

                favDelete.setOnClickListener {
                    onDeleteFromFav(favProduct)
                }

                root.setOnClickListener {
                    onProductClick(favProduct.id)
                }
            }
        }
    }

    class FavProductDiffUtilCallBack : DiffUtil.ItemCallback<ProductUI>() {
        override fun areItemsTheSame(oldItem: ProductUI, newItem: ProductUI): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ProductUI, newItem: ProductUI): Boolean {
            return oldItem == newItem
        }
    }
}
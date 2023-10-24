package com.seymasingin.e_commerceapp.ui.favorites

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.seymasingin.e_commerceapp.data.model.response.FavProduct
import com.seymasingin.e_commerceapp.databinding.FavCartBinding


class FavAdapter(private val onDeleteFromFav: (Int) -> Unit,
                 private val onProductClick: (Int) -> Unit) :
    RecyclerView.Adapter<FavAdapter.FavHolder>() {

    private val favList = ArrayList<FavProduct>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavHolder {
        val binding = FavCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavHolder(binding, onDeleteFromFav, onProductClick)
    }

    override fun onBindViewHolder(holder: FavHolder, position: Int) {
        holder.bind(favList[position])
    }

    class FavHolder(
        private val binding: FavCartBinding,
        private val onDeleteFromFav: (Int) -> Unit,
        private val onProductClick: (Int) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(favProduct: FavProduct) {
            with(binding) {
                favTitle.text = favProduct.title
                favPrice.text = "${favProduct.price}"
                Glide.with(favImage).load(favProduct.imageOne).into(favImage)
                favDelete.setOnClickListener {
                    onDeleteFromFav(favProduct.id)
                }
                root.setOnClickListener {
                    onProductClick(favProduct.id)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return favList.size
    }

    fun updateList(list: List<FavProduct>) {
        favList.clear()
        favList.addAll(list)
        notifyItemRangeChanged(0, list.size)
    }
}
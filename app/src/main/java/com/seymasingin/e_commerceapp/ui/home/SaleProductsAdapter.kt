package com.seymasingin.e_commerceapp.ui.home

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.seymasingin.e_commerceapp.data.model.response.ProductUI
import com.seymasingin.e_commerceapp.databinding.SaleCartBinding

class SaleProductsAdapter(private val onSaleClick: (Int) -> Unit) :
    RecyclerView.Adapter<SaleProductsAdapter.SaleHolder>() {

    private val saleList = ArrayList<ProductUI>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SaleHolder {
        val binding = SaleCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SaleHolder(binding, onSaleClick)
    }

    override fun onBindViewHolder(holder: SaleHolder, position: Int) {
        holder.bind(saleList[position])
    }

    class SaleHolder(
        private val binding: SaleCartBinding,
        private val onSaleClick: (Int) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(product: ProductUI) {
            with(binding) {
                saleTitle.text = product.title
                productOldPrice.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                productOldPrice.text = "${product.price} £"
                salePrice.text = "${product.salePrice} £"
                Glide.with(saleImage).load(product.imageOne).into(saleImage)

                root.setOnClickListener {
                    onSaleClick(product.id)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return saleList.size
    }

    fun updateList(list: List<ProductUI>) {
        saleList.clear()
        saleList.addAll(list)
        notifyItemRangeChanged(0, list.size)
    }
}
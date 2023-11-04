package com.seymasingin.e_commerceapp.ui.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.seymasingin.e_commerceapp.databinding.ImageSliderBinding

class ImageAdapter :
    RecyclerView.Adapter<ImageAdapter.PageViewHolder>() {

    private val imageList = ArrayList<String>()

    class PageViewHolder(val binding: ImageSliderBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(imageUrl: String) {
            Glide.with(binding.img).load(imageUrl).centerCrop().into(binding.img)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PageViewHolder {
        val binding = ImageSliderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PageViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return imageList.size
    }

    override fun onBindViewHolder(holder: PageViewHolder, position: Int) {
        imageList[position].let {
            holder.bind(it)
        }
    }

    fun updateList(list: List<String>) {
        imageList.clear()
        imageList.addAll(list)
        notifyItemRangeChanged(0, list.size)
    }
}
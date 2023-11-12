package com.seymasingin.e_commerceapp.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.seymasingin.e_commerceapp.databinding.CategoryTextBinding

class CategoryAdapter(private val onCategoryClick: (String) -> Unit) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

  private val categories= ArrayList<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val binding = CategoryTextBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryViewHolder(binding, onCategoryClick)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(categories[position])
    }

    override fun getItemCount(): Int {
        return categories.size
    }

    class CategoryViewHolder(
        private val binding: CategoryTextBinding,
        private val onCategoryClick: (String) -> Unit
        ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(category: String) {
            with(binding) {
                textCat.text = category

                root.setOnClickListener {
                    onCategoryClick(category)
                }
            }

        }
    }

    fun updateList(list: List<String>) {
        categories.clear()
        categories.addAll(list)
        notifyItemRangeChanged(0, list.size)
    }
}
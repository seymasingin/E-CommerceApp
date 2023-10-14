package com.seymasingin.e_commerceapp.ui.favorites


import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.seymasingin.e_commerceapp.R
import com.seymasingin.e_commerceapp.common.viewBinding
import com.seymasingin.e_commerceapp.databinding.FragmentFavoritesBinding


class FavoritesFragment : Fragment(R.layout.fragment_favorites) {

    private val binding by viewBinding(FragmentFavoritesBinding::bind)

    private val favAdapter = FavAdapter(onDeleteFromFav = ::onDeleteFromFav)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getFavProducts()

        with(binding) {
            rvFav.adapter = favAdapter
        }
    }

    fun getFavProducts() {
        
    }

    fun onDeleteFromFav(id: Int) {

    }

}
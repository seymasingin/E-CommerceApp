package com.seymasingin.e_commerceapp.ui.search

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.seymasingin.e_commerceapp.R
import com.seymasingin.e_commerceapp.common.Resource
import com.seymasingin.e_commerceapp.common.gone
import com.seymasingin.e_commerceapp.common.viewBinding
import com.seymasingin.e_commerceapp.common.visible
import com.seymasingin.e_commerceapp.databinding.FragmentSearchBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment(R.layout.fragment_search) {

    private val binding by viewBinding(FragmentSearchBinding::bind)

    private val viewModel by viewModels<SearchViewModel>()

    private val searchAdapter = SearchAdapter(onProductClick = ::onProductClick, onFavClick = ::onFavClick)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    var query = query.orEmpty()
                    viewModel.getSearchProducts(query)
                    return true
                }
                override fun onQueryTextChange(newText: String?): Boolean {
                    if (newText != null) {
                        viewModel.getSearchProducts(newText)
                    }
                    return true
                }
            })
            rvSearch.adapter = searchAdapter
        }

        observeData()
    }

    private fun observeData() {
        viewModel.searchProductsList.observe(viewLifecycleOwner){
            when (it) {
                Resource.Loading -> binding.progressBarSearch.visible()

                is Resource.Success -> {
                    binding.progressBarSearch.gone()
                    searchAdapter.updateList(it.data)
                }

                is Resource.Fail -> {
                    binding.progressBarSearch.gone()
                    Snackbar.make(requireView(), it.failMessage, 1000).show()
                }

                is Resource.Error -> {
                    binding.progressBarSearch.gone()
                    Snackbar.make(requireView(), it.errorMessage, 1000).show()
                }
            }
        }
    }

    private fun onFavClick(id: Int) {
        null
    }

    private fun onProductClick(id: Int) {
        findNavController().navigate(SearchFragmentDirections.searchToDetail(id))
    }
}
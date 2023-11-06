package com.seymasingin.e_commerceapp.ui.search

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.seymasingin.e_commerceapp.R
import com.seymasingin.e_commerceapp.common.gone
import com.seymasingin.e_commerceapp.common.viewBinding
import com.seymasingin.e_commerceapp.common.visible
import com.seymasingin.e_commerceapp.databinding.FragmentSearchBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment(R.layout.fragment_search) {

    private val binding by viewBinding(FragmentSearchBinding::bind)

    private val viewModel by viewModels<SearchViewModel>()

    private val searchAdapter = SearchAdapter(onProductClick = ::onProductClick)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    val query = query.orEmpty()
                    viewModel.getSearchProducts(query)
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    if (newText != null) {
                        if (newText.length >= 3) {
                            viewModel.getSearchProducts(newText)
                            tvSearchEmpty.gone()
                            icSearchEmpty.gone()
                        }
                        else {
                            searchAdapter.clearList()
                            tvSearchEmpty.gone()
                            icSearchEmpty.gone()
                        }
                    }
                    return true
                }
            })
            rvSearch.adapter = searchAdapter
        }

        observeData()
    }

    private fun observeData() = with(binding) {
        viewModel.searchState.observe(viewLifecycleOwner){ state ->
            when (state) {
                SearchState.Loading -> progressBarSearch.visible()

                is SearchState.SuccessState -> {
                    progressBarSearch.gone()
                    searchAdapter.updateList(state.products)
                }

                is SearchState.EmptyScreen -> {
                    progressBarSearch.gone()
                    icSearchEmpty.visible()
                    tvSearchEmpty.visible()
                    tvSearchEmpty.text = state.failMessage
                }

                is SearchState.ShowPopUp -> {
                    progressBarSearch.gone()
                    Snackbar.make(requireView(), state.errorMessage, 1000).show()
                }
            }
        }
    }

    private fun onProductClick(id: Int) {
        findNavController().navigate(SearchFragmentDirections.searchToDetail(id))
    }
}
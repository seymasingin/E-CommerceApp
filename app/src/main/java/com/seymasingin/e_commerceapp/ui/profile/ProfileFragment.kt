package com.seymasingin.e_commerceapp.ui.profile

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.seymasingin.e_commerceapp.R
import com.seymasingin.e_commerceapp.common.gone
import com.seymasingin.e_commerceapp.common.viewBinding
import com.seymasingin.e_commerceapp.common.visible
import com.seymasingin.e_commerceapp.databinding.FragmentProfileBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private val binding by viewBinding(FragmentProfileBinding::bind)

    private val viewModel by viewModels<ProfileViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.icLogout.setOnClickListener {
            viewModel.logOut()
        }

        observeData()
    }

    private fun observeData() = with(binding) {
        viewModel.userState.observe(viewLifecycleOwner) {state ->
            when(state) {

                ProfileState.Loading -> progressBarProfile.visible()

                is ProfileState.SuccessState -> {
                    progressBarProfile.gone()
                    tvName.text = state.user.name
                    tvSurname.text = state.user.surname
                    tvEmail.text = state.user.email
                }

                ProfileState.GoToSignIn -> {
                    findNavController().navigate(R.id.profileToSignIn)
                }
            }
        }
    }
}


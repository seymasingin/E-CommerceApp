package com.seymasingin.e_commerceapp.ui.payment

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.seymasingin.e_commerceapp.R
import com.seymasingin.e_commerceapp.common.gone
import com.seymasingin.e_commerceapp.common.viewBinding
import com.seymasingin.e_commerceapp.common.visible
import com.seymasingin.e_commerceapp.databinding.FragmentPaymentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PaymentFragment : Fragment(R.layout.fragment_payment) {

    private val binding by viewBinding(FragmentPaymentBinding::bind)

    private val viewModel by viewModels<PaymentViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {

            val months = resources.getStringArray(R.array.months)
            val monthAdapter = ArrayAdapter(requireContext(), R.layout.item_dropdown_menu, months)
            etMonth.setAdapter(monthAdapter)

            val years = resources.getStringArray(R.array.years)
            val yearAdapter = ArrayAdapter(requireContext(), R.layout.item_dropdown_menu, years)
            etYear.setAdapter(yearAdapter)

            etMonth.setOnItemClickListener { _, _, position, _ ->
                months[position]
            }

            etYear.setOnItemClickListener { _, _, position, _ ->
                years[position]
            }

            buy.setOnClickListener {
                val number = etNumber.text.toString()
                val cvc = etCVC.text.toString()
                val name = etCardName.text.toString()
                val city = etCity.text.toString()
                val town = etTown.text.toString()
                val address = etAddress.text.toString()
                val selectedMonth = etMonth.text.toString()
                val selectedYear = etYear.text.toString()

                viewModel.payment(number, cvc, name, city, town, address, selectedMonth, selectedYear)
            }
        }

        observeData()
    }

    private fun observeData() = with(binding) {
        viewModel.paymentState.observe(viewLifecycleOwner) {state ->
            when(state) {
                PaymentState.Loading -> progressBarPayment.visible()

                is PaymentState.SuccessState -> {
                    progressBarPayment.gone()
                    findNavController().navigate(R.id.paymentToSuccess)
                    viewModel.clearCart()
                }

                is PaymentState.ShowPopUp -> {
                    progressBarPayment.gone()
                    Snackbar.make(requireView(), state.errorMessage, 1000).show()
                }
            }
        }
    }
}
package com.seymasingin.e_commerceapp.ui.payment

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.seymasingin.e_commerceapp.R
import com.seymasingin.e_commerceapp.common.viewBinding
import com.seymasingin.e_commerceapp.databinding.FragmentPaymentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PaymentFragment : Fragment(R.layout.fragment_payment) {

    private val binding by viewBinding(FragmentPaymentBinding::bind)

    private val viewModel by viewModels<PaymentViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val months = resources.getStringArray(R.array.months)
        val monthAdapter = ArrayAdapter(requireContext(), R.layout.item_dropdown_menu, months)
        binding.etMonth.setAdapter(monthAdapter)

        val years = resources.getStringArray(R.array.years)
        val yearAdapter = ArrayAdapter(requireContext(), R.layout.item_dropdown_menu, years)
        binding.etYear.setAdapter(yearAdapter)

        with(binding) {
            etMonth.setOnItemClickListener { _, _, position, _ ->
                months[position]
            }

            etYear.setOnItemClickListener { _, _, position, _ ->
                years[position]
            }

            buy.setOnClickListener {
                val number = etNumber.text.toString()
                val name = etCardName.text.toString()
                val cvc = etCVC.text.toString()
                val city = etCity.text.toString()
                val town = etTown.text.toString()
                val address = etAddress.text.toString()

                if (checkFields(number, name, cvc, city, town, address)) {
                    findNavController().navigate(PaymentFragmentDirections.paymentToSuccess())
                    viewModel.clearCart(viewModel.userId)
                }
                else {
                    Snackbar.make(requireView(), "Fill in the required blanks", 1000).show()
                }
            }
        }
    }

    private fun checkFields(
        number: String,
        name: String,
        cvc: String,
        city: String,
        town: String,
        address: String
    ): Boolean {

        return when {

            number.length < 16 -> {
                    binding.tilCartNumber.error = "Card number cannot be less than 16"
                false
            }
            cvc.length < 3 -> {
                binding.tilCartNumber.isErrorEnabled = false
                binding.tilCVC.error = "CVC must consist of 3 digits"
                false
            }
            name.isEmpty() -> {
                binding.tilCartNumber.isErrorEnabled = false
                binding.tilCVC.isErrorEnabled = false
                binding.tilCartHolderName.error = "Name can not be empty!"
                false
            }
            city.isEmpty() -> {
                binding.tilCartNumber.isErrorEnabled = false
                binding.tilCVC.isErrorEnabled = false
                binding.tilCity.error = "City can not be empty!"
                false
            }
            town.isEmpty() -> {
                binding.tilCartNumber.isErrorEnabled = false
                binding.tilCVC.isErrorEnabled = false
                binding.tilTown.error = "Town can not be empty!"
                false
            }
            address.isEmpty() -> {
                binding.tilCartNumber.isErrorEnabled = false
                binding.tilCVC.isErrorEnabled = false
                binding.tilAddress.error = "Town can not be empty!"
                false
            }
            else -> true
        }
    }
}
package com.seymasingin.e_commerceapp.ui.payment

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.seymasingin.e_commerceapp.R
import com.seymasingin.e_commerceapp.common.viewBinding
import com.seymasingin.e_commerceapp.databinding.FragmentPaymentBinding

class PaymentFragment : Fragment(R.layout.fragment_payment) {

    private val binding by viewBinding(FragmentPaymentBinding::bind)

    private val monthList =
        listOf("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12")
    private val yearList = listOf("2023", "2024", "2025", "2026", "2027", "2028")

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val monthAdapter = ArrayAdapter(requireContext(), R.layout.fragment_payment, monthList)
        binding.etMonth.setAdapter(monthAdapter)

        val yearAdapter = ArrayAdapter(requireContext(), R.layout.fragment_payment, yearList)
        binding.etYear.setAdapter(yearAdapter)

        with(binding) {
            val number = etNumber.text.toString()
            val name = etCardName.text.toString()
            val cvc = etCVC.text.toString()
            val city = etCity.text.toString()
            val town = etTown.text.toString()
            val address = etAddress.text.toString()

            etMonth.setOnItemClickListener { _, _, position, _ ->
                monthList[position]
            }

            etYear.setOnItemClickListener { _, _, position, _ ->
                yearList[position]
            }

            if (checkFields(number, name, cvc, city, town, address)) {
                findNavController().navigate(PaymentFragmentDirections.paymentToSuccess())
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
                binding.tilCartNumber.error = "CVC must consist of 3 digits"
                false
            }
            name.isEmpty() -> {
                binding.tilCartHolderName.error = "Name can not be empty!"
                false
            }
            city.isEmpty() -> {
                binding.tilCity.error = "City can not be empty!"
                false
            }
            town.isEmpty() -> {
                binding.tilTown.error = "Town can not be empty!"
                false
            }
            address.isEmpty() -> {
                binding.tilAddress.error = "Town can not be empty!"
                false
            }
            else -> true
        }
    }
}
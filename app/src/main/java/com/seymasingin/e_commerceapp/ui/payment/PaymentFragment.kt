package com.seymasingin.e_commerceapp.ui.payment


import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.seymasingin.e_commerceapp.R
import com.seymasingin.e_commerceapp.common.viewBinding
import com.seymasingin.e_commerceapp.databinding.FragmentPaymentBinding

class PaymentFragment : Fragment(R.layout.fragment_payment) {

    private val binding by viewBinding(FragmentPaymentBinding::bind)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            val number = etNumber.text
            val name = etCardName.text
            val cvc = etCVC.text
            val city = etCity.text
            val town = etTown.text
            val address = etAddress.text
            /*if(checkFields(number, name, cvc)) {
                findNavController().navigate(PaymentFragmentDirections.paymentToSuccess())
            }*/
        }
    }
    private fun checkFields(number: Int, name: String, cvc: Int): Boolean {
        return when {
            number.toString().length < 16 -> {
                binding.tilCartNumber.error = "Card number cannot be less than 16"
                false
            }
            else -> true
        }
    }
}
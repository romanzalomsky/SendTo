package com.zalomsky.sendto.presentation.common

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.zalomsky.sendto.R
import com.zalomsky.sendto.databinding.FragmentSendTypeBinding
import com.zalomsky.sendto.databinding.FragmentStatisticsBinding

class SendTypeFragment : Fragment() {

    private var _binding: FragmentSendTypeBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSendTypeBinding.inflate(inflater, container, false)
        val view = binding.root

        sendTypeNavigation(view)

        return view
    }

    private fun sendTypeNavigation(
        view: View
    ){
        binding.buttonEmail.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_sendTypeFragment2_to_sendFragment)
        }

        binding.buttonSms.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_sendTypeFragment2_to_smsFragment)
        }
    }
}
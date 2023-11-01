package com.zalomsky.sendto.presentation.user.send.sms

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zalomsky.sendto.R
import com.zalomsky.sendto.databinding.FragmentSmsBinding

class SmsFragment : Fragment() {

    private var _binding: FragmentSmsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSmsBinding.inflate(layoutInflater, container, false)
        val view = binding.root


        return view
    }

}
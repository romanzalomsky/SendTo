package com.zalomsky.sendto.presentation.user.mysends

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.database.DatabaseReference
import com.zalomsky.sendto.R
import com.zalomsky.sendto.databinding.FragmentClientBinding
import com.zalomsky.sendto.databinding.FragmentMySendsBinding

class MySendsFragment : Fragment() {

    private lateinit var database: DatabaseReference
    private var _binding: FragmentMySendsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentMySendsBinding.inflate(inflater, container, false)
        val view = binding.root


        return view
    }
}
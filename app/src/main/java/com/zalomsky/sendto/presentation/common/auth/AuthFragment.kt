package com.zalomsky.sendto.presentation.common.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.Navigation
import com.zalomsky.sendto.R

class AuthFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_auth, container, false)

        view.findViewById<TextView>(R.id.go_to_sign_in)
            .setOnClickListener {
                Navigation.findNavController(view).navigate(R.id.action_authFragment_to_loginFragment)
            }

        view.findViewById<Button>(R.id.sign_up_button)
            .setOnClickListener {
                Navigation.findNavController(view).navigate(R.id.action_authFragment_to_statisticsFragment)
            }

        return view
    }

}
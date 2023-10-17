package com.zalomsky.sendto.presentation.common.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.Navigation
import com.zalomsky.sendto.R

class LoginFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_login, container, false)

        view.findViewById<TextView>(R.id.go_to_sign_up)
            .setOnClickListener {
                Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_authFragment)
            }

        view.findViewById<Button>(R.id.sign_in_button)
            .setOnClickListener {
                Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_statisticsFragment)
            }

        return view
    }

}
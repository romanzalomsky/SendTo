package com.zalomsky.sendto.presentation.common.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.zalomsky.sendto.R

class AuthFragment : Fragment() {

    private val viewModel: AuthFragmentViewModel by lazy {
        ViewModelProvider(requireActivity()).get(AuthFragmentViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_auth, container, false)
        val goToSignUp = view.findViewById<TextView>(R.id.go_to_sign_up)
        val signInButton = view.findViewById<TextView>(R.id.sign_in_button)

        goToSignUp.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_authFragment)
        }
        signInButton.setOnClickListener { SignIn(view) }

        return view
    }

    private fun SignIn(view: View){
        val email = view.findViewById<EditText>(R.id.sign_in_email_input).text.toString()
        val password = view.findViewById<EditText>(R.id.sign_in_password_input).text.toString()

        viewModel.onSignInClick(email, password, view)
    }
}
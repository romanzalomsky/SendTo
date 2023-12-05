package com.zalomsky.sendto.presentation.common.reg

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.zalomsky.sendto.R

class RegistrationFragment : Fragment() {

    private val viewModel: RegistrationFragmentViewModel by lazy {
        ViewModelProvider(requireActivity()).get(RegistrationFragmentViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_registration, container, false)
        val goToSignIn = view.findViewById<TextView>(R.id.go_to_sign_in)
        val signUpButton = view.findViewById<Button>(R.id.sign_up_button)

        goToSignIn.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_authFragment_to_loginFragment)
        }
        signUpButton.setOnClickListener { SignUp(view) }

        return view
    }

    private fun SignUp(view: View){

        val name = view.findViewById<EditText>(R.id.name_editText).text.toString()
        val phone = view.findViewById<EditText>(R.id.phone_editText).text.toString()
        val email = view.findViewById<EditText>(R.id.email_editText).text.toString()
        val password = view.findViewById<EditText>(R.id.password_editText).text.toString()

        viewModel.onRegistrationClick(name, email, phone, password, view)
    }
}

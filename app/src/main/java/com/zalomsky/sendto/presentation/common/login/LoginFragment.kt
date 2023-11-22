package com.zalomsky.sendto.presentation.common.login

import android.os.Bundle
import android.util.Log
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.zalomsky.sendto.R
import com.zalomsky.sendto.databinding.FragmentLoginBinding
import com.zalomsky.sendto.presentation.common.auth.AuthFragmentViewModel

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: LoginFragmentViewModel

    override fun onStart() {
        super.onStart()
        FirebaseAuth.getInstance()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        val view = binding.root

        viewModel = ViewModelProvider(requireActivity()).get(LoginFragmentViewModel::class.java)

        binding.goToSignUp.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_authFragment)
        }

        binding.signInButton.setOnClickListener { SignIn(view) }

        return view
    }

    private fun SignIn(
        view: View
    ){
        val email = binding.signInEmailInput.text.toString()
        val password = binding.signInPasswordInput.text.toString()

        viewModel.onSignInClick(email, password, view)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
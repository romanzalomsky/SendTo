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
import androidx.navigation.Navigation
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.zalomsky.sendto.R
import com.zalomsky.sendto.databinding.FragmentLoginBinding
import com.zalomsky.sendto.presentation.common.auth.AuthFragmentViewModel

class LoginFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val authFragmentViewModel: AuthFragmentViewModel by viewModels()

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

        auth = Firebase.auth

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

        if(checkAllField()){
            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
                if(it.isSuccessful){
                    Toast.makeText(requireActivity(), "Account: ${auth.currentUser?.email}", Toast.LENGTH_SHORT).show()
                    Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_statisticsFragment)
                }
                else{
                    Log.e("error: ", it.exception.toString())
                }
            }
        }
    }

    private fun checkAllField(): Boolean {

        val email = binding.signInEmailInput.text.toString()
        if(email == ""){
            binding.layoutSignInEmailInput.error = "This is required field"
            return false
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            binding.layoutSignInEmailInput.error = "Check email format"
            return false
        }
        if(binding.signInPasswordInput.text.toString() == ""){
            binding.layoutSignInPasswordInput.error = "This is required field"
            binding.layoutSignInPasswordInput.errorIconDrawable = null
            return false
        }
        if(binding.signInPasswordInput.length() <= 6){
            binding.layoutSignInPasswordInput.error = "Password should at least 6 numbers"
            binding.layoutSignInPasswordInput.errorIconDrawable = null
            return false
        }
        return true
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
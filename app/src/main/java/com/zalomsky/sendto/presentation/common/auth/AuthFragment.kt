package com.zalomsky.sendto.presentation.common.auth

import android.os.Bundle
import android.util.Log
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.zalomsky.sendto.R
import com.zalomsky.sendto.databinding.FragmentAuthBinding

class AuthFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private var _binding: FragmentAuthBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentAuthBinding.inflate(inflater, container, false)
        val view = binding.root
        auth = Firebase.auth


        binding.goToSignIn.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_authFragment_to_loginFragment)
        }
        binding.signUpButton.setOnClickListener {
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()

            if(checkAllField()){
                auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                    if(it.isSuccessful){
                        Toast.makeText(requireActivity(), "Account created successfully!", Toast.LENGTH_SHORT).show()
                        Navigation.findNavController(view).navigate(R.id.action_authFragment_to_statisticsFragment)
                    }
                    else {
                        Log.e("error: ", it.exception.toString())
                    }
                }
            }
        }
        return view
    }

    private fun checkAllField(): Boolean {

        val email = binding.emailEditText.text.toString()
        if(email == ""){
            binding.layoutEmailEditText.error = "This is required field"
            return false
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            binding.layoutEmailEditText.error = "Check email format"
            return false
        }
        if(binding.passwordEditText.text.toString() == ""){
            binding.layoutPasswordEditText.error = "This is required field"
            binding.layoutPasswordEditText.errorIconDrawable = null
            return false
        }
        if(binding.passwordEditText.length() <= 6){
            binding.layoutPasswordEditText.error = "Password should at least 6 numbers"
            binding.layoutPasswordEditText.errorIconDrawable = null
            return false
        }
        if(binding.confirmPasswordEditText.text.toString() == ""){
            binding.layoutConfirmPasswordEditText.error = "This is required field"
            binding.layoutConfirmPasswordEditText.errorIconDrawable = null
            return false
        }
        if(binding.passwordEditText.text.toString() != binding.confirmPasswordEditText.text.toString()){
            binding.layoutPasswordEditText.error = "Password don't match"
            return false
        }
        return true
    }
}

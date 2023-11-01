package com.zalomsky.sendto.presentation.common.auth

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
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.zalomsky.sendto.R
import com.zalomsky.sendto.SendToConstants
import com.zalomsky.sendto.databinding.FragmentAuthBinding
import com.zalomsky.sendto.domain.User

class AuthFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private lateinit var databaseReference: DatabaseReference

    private var _binding: FragmentAuthBinding? = null
    private val binding get() = _binding!!

    private val authFragmentViewModel: AuthFragmentViewModel by viewModels()

    override fun onStart() {
        super.onStart()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentAuthBinding.inflate(inflater, container, false)
        val view = binding.root

        databaseReference = FirebaseDatabase.getInstance().getReference(SendToConstants.USER_KEY)
        auth = Firebase.auth

        binding.goToSignIn.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_authFragment_to_loginFragment)
        }
        binding.signUpButton.setOnClickListener { SignUp(view) }
        return view
    }

    init {
        auth = FirebaseAuth.getInstance()
    }

    private fun SignUp(
        view: View
    ){
        val id = databaseReference.key.toString()
        val name = binding.nameEditText.text.toString()
        val phone = binding.phoneEditText.text.toString()
        val email = binding.emailEditText.text.toString()
        val password = binding.passwordEditText.text.toString()

        val newUser = User(id, name, phone, email, password)
        databaseReference.push().setValue(newUser)

        if(checkAllFields()){
            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                if(it.isSuccessful){
                    emailVerification()
                    val user: FirebaseUser? = auth.currentUser

                    if(user?.isEmailVerified == true){
                        Toast.makeText(requireActivity(), "Account created successfully!", Toast.LENGTH_SHORT).show()
                        Navigation.findNavController(view).navigate(R.id.action_authFragment_to_statisticsFragment)
                    }
                }
                else {
                    Log.e("error: ", it.exception.toString())
                }
            }
        }
    }

    private fun emailVerification(){

        val user: FirebaseUser? = auth.currentUser

        user?.sendEmailVerification()?.addOnCompleteListener {
            if(it.isSuccessful){
                Toast.makeText(requireActivity(), "Verify email", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun checkAllFields(): Boolean {

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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

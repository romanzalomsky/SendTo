package com.zalomsky.sendto.presentation.common.auth

import android.os.Bundle
import android.util.Log
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
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.zalomsky.sendto.R
import com.zalomsky.sendto.data.firebase.model.FirebaseConstants
import com.zalomsky.sendto.databinding.FragmentAuthBinding

class AuthFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference

    private var _binding: FragmentAuthBinding? = null
    private val binding get() = _binding!!

    private val authFragmentViewModel: AuthFragmentViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentAuthBinding.inflate(inflater, container, false)
        val view = binding.root

        database = Firebase.database.reference
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
        val name = binding.nameEditText.text.toString()
        val phone = binding.phoneEditText.text.toString()
        val email = binding.emailEditText.text.toString()
        val password = binding.passwordEditText.text.toString()

        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
            if(it.isSuccessful){

                database.child(FirebaseConstants.USER_KEY).child(FirebaseAuth.getInstance().currentUser!!.uid).child("name").setValue(name)
                database.child(FirebaseConstants.USER_KEY).child(FirebaseAuth.getInstance().currentUser!!.uid).child("phone").setValue(phone)
                database.child(FirebaseConstants.USER_KEY).child(FirebaseAuth.getInstance().currentUser!!.uid).child("email").setValue(email)
                database.child(FirebaseConstants.USER_KEY).child(FirebaseAuth.getInstance().currentUser!!.uid).child("password").setValue(password)

                emailVerification()
                val currentUser: FirebaseUser? = auth.currentUser

                if(currentUser?.isEmailVerified == true){

                    Toast.makeText(requireActivity(), "Account created successfully!", Toast.LENGTH_SHORT).show()
                    Navigation.findNavController(view).navigate(R.id.action_authFragment_to_statisticsFragment)
                }
            }
            else {
                Log.e("error: ", it.exception.toString())
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

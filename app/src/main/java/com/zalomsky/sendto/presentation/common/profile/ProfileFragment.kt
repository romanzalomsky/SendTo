package com.zalomsky.sendto.presentation.common.profile

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.Navigation
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import com.zalomsky.sendto.R
import com.zalomsky.sendto.SendToConstants
import com.zalomsky.sendto.databinding.FragmentAuthBinding
import com.zalomsky.sendto.databinding.FragmentProfileBinding
import com.zalomsky.sendto.domain.User

class ProfileFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private lateinit var databaseReference: DatabaseReference
    private var list = mutableListOf<User>()

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val view = binding.root

        auth = Firebase.auth

        databaseReference = FirebaseDatabase.getInstance().getReference(SendToConstants.USER_KEY)
        getDataFromDb()

        navigationProfile(view)
        return view
    }

    private fun getDataFromDb(){
        
        databaseReference.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(dataSnapshot: DataSnapshot) {

                for (ds in dataSnapshot.children){

                    databaseReference.child(SendToConstants.USER_KEY).get()

                    val name = ds.child("name").value.toString()
                    val email = ds.child("email").value.toString()
                    val phone = ds.child("phone").value.toString()
                    val password = ds.child("password").value.toString()

                    binding.nameEdit.setText(name)
                    binding.emailEdit.setText(/*email*/auth.currentUser?.email)
                    binding.phoneEdit.setText(phone)
                    binding.passwordEdit.setText(password)
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }
    private fun navigationProfile(
        view: View
    ){
        view.findViewById<Button>(R.id.logOutButton).setOnClickListener {
            Firebase.auth.signOut()

            Toast.makeText(requireActivity(), "log out!", Toast.LENGTH_SHORT).show()
            Navigation.findNavController(view).navigate(R.id.action_profileFragment_to_loginFragment)
        }
    }
}
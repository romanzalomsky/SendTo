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
        updateData()

        navigationProfile(view)
        return view
    }

    private fun getDataFromDb(){

        databaseReference = FirebaseDatabase.getInstance()
            .getReference(SendToConstants.USER_KEY)
            .child(FirebaseAuth.getInstance().currentUser!!.uid)
        databaseReference.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(dataSnapshot: DataSnapshot) {

                val name = dataSnapshot.child("name").value.toString()
                val email = dataSnapshot.child("email").value.toString()
                val phone = dataSnapshot.child("phone").value.toString()
                val password = dataSnapshot.child("password").value.toString()

                binding.nameEdit.setText(name)
                binding.emailEdit.setText(email)
                binding.phoneEdit.setText(phone)
                binding.passwordEdit.setText(password)
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

    //todo: Fix update data
    private fun updateData(){

        val name = binding.nameEdit.text.toString()

        binding.updateButton.setOnClickListener {
            val hashMap = hashMapOf<String, Any>()
            hashMap.put("name", name)
            databaseReference.updateChildren(hashMap).addOnSuccessListener {
                Toast.makeText(requireActivity(), "Data updated!", Toast.LENGTH_SHORT).show()
            }
        }
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
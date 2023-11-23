package com.zalomsky.sendto.presentation.common.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import com.zalomsky.sendto.R
import com.zalomsky.sendto.data.firebase.model.FirebaseConstants
import com.zalomsky.sendto.databinding.FragmentProfileBinding
import com.zalomsky.sendto.domain.model.User
import com.zalomsky.sendto.presentation.common.login.LoginFragmentViewModel

class ProfileFragment : Fragment() {

    private lateinit var database: DatabaseReference

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: ProfileFragmentViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val view = binding.root

        viewModel = ViewModelProvider(requireActivity()).get(ProfileFragmentViewModel::class.java)

        binding.profileToolBar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        getDataFromDb()

        navigationProfile(view)
        return view
    }

    private fun getDataFromDb(){

        database = FirebaseDatabase.getInstance()
            .getReference(FirebaseConstants.USER_KEY)
            .child(FirebaseAuth.getInstance().currentUser!!.uid)
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                val name = dataSnapshot.child("name").value.toString()
                val email = dataSnapshot.child("email").value.toString()
                val phone = dataSnapshot.child("phone").value.toString()
                val password = dataSnapshot.child("password").value.toString()

                binding.nameEdit.setText(name)
                binding.emailEdit.setText(email)
                binding.phoneEdit.setText(phone)
                binding.passwordEdit.setText(password)

                binding.apply {
                    binding.updateButton.setOnClickListener {
                        updateData()
                    }
                }
            }
            override fun onCancelled(error: DatabaseError) {}
        })
    }

    private fun updateData(){

        val name = binding.nameEdit.text.toString()
        val email = binding.emailEdit.text.toString()
        val phone = binding.phoneEdit.text.toString()
        val password = binding.passwordEdit.text.toString()

        val user = User(FirebaseAuth.getInstance().currentUser!!.uid, name, phone, email, password)

        viewModel.onUpdateButtonClick(name, email, phone, password)
        Toast.makeText(requireActivity(), "Данные обновлены", Toast.LENGTH_SHORT).show()

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
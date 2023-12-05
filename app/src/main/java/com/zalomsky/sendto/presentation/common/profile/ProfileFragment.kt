package com.zalomsky.sendto.presentation.common.profile

import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.zalomsky.sendto.R

class ProfileFragment : Fragment() {

    private val viewModel: ProfileFragmentViewModel by lazy {
        ViewModelProvider(requireActivity()).get(ProfileFragmentViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        val imageView = view.findViewById<ImageView>(R.id.profileImageView)
        val imagePlaceholder = BitmapFactory.decodeResource(resources, R.drawable.avatar)
        val toolBar = view.findViewById<Toolbar>(R.id.profileToolBar)

        toolBar.setNavigationOnClickListener { findNavController().navigateUp() }

        getUsersInfo(view)
/*        updateData(view)*/
        navigationProfile(view)

        return view
    }

    private fun getUsersInfo(view: View){ viewModel.onGetUsersData(view) }

    private fun updateData(name: String, phone: String, email: String, password: String, view: View){

        val updateButton = view.findViewById<Button>(R.id.updateButton)
        view.apply {
            updateButton.setOnClickListener {
                viewModel.onUpdateButtonClick(name, phone, email, password)
                Toast.makeText(requireActivity(), "Данные обновлены", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun navigationProfile(view: View){
        view.findViewById<Button>(R.id.logOutButton).setOnClickListener {
            Firebase.auth.signOut()

            Toast.makeText(requireActivity(), "Вы вышли из аккаунта!", Toast.LENGTH_SHORT).show()
            Navigation.findNavController(view).navigate(R.id.action_profileFragment_to_loginFragment)
        }
    }
}
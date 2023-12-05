package com.zalomsky.sendto.presentation.user.clients.template

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.zalomsky.sendto.R
import com.zalomsky.sendto.data.firebase.model.FirebaseConstants
import com.zalomsky.sendto.presentation.user.clients.add.AddAddressBookViewModel

class AddTemplateFragment : Fragment() {

    private lateinit var database: DatabaseReference

    private val viewModel: AddTemplateFragmentViewModel by lazy {
        ViewModelProvider(requireActivity()).get(AddTemplateFragmentViewModel::class.java)
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_add_template, container, false)
        val toolbar = view.findViewById<Toolbar>(R.id.createTemplateToolbar)
        val name = view.findViewById<EditText>(R.id.templateName)
        val text = view.findViewById<EditText>(R.id.templateText)
        val createButton = view.findViewById<Button>(R.id.createTemplateButton)

        toolbar.setNavigationOnClickListener { findNavController().navigateUp() }

        database = Firebase.database
            .getReference(FirebaseConstants.USER_KEY)
            .child(FirebaseAuth.getInstance().currentUser!!.uid)

        createButton.setOnClickListener {

            val id = database.push().key!!
            val nameEdit = name.text.toString()
            val textEdit = text.text.toString()
            viewModel.onAddTemplate(id, nameEdit, textEdit)
            Toast.makeText(requireActivity(), "Шаблон добавлен", Toast.LENGTH_SHORT).show()
            findNavController().navigateUp()
        }

        return view
    }
}
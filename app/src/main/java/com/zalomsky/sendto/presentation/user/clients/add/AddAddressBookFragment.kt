package com.zalomsky.sendto.presentation.user.clients.add

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.zalomsky.sendto.R
import com.zalomsky.sendto.data.firebase.model.FirebaseConstants
import com.zalomsky.sendto.databinding.FragmentAddAddressBookBinding
import com.zalomsky.sendto.domain.model.AddressBook
import com.zalomsky.sendto.presentation.common.auth.AuthFragmentViewModel

class AddAddressBookFragment : Fragment() {

    private lateinit var database: DatabaseReference
    private var _binding: FragmentAddAddressBookBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: AddAddressBookViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentAddAddressBookBinding.inflate(inflater, container, false)
        val view = binding.root

        viewModel = ViewModelProvider(requireActivity()).get(AddAddressBookViewModel::class.java)

        database = Firebase.database
            .getReference(FirebaseConstants.USER_KEY)
            .child(FirebaseAuth.getInstance().currentUser!!.uid)

        createAddressBook(view)

        return view
    }

    private fun createAddressBook(
        view: View
    ){
        binding.createButton.setOnClickListener {
            val id = database.push().key!!
            val name = binding.nameAddressBookEditText.text.toString()

            viewModel.onAddAddressBook(id, name)
            Navigation.findNavController(view).navigate(R.id.action_addAddressBookFragment_to_addressBookFragment)
            Toast.makeText(requireActivity(), "Книга добавлена", Toast.LENGTH_SHORT).show()
        }
    }
}
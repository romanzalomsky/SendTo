package com.zalomsky.sendto.presentation.user.clients.add

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.runtime.key
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.zalomsky.sendto.R
import com.zalomsky.sendto.SendToConstants
import com.zalomsky.sendto.databinding.FragmentAddressBookBinding
import com.zalomsky.sendto.databinding.FragmentClientBinding
import com.zalomsky.sendto.domain.AddressBook
import com.zalomsky.sendto.domain.Client
import com.zalomsky.sendto.presentation.user.clients.RecycleViewAdapter

class ClientFragment : Fragment() {

    private lateinit var database: DatabaseReference
    private var _binding: FragmentClientBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AddressBookViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentClientBinding.inflate(inflater, container, false)
        val view = binding.root

        database = Firebase.database
            .getReference(SendToConstants.USER_KEY)
            .child(FirebaseAuth.getInstance().currentUser!!.uid)
            .child(SendToConstants.ADDRESS_BOOK_KEY)

        database.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                binding.createClientButton.setOnClickListener {

                    val id = database.push().key!!
                    val email = binding.clientsEditText.text.toString()
                    val phone = binding.phoneClientEditText.text.toString()

                    val client = Client(id, email, phone)

                    val addressBookId = arguments?.getString("id")

                    database.child(addressBookId!!).child(SendToConstants.CLIENTS_KEY).child(id).setValue(client).addOnSuccessListener {
                        Navigation.findNavController(view).navigate(R.id.action_clientFragment_to_addressBookFragment)
                        Toast.makeText(requireActivity(), "Клиент добавлен", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            override fun onCancelled(error: DatabaseError) {}
        })

        return view
    }
}
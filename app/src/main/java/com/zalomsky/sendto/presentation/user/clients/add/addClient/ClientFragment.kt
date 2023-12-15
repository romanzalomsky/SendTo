package com.zalomsky.sendto.presentation.user.clients.add.addClient

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.zalomsky.sendto.R
import com.zalomsky.sendto.data.firebase.model.FirebaseConstants
import com.zalomsky.sendto.databinding.FragmentClientBinding
import com.zalomsky.sendto.presentation.user.clients.AddressBookViewModel

class ClientFragment : Fragment() {

    private lateinit var database: DatabaseReference
    private var _binding: FragmentClientBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: ClientFragmentViewModel

    private lateinit var addressBookViewModel: AddressBookViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentClientBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.toolbar3.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        val delete = view.findViewById<ImageView>(R.id.deleteAB)
        delete.setOnClickListener {
            database = Firebase.database
                .getReference(FirebaseConstants.USER_KEY)
                .child(FirebaseAuth.getInstance().currentUser!!.uid)
                .child(FirebaseConstants.ADDRESS_BOOK_KEY)
                .child(arguments?.getString("id").toString())
            database.addValueEventListener(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (newSnapshot in snapshot.getChildren()) {
                        newSnapshot.ref.removeValue()
                    }
                    val createClientButton = view.findViewById<Button>(R.id.createClientButton)
                    createClientButton.isEnabled = false
                    Toast.makeText(requireActivity(), "Книга удалена", Toast.LENGTH_SHORT).show()
                }
                override fun onCancelled(error: DatabaseError) {}
            })
        }

        viewModel = ViewModelProvider(requireActivity()).get(ClientFragmentViewModel::class.java)

        database = Firebase.database
            .getReference(FirebaseConstants.USER_KEY)
            .child(FirebaseAuth.getInstance().currentUser!!.uid)
/*            .child(FirebaseConstants.ADDRESS_BOOK_KEY)
            .child(arguments?.getString("id")!!)*/
            .child(FirebaseConstants.CLIENTS_KEY)

        database.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                binding.createClientButton.setOnClickListener {

                    val id = database.push().key!!
                    val email = binding.clientsEditText.text.toString()
                    val phone = binding.phoneClientEditText.text.toString()

                    val addressBookId = arguments?.getString("id")

                    viewModel.onAddClient(id, email, phone, addressBookId!!, view)
                    findNavController().navigateUp()
                }
            }
            override fun onCancelled(error: DatabaseError) {}
        })

        return view
    }
}
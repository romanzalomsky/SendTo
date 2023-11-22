package com.zalomsky.sendto.presentation.user.send.email

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.zalomsky.sendto.R
import com.zalomsky.sendto.data.firebase.model.FirebaseConstants
import com.zalomsky.sendto.databinding.FragmentSendBinding
import com.zalomsky.sendto.domain.model.AddressBook
import com.zalomsky.sendto.domain.model.Client
import com.zalomsky.sendto.domain.model.EmailMessages
import com.zalomsky.sendto.presentation.user.clients.adapter.RecycleViewAdapter

class SendEmailFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference

    private var _binding: FragmentSendBinding? = null
    private val binding get() = _binding!!

    private lateinit var list: ArrayList<AddressBook>

    private var selectedId: String = ""

    init {
        auth = FirebaseAuth.getInstance()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSendBinding.inflate(inflater, container, false)
        val view = binding.root

        database = Firebase.database.getReference(FirebaseConstants.USER_KEY).child(FirebaseAuth.getInstance().currentUser!!.uid)
        auth = Firebase.auth

        var userEmail = auth.currentUser?.email

        list = arrayListOf()

        val spinner: Spinner = binding.spinnerAddressBook

        val clientsMailList = mutableListOf<String>()

        database = FirebaseDatabase.getInstance().getReference(FirebaseConstants.USER_KEY)
            .child(FirebaseAuth.getInstance().currentUser!!.uid)
            .child(FirebaseConstants.ADDRESS_BOOK_KEY)

        database.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                list.clear()
                if(snapshot.exists()){
                    for (snap in snapshot.children){
                        val addressBook = snap.getValue(AddressBook::class.java)
                        addressBook?.let {
                            list.add(it)
                        }
                    }
                    val adapter = ArrayAdapter(
                        requireActivity(),
                        androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                        list.map { it.name }
                    )
                    spinner.adapter = adapter

                    spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                            binding.addressBookPlace.setText(parent?.getItemAtPosition(position).toString())

                            val selectedAddressBook = list[position]
                            selectedId = selectedAddressBook.id!!

                            database.child(selectedId).child(FirebaseConstants.CLIENTS_KEY)
                                .addListenerForSingleValueEvent(object : ValueEventListener{
                                override fun onDataChange(snapshot: DataSnapshot) {
                                    for (snap in snapshot.children){
                                        val clientsMail = snap.getValue(Client::class.java)?.email
                                        clientsMail?.let {
                                            clientsMailList.add(it)
                                        }
                                    }
                                }
                                override fun onCancelled(error: DatabaseError) {}
                            })
                        }
                        override fun onNothingSelected(parent: AdapterView<*>?) {}
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {}
        })

        binding.buttonSend.setOnClickListener {

            val allClientsMail = clientsMailList.joinToString(", ")

            val id = database.push().key!!
            val to = allClientsMail
            val from = userEmail!!
            val message = binding.tempateText.text.toString()

            val emailMessage = EmailMessages(id, to, from, message)

            val email = Intent(Intent.ACTION_SEND)
            email.putExtra(Intent.EXTRA_EMAIL, arrayOf(to))
            email.putExtra(Intent.EXTRA_SUBJECT, from)
            email.putExtra(Intent.EXTRA_TEXT, message)

            email.type = "message/rfc822"
            database.child(selectedId).child(FirebaseConstants.MESSAGE_KEY).child(id).setValue(emailMessage)

            startActivity(Intent.createChooser(email, "Выберите email клиент :"))

        }

        return view
    }

}
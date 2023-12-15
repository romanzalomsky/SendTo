package com.zalomsky.sendto.presentation.user.send.email

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.zalomsky.sendto.R
import com.zalomsky.sendto.data.firebase.model.FirebaseConstants
import com.zalomsky.sendto.domain.model.AddressBook
import com.zalomsky.sendto.domain.model.Template
import com.zalomsky.sendto.presentation.user.send.email.SendEmailFragment.Companion.Database.database
import com.zalomsky.sendto.presentation.user.send.email.SendEmailFragment.Companion.Database.list
import com.zalomsky.sendto.presentation.user.send.email.SendEmailFragment.Companion.Database.template_list

class SendEmailFragment : Fragment() {

    private val viewModel: SendEmailFragmentViewModel by lazy {
        ViewModelProvider(requireActivity()).get(SendEmailFragmentViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_send, container, false)
        val emailSendToolBar = view.findViewById<Toolbar>(R.id.emailSendToolBar)
        val spinner: Spinner = view.findViewById(R.id.spinnerAddressBook)
        val spinnerTemplate: Spinner = view.findViewById(R.id.spinnerTemplate)
        val buttonSend = view.findViewById<Button>(R.id.buttonSend)

        emailSendToolBar.setNavigationOnClickListener { findNavController().navigateUp() }

        database.child(FirebaseConstants.ADDRESS_BOOK_KEY).addValueEventListener(object : ValueEventListener {

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
                }
            }
            override fun onCancelled(error: DatabaseError) {}
        })
        getClientsFromAB(spinner)

        database.child(FirebaseConstants.TEMPLATE_KEY).addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                template_list.clear()
                if(snapshot.exists()){
                    for (snap in snapshot.children){
                        val template = snap.getValue(Template::class.java)
                        template?.let {
                            template_list.add(it)
                        }
                    }
                    val adapter = ArrayAdapter(
                        requireActivity(),
                        androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                        template_list.map { it.name }
                    )
                    spinnerTemplate.adapter = adapter
                }
            }
            override fun onCancelled(error: DatabaseError) {}
        })
        getTextFromTemplate(spinnerTemplate)

        buttonSend.setOnClickListener {

            val allClientsMail = clientsMailList.joinToString(", ")

            val id = database.push().key!!
            val to = allClientsMail
            val from = Database.auth.currentUser?.email!!

            val email = Intent(Intent.ACTION_SEND)
            email.putExtra(Intent.EXTRA_EMAIL, arrayOf(to))
            email.putExtra(Intent.EXTRA_SUBJECT, from)
            email.putExtra(Intent.EXTRA_TEXT, templateText)

            email.type = "message/rfc822"

            viewModel.onSendEmail(id, to, from, templateText, selectedName, selectedId)
            startActivity(Intent.createChooser(email, "Выберите email клиент :"))
        }

        return view
    }

    private fun getClientsFromAB(spinner: Spinner){

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

                val selectedAddressBook = list[position]

                selectedId = selectedAddressBook.id!!
                selectedName = selectedAddressBook.name!!

                database.child(FirebaseConstants.CLIENTS_KEY).addValueEventListener(object : ValueEventListener{
                    override fun onDataChange(snapshot: DataSnapshot) {
                        for (snap in snapshot.children){
                            val clientsMail = snap.child("email").value.toString()
                            if(selectedId == snap.child("addressBookId").value.toString()){
                                clientsMail?.let {
                                    clientsMailList.add(it)
                                }
                            }
                        }
                    }
                    override fun onCancelled(error: DatabaseError) {}
                })
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    private fun getTextFromTemplate(spinnerTemplate: Spinner){
        spinnerTemplate.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

                val selectedTemplate = template_list[position]

                templateText = selectedTemplate.text.toString()
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    companion object {

        private var selectedId: String = ""
        private var selectedName: String = ""
        private var clientsMailList = mutableListOf<String>()
        private var templateText: String = ""

        object Database {
            val auth: FirebaseAuth = Firebase.auth
            val database: DatabaseReference = Firebase.database.getReference(FirebaseConstants.USER_KEY).child(FirebaseAuth.getInstance().currentUser!!.uid)
            val list: ArrayList<AddressBook> = arrayListOf()
            val template_list: ArrayList<Template> = arrayListOf()
        }
    }
}
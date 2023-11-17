package com.zalomsky.sendto.presentation.user.send.email

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.zalomsky.sendto.R
import com.zalomsky.sendto.data.firebase.model.FirebaseConstants
import com.zalomsky.sendto.databinding.FragmentSendBinding

class SendEmailFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference

    private var _binding: FragmentSendBinding? = null
    private val binding get() = _binding!!

    override fun onStart() {
        super.onStart()
    }

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

        binding.templatePlace.visibility = View.GONE
        binding.addressBookPlace.visibility = View.GONE

        downloadAddressBook(view)

        binding.buttonSend.setOnClickListener {

            val id = database.push().key!!
/*            val to = binding.toEditField.text.toString()*/
            val from = userEmail!!
/*            val message = binding.message.text.toString()*/

/*            val emailMessage = EmailMessages(id, to, from, message)

            val email = Intent(Intent.ACTION_SEND)
            email.putExtra(Intent.EXTRA_EMAIL, arrayOf(to))
            email.putExtra(Intent.EXTRA_SUBJECT, from)
            email.putExtra(Intent.EXTRA_TEXT, message)

            email.type = "message/rfc822"

            database.child(SendToConstants.MESSAGE_KEY).child(id).setValue(emailMessage)

            startActivity(Intent.createChooser(email, "Выберите email клиент :"))*/

        }

        return view
    }

    private fun downloadAddressBook(
        view: View
    ){
        binding.downloadAddressBook.setOnClickListener {

            Navigation.findNavController(view).navigate(R.id.action_sendFragment_to_getAddressBookFragment)
            Toast.makeText(requireActivity(), "Адресная книга загружена", Toast.LENGTH_SHORT).show()
        }
    }

    private fun SendEmail(){

    }
}
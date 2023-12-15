package com.zalomsky.sendto.presentation.user.send.sms

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.Telephony
import android.telephony.SmsManager
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat.RECEIVER_EXPORTED
import androidx.core.content.ContextCompat.registerReceiver
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.zalomsky.sendto.data.firebase.model.FirebaseConstants
import com.zalomsky.sendto.databinding.FragmentSmsBinding
import com.zalomsky.sendto.domain.model.SmsMessage

class SmsFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference
    private var _binding: FragmentSmsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSmsBinding.inflate(layoutInflater, container, false)
        val view = binding.root

        database = Firebase.database.getReference(FirebaseConstants.USER_KEY).child(FirebaseAuth.getInstance().currentUser!!.uid)

        binding.smsToolBar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        var phoneMessage = binding.idEdtMessage.text.toString()

        if (activity?.checkSelfPermission(Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED
            || activity?.checkSelfPermission(Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED
            || activity?.checkSelfPermission(Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED
        ) {
            activity?.requestPermissions(
                arrayOf(Manifest.permission.RECEIVE_SMS,
                    Manifest.permission.SEND_SMS,
                    Manifest.permission.READ_SMS), PackageManager.PERMISSION_GRANTED
            )
        }

        val br = object : BroadcastReceiver() {
            override fun onReceive(p0: Context?, p1: Intent?) {
                for (sms in Telephony.Sms.Intents.getMessagesFromIntent(
                    p1
                )) {
                    val smsSender = sms.originatingAddress
                    val smsMessageBody = sms.displayMessageBody
                    if (smsSender == "+375293450059") {
                        phoneMessage = smsMessageBody
                        break
                    }
                }
            }
        }

        registerReceiver(
            requireContext(),
            br,
            IntentFilter("android.provider.Telephony.SMS_RECEIVED"),
            RECEIVER_EXPORTED
        )

        fun sendSms(phoneNumber: String, message: String) {
            val smsManager = SmsManager.getDefault()
            smsManager.sendTextMessage(phoneNumber, null, message, null, null)
        }


        binding.idBtnSendMessage.setOnClickListener {

            val id = database.push().key!!
            val phone = binding.idEdtPhone.text.toString()
            val phoneMessage = binding.idEdtMessage.text.toString()

            val smsMessage = SmsMessage(id, phone, "+375293450059", phoneMessage)

            try {
                database = FirebaseDatabase.getInstance().getReference(FirebaseConstants.USER_KEY)
                    .child(FirebaseAuth.getInstance().currentUser!!.uid)
                    .child(FirebaseConstants.SMS_MESSAGE_KEY)
                database.child(id).setValue(smsMessage)
                sendSms(phone, phoneMessage)
                Toast.makeText(requireActivity(), "Message Sent", Toast.LENGTH_LONG).show()
            } catch (e: Exception){
                Toast.makeText(requireActivity(), "Please enter all the data.. ${e.message}", Toast.LENGTH_LONG).show()
            }
        }

        return view
    }

}
package com.zalomsky.sendto.presentation.user.send.email

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zalomsky.sendto.databinding.FragmentSendBinding

class SendEmailFragment : Fragment() {

    private var _binding: FragmentSendBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSendBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.buttonSend.setOnClickListener {

            val onClickListener = View.OnClickListener {
                val to = binding.toEditField.text.toString()
                val from = binding.fromEditField.text.toString()
                val message = binding.message.text.toString()

                val email = Intent(Intent.ACTION_SEND)
                email.putExtra(Intent.EXTRA_EMAIL, arrayOf(to))
                email.putExtra(Intent.EXTRA_SUBJECT, from)
                email.putExtra(Intent.EXTRA_TEXT, message)

                email.type = "message/rfc822"

                startActivity(Intent.createChooser(email, "Выберите email клиент :"))
            }
            binding.buttonSend.setOnClickListener(onClickListener)
        }
        return view
    }
}
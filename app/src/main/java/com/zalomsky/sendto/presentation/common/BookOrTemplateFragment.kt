package com.zalomsky.sendto.presentation.common

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.widget.Toolbar
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.zalomsky.sendto.R

class BookOrTemplateFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_book_or_template, container, false)
        val toolbar = view.findViewById<Toolbar>(R.id.toolbarBookOrTemplate)
        val addressBookButton = view.findViewById<Button>(R.id.buttonAddressBook)
        val templateButton = view.findViewById<Button>(R.id.buttonTemplate)

        toolbar.setNavigationOnClickListener { findNavController().navigateUp() }

        addressBookButton.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_bookOrTemplateFragment_to_addAddressBookFragment)
        }

        templateButton.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_bookOrTemplateFragment_to_addTemplateFragment)
        }

        return view
    }
}
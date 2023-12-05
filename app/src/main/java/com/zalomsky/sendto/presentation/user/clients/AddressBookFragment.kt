package com.zalomsky.sendto.presentation.user.clients

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.widget.Toolbar
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.zalomsky.sendto.R
import com.zalomsky.sendto.data.firebase.model.FirebaseConstants
import com.zalomsky.sendto.domain.model.AddressBook
import com.zalomsky.sendto.presentation.user.clients.AddressBookFragment.Companion.Path.addressBookPath
import com.zalomsky.sendto.presentation.user.clients.adapter.RecycleViewAdapter

class AddressBookFragment : Fragment() {

    private lateinit var database: DatabaseReference

    private lateinit var recycleView: RecyclerView

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_address_book, container, false)
        val toolbar = view.findViewById<Toolbar>(R.id.addressBookToolBar)
        val listView = view.findViewById<RecyclerView>(R.id.listView)

        toolbar.setNavigationOnClickListener { findNavController().navigateUp() }

        showAddressBookList(view)
        navigationAddressBook(view)

        recycleView = listView
        recycleView.layoutManager = LinearLayoutManager(requireActivity())
        recycleView.setHasFixedSize(true)

        return view
    }

    private fun showAddressBookList(view: View){
        database = addressBookPath
        database.addValueEventListener(object : ValueEventListener{

            override fun onDataChange(snapshot: DataSnapshot) {
                list_addressBook.clear()

                for (snap in snapshot.children){
                    val data = snap.getValue(AddressBook::class.java)
                    list_addressBook.add(data!!)
                }
                val adapter = RecycleViewAdapter(list_addressBook)
                recycleView.adapter = adapter

                adapter.setOnItemClickListener(object : RecycleViewAdapter.onItemClickListener{
                    override fun onItemClick(position: Int) {

                        val bundle = bundleOf("id" to list_addressBook[position].id)
                        bundle.putString("id", list_addressBook[position].id)

                        Navigation.findNavController(view).navigate(R.id.action_addressBookFragment_to_clientFragment, bundle)
                    }
                })
            }
            override fun onCancelled(error: DatabaseError) {}
        })
    }

    private fun navigationAddressBook(
        view: View
    ){
        view.findViewById<ImageView>(R.id.addAddressBook).setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_addressBookFragment_to_bookOrTemplateFragment)
        }
    }

    companion object {

        private val list_addressBook: ArrayList<AddressBook> = arrayListOf()

        object Path {
            val addressBookPath = FirebaseDatabase.getInstance()
                .getReference(FirebaseConstants.USER_KEY)
                .child(FirebaseAuth.getInstance().currentUser!!.uid)
                .child(FirebaseConstants.ADDRESS_BOOK_KEY)
        }
    }
}
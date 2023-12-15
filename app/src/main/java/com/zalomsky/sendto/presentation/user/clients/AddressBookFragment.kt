package com.zalomsky.sendto.presentation.user.clients

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
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
import com.zalomsky.sendto.presentation.user.statistics.RecycleViewNew


class AddressBookFragment : Fragment() {

    private lateinit var database: DatabaseReference

    private lateinit var recycleView: RecyclerView

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_address_book, container, false)
        val toolbar = view.findViewById<Toolbar>(R.id.addressBookToolBar)
        val listView = view.findViewById<RecyclerView>(R.id.listView)
        val searchButton = view.findViewById<Button>(R.id.buttonSearch)
        val spinnerAB: Spinner = view.findViewById(R.id.spinnerAB)

        toolbar.setNavigationOnClickListener { findNavController().navigateUp() }

        showAddressBookList(view)
        navigationAddressBook(view)
        searchButton.setOnClickListener { searchAB(view) }
        onSorted(view, spinnerAB)


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

    private fun searchAB(view: View){
        database = addressBookPath
        database.addValueEventListener(object : ValueEventListener{

            override fun onDataChange(snapshot: DataSnapshot) {

                search_list_addressBook.clear()

                for (childSnapshot in snapshot.children) {
                    val data = childSnapshot.getValue(AddressBook::class.java)
                    data?.let { search_list_addressBook.add(it) }
                }

                val filteredList = ArrayList<AddressBook>(search_list_addressBook)
                val searchPer = view.findViewById<EditText>(R.id.findElement).text.toString()
                val query = searchPer
                if (query.isNotEmpty()) {
                    filteredList.clear()
                    for (data in search_list_addressBook) {
                        if (data.name?.contains(query, ignoreCase = true) == true) {
                            filteredList.add(data)
                        }
                    }
                }
                val adapter = RecycleViewNew(filteredList)
                recycleView.adapter = adapter
/*                val searchPer = view.findViewById<EditText>(R.id.findElement).text.toString()
                val addressBook = AddressBook()
                val filteredData = snapshot.children.filter { childSnapshot ->
                    childSnapshot.child(addressBook.name.toString()).getValue(String::class.java)?.contains(searchPer, ignoreCase = true) == true
                }*/

            }
            override fun onCancelled(error: DatabaseError) {}
        })
    }

    private fun onSorted(view: View, spinnerAB: Spinner){
        val adapter = ArrayAdapter(
            requireActivity(),
            androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
            sortedList.map { it }
        )
        spinnerAB.adapter = adapter

        spinnerAB.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

                val selectedSortedMethod = sortedList[position]

                if(selectedSortedMethod == "a-z"){
                    database = addressBookPath
                    database.addValueEventListener(object : ValueEventListener{
                        override fun onDataChange(snapshot: DataSnapshot) {
                            new_list_addressBook.clear()
                            for (childSnapshot in snapshot.children) {
                                val data = childSnapshot.getValue(AddressBook::class.java)
                                data?.let { new_list_addressBook.add(it) }
                            }

                            new_list_addressBook.sortBy { it.name }

                            recycleView.adapter = RecycleViewNew(new_list_addressBook)
                        }
                        override fun onCancelled(error: DatabaseError) {}
                    })
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

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
        val sortedList: ArrayList<String> = arrayListOf("", "a-z")
        private val new_list_addressBook: ArrayList<AddressBook> = arrayListOf()
        private val search_list_addressBook: ArrayList<AddressBook> = arrayListOf()

        object Path {
            val addressBookPath = FirebaseDatabase.getInstance()
                .getReference(FirebaseConstants.USER_KEY)
                .child(FirebaseAuth.getInstance().currentUser!!.uid)
                .child(FirebaseConstants.ADDRESS_BOOK_KEY)
        }
    }
}
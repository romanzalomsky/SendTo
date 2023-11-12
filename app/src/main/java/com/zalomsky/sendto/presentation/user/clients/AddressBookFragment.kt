package com.zalomsky.sendto.presentation.user.clients

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.zalomsky.sendto.R
import com.zalomsky.sendto.SendToConstants
import com.zalomsky.sendto.databinding.FragmentAddressBookBinding
import com.zalomsky.sendto.domain.AddressBook
import com.zalomsky.sendto.domain.User
import com.zalomsky.sendto.presentation.user.clients.add.AddressBookViewModel
import com.zalomsky.sendto.presentation.user.clients.add.ClientFragment

class AddressBookFragment : Fragment() {

    private lateinit var database: DatabaseReference

    private var _binding: FragmentAddressBookBinding? = null
    private val binding get() = _binding!!

    private lateinit var recycleView: RecyclerView
    private lateinit var list: ArrayList<AddressBook>

    private val viewModel: AddressBookViewModel by activityViewModels ()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentAddressBookBinding.inflate(inflater, container, false)
        val view = binding.root

        checkExists(view)
        navigationAddressBook(view)
/*        clientsCount(view)*/

        recycleView = binding.listView
        recycleView.layoutManager = LinearLayoutManager(requireActivity())
        recycleView.setHasFixedSize(true)

        list = arrayListOf()

        return view
    }

    private fun checkExists(
        view: View
    ){
        database = FirebaseDatabase.getInstance()
            .getReference(SendToConstants.USER_KEY)
            .child(FirebaseAuth.getInstance().currentUser!!.uid)
            .child(SendToConstants.ADDRESS_BOOK_KEY)

        database.addValueEventListener(object : ValueEventListener{

            override fun onDataChange(snapshot: DataSnapshot) {
                list.clear()
                if(snapshot.exists()){
                    binding.empty.visibility = View.GONE
                    recycleView.visibility = View.VISIBLE
                    for (snap in snapshot.children){
                        val data = snap.getValue(AddressBook::class.java)
                        list.add(data!!)
                    }
                    val adapter = RecycleViewAdapter(list)
                    recycleView.adapter = adapter

                    adapter.setOnItemClickListener(object : RecycleViewAdapter.onItemClickListener{
                        override fun onItemClick(position: Int) {

                            val bundle = bundleOf("id" to list[position].id)
                            bundle.putString("id", list[position].id)

                            Navigation.findNavController(view).navigate(R.id.action_addressBookFragment_to_clientFragment, bundle)
                        }
                    })

/*                    database.child(arguments?.getString("id")!!).child(SendToConstants.CLIENTS_KEY) //todo: fix this query
                    database.addValueEventListener(object : ValueEventListener{
                        override fun onDataChange(snapshot: DataSnapshot) {
                            view.findViewById<TextView>(R.id.amountClients).setText(snapshot.children.count().toString())
                        }
                        override fun onCancelled(error: DatabaseError) {}
                    })*/
                }
                else{
                    binding.empty.visibility = View.VISIBLE
                    recycleView.visibility = View.GONE
                }
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }

    private fun navigationAddressBook(
        view: View
    ){
        view.findViewById<ImageView>(R.id.addAddressBook).setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_addressBookFragment_to_addAddressBookFragment)
        }
    }
}
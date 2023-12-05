package com.zalomsky.sendto.presentation.user.mysends

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
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
import com.zalomsky.sendto.domain.model.EmailMessages
import com.zalomsky.sendto.presentation.user.mysends.MySendsFragment.Companion.Path.messagePath

class MySendsFragment : Fragment() {

    private lateinit var database: DatabaseReference

    private lateinit var recycleView: RecyclerView

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_my_sends, container, false)
        val toolbar = view.findViewById<Toolbar>(R.id.mySendsToolBar)
        val messageListView = view.findViewById<RecyclerView>(R.id.message_listView)

        toolbar.setNavigationOnClickListener { findNavController().navigateUp() }

        recycleView = messageListView
        recycleView.layoutManager = LinearLayoutManager(requireActivity())
        recycleView.setHasFixedSize(true)

        getAddressBookName()
/*        getAmountClients()*/

        return view
    }

    private fun getAddressBookName(){

        database = messagePath
        database.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                list_message.clear()
                if(snapshot.exists()){
                    for (snap in snapshot.children){

                        val data = snap.getValue(EmailMessages::class.java)
                        list_message.add(data!!)
                    }
                    val adapter = MySendsAdapter(list_message)
                    recycleView.adapter = adapter
                }
            }
            override fun onCancelled(error: DatabaseError) {}
        })
    }

    private fun getAmountClients(){
        database = messagePath
        database.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                view?.findViewById<TextView>(R.id.amount_сlients)?.setText(snapshot.children.count().toString())
            }
            override fun onCancelled(error: DatabaseError) {}
        })
    }

    companion object{

        private val list_message: ArrayList<EmailMessages> = arrayListOf()

        object Path{
            val messagePath = FirebaseDatabase.getInstance()
                .getReference(FirebaseConstants.USER_KEY)
                .child(FirebaseAuth.getInstance().currentUser!!.uid)
                .child(FirebaseConstants.MESSAGE_KEY)
        }
    }
}
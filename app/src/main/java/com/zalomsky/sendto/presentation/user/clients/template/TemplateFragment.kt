package com.zalomsky.sendto.presentation.user.clients.template

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import com.zalomsky.sendto.domain.model.Template
import com.zalomsky.sendto.presentation.user.clients.adapter.TemplateRecycleViewAdapter

class TemplateFragment : Fragment() {

    private lateinit var database: DatabaseReference

    private lateinit var recycleView: RecyclerView

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_template, container, false)
        val templateListToolBar = view.findViewById<Toolbar>(R.id.templateListToolBar)
        val listView = view.findViewById<RecyclerView>(R.id.templateListView)

        templateListToolBar.setNavigationOnClickListener { findNavController().navigateUp() }

        recycleView = listView
        recycleView.layoutManager = LinearLayoutManager(requireActivity())
        recycleView.setHasFixedSize(true)

        showTemplateList(view)

        return view
    }

    private fun showTemplateList(view: View){
        database = Path.templatePath
        database.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                list_template.clear()

                for (snap in snapshot.children){
                    val data = snap.getValue(Template::class.java)
                    list_template.add(data!!)
                }
                val adapter = TemplateRecycleViewAdapter(list_template)
                recycleView.adapter = adapter

/*                adapter.setOnItemClickListener(object : TemplateRecycleViewAdapter.onItemClickListener{
                    override fun onItemClick(position: Int) {

                        val bundle = bundleOf("id" to AddressBookFragment.list_addressBook[position].id)
                        bundle.putString("id", AddressBookFragment.list_addressBook[position].id)

                        Navigation.findNavController(view).navigate(R.id.action_addressBookFragment_to_clientFragment, bundle)
                    }
                })*/
            }
            override fun onCancelled(error: DatabaseError) {}
        })
    }

    companion object {

        private val list_template: ArrayList<Template> = arrayListOf()

        object Path {
            val templatePath = FirebaseDatabase.getInstance()
                .getReference(FirebaseConstants.USER_KEY)
                .child(FirebaseAuth.getInstance().currentUser!!.uid)
                .child(FirebaseConstants.TEMPLATE_KEY)
        }
    }
}
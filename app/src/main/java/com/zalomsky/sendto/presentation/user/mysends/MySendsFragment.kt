package com.zalomsky.sendto.presentation.user.mysends

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Environment
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.core.content.FileProvider
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
import java.io.File
import java.io.FileWriter
import java.io.IOException

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
        val report = view.findViewById<ImageView>(R.id.report)

        toolbar.setNavigationOnClickListener { findNavController().navigateUp() }

        recycleView = messageListView
        recycleView.layoutManager = LinearLayoutManager(requireActivity())
        recycleView.setHasFixedSize(true)

        getAddressBookName()
/*        getAmountClients()*/

        report.setOnClickListener {

            val text = convertListToString(list_message)
            val fileName = "mylist.txt"
            val documentsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)
            val file = File(documentsDir, fileName)
            val filePath = file.absolutePath

            saveToFile(text, filePath)

            Toast.makeText(requireActivity(), "Файл сохранен: $filePath", Toast.LENGTH_SHORT).show()
        }

        return view
    }

    private fun convertListToString(list: List<EmailMessages>): String {
        val stringBuilder = StringBuilder()
        for (item in list) {
            stringBuilder.append("Собщение: " + item.message).append("\n")
            stringBuilder.append("От: " + item.from).append("\n")
            stringBuilder.append("Кому: " + item.to).append("\n")
            stringBuilder.append("Адресная книга: " + item.addressBook).append("\n")
            stringBuilder.append("\n")
        }
        return stringBuilder.toString()
    }

    private fun saveToFile(text: String, filePath: String) {
        try {
            val file = File(filePath)
            val writer = FileWriter(file)
            writer.write(text)
            writer.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
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
        private val list_string = list_message.toString()

        object Path{
            val messagePath = FirebaseDatabase.getInstance()
                .getReference(FirebaseConstants.USER_KEY)
                .child(FirebaseAuth.getInstance().currentUser!!.uid)
                .child(FirebaseConstants.MESSAGE_KEY)
        }
    }
}
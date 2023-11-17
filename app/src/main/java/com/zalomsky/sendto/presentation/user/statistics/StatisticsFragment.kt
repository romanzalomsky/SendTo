package com.zalomsky.sendto.presentation.user.statistics

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import com.zalomsky.sendto.R
import com.zalomsky.sendto.data.firebase.model.FirebaseConstants
import com.zalomsky.sendto.databinding.FragmentStatisticsBinding
import com.zalomsky.sendto.presentation.common.auth.AuthFragmentViewModel

class StatisticsFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private var _binding: FragmentStatisticsBinding? = null
    private val binding get() = _binding!!

    private lateinit var databaseReference: DatabaseReference

    private val authFragmentViewModel: AuthFragmentViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentStatisticsBinding.inflate(inflater, container, false)
        val view = binding.root

        auth = Firebase.auth

        val drawerLayout = binding.drawerLayout
        val navigationView = binding.navView
        val toolbar = binding.toolbar

        navigationView.bringToFront()
        val toggle = ActionBarDrawerToggle(
            requireActivity(),
            drawerLayout,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

/*        headerData(view)*/
        sendsCount()
        NavigationDrawerRoutes(navigationView, view, drawerLayout)
        return view
    }

    private fun sendsCount(){
        databaseReference = FirebaseDatabase.getInstance()
            .getReference(FirebaseConstants.USER_KEY)
            .child(FirebaseAuth.getInstance().currentUser!!.uid)
            .child(FirebaseConstants.MESSAGE_KEY)
        databaseReference.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                binding.sendsCount.setText(snapshot.children.count().toString())
            }
            override fun onCancelled(error: DatabaseError) {}
        })
    }

/*    private fun headerData(
        view: View
    ){
        databaseReference = FirebaseDatabase.getInstance().getReference(SendToConstants.USER_KEY).child(FirebaseAuth.getInstance().currentUser!!.uid)
        databaseReference.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(dataSnapshot: DataSnapshot) {

                val name = dataSnapshot.child("name").value.toString()
                val email = dataSnapshot.child("email").value.toString()

                view.findViewById<TextView>(R.id.nameTextView).setText(name)
                view.findViewById<TextView>(R.id.emailTextView)?.setText(email)
            }
            override fun onCancelled(error: DatabaseError) {

            }
        })
    }*/

    private fun NavigationDrawerRoutes(
        navigationView: NavigationView,
        view: View,
        drawerLayout: DrawerLayout
    ){
        val profile = R.id.nav_profile
        val send = R.id.nav_my_send
        val statistics = R.id.nav_statistics
        val addressBook = R.id.nav_client_databases
        val mySends = R.id.nav_my_sends

        navigationView.setNavigationItemSelectedListener {
            when (it.itemId){
                profile -> {
                    Navigation.findNavController(view).navigate(R.id.action_statisticsFragment_to_profileFragment)
                    true
                }
                addressBook -> {
                    Navigation.findNavController(view).navigate(R.id.action_statisticsFragment_to_addressBookFragment)
                    true
                }
                send -> {
                    Navigation.findNavController(view).navigate(R.id.action_statisticsFragment_to_sendTypeFragment2)
                    true
                }
                mySends -> {
                    Navigation.findNavController(view).navigate(R.id.action_statisticsFragment_to_mySendsFragment)
                    true
                }
                statistics -> {
                    drawerLayout.closeDrawer(GravityCompat.START, true)
                    true
                }
                else -> false
            }
        }
    }
}

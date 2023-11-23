package com.zalomsky.sendto.presentation.user.statistics

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.compose.ui.graphics.Color
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
import com.zalomsky.sendto.domain.model.AddressBook
import com.zalomsky.sendto.domain.model.Client
import com.zalomsky.sendto.presentation.common.auth.AuthFragmentViewModel
import com.zalomsky.sendto.presentation.user.clients.adapter.RecycleViewAdapter
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.eazegraph.lib.models.PieModel
import kotlin.random.Random

class StatisticsFragment : Fragment() {

    private lateinit var auth: FirebaseAuth

    private var _binding: FragmentStatisticsBinding? = null

    private val binding get() = _binding!!

    private lateinit var databaseReference: DatabaseReference

    private lateinit var viewModel: StatisticsFragmentViewModel

    private lateinit var list: ArrayList<AddressBook>

    private lateinit var recycleView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentStatisticsBinding.inflate(inflater, container, false)
        val view = binding.root

        viewModel = ViewModelProvider(requireActivity()).get(StatisticsFragmentViewModel::class.java)
        list = arrayListOf()
        auth = Firebase.auth

        recycleView = binding.newlistView
        recycleView.layoutManager = LinearLayoutManager(requireActivity())
        recycleView.setHasFixedSize(true)

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

        lifecycleScope.launch {
            headerData(view)
        }
        lifecycleScope.launch {
            sendsStatistics(view)
        }

        NavigationDrawerRoutes(navigationView, view, drawerLayout)
        return view
    }

    private fun headerData(
        view: View
    ){
        databaseReference = FirebaseDatabase.getInstance().getReference(FirebaseConstants.USER_KEY).child(FirebaseAuth.getInstance().currentUser!!.uid)
        databaseReference.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(dataSnapshot: DataSnapshot) {

                val name = dataSnapshot.child("name").value.toString()
                val email = dataSnapshot.child("email").value.toString()

                viewModel.showHeaderData(name, email)

                view?.findViewById<TextView>(R.id.nameTextView)?.setText(name)
                view?.findViewById<TextView>(R.id.emailTextView)?.setText(email)
            }
            override fun onCancelled(error: DatabaseError) {}
        })
    }

    private fun sendsStatistics(
        view: View
    ){
        databaseReference = FirebaseDatabase.getInstance()
            .getReference(FirebaseConstants.USER_KEY)
            .child(FirebaseAuth.getInstance().currentUser!!.uid)
            .child(FirebaseConstants.ADDRESS_BOOK_KEY)
        databaseReference.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                list.clear()
                if(dataSnapshot.exists()){
                    val amount = dataSnapshot.children.count().toString()

                    viewModel.getAmountAddressBook(amount)

                    view?.findViewById<TextView>(R.id.sendsCount)?.setText(amount)

                    for (snap in dataSnapshot.children){
                        val addressBook = snap.getValue(AddressBook::class.java)
                        val name = snap.getValue(AddressBook::class.java)?.name
                        binding.piechart.addPieSlice(PieModel(name, 13f, generateRandomColor()))
                        list.add(addressBook!!)
                    }
                    val adapter = RecycleViewNew(list)
                    recycleView.adapter = adapter
                }
            }
            override fun onCancelled(error: DatabaseError) {}
        })
    }

    fun generateRandomColor(): Int {
        val alpha = 255
        val red = Random.nextInt(256)
        val green = Random.nextInt(256)
        val blue = Random.nextInt(256)
        return android.graphics.Color.argb(alpha, red, green, blue)
    }

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

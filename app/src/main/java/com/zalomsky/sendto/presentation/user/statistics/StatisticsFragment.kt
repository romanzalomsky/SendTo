package com.zalomsky.sendto.presentation.user.statistics

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.navigation.NavigationView
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
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
import com.zalomsky.sendto.presentation.user.statistics.StatisticsFragment.Companion.Path.addressBook
import com.zalomsky.sendto.presentation.user.statistics.StatisticsFragment.Companion.Path.clientsPath
import com.zalomsky.sendto.presentation.user.statistics.StatisticsFragment.Companion.Path.messagePath
import com.zalomsky.sendto.presentation.user.statistics.StatisticsFragment.Companion.Path.userPath
import kotlinx.coroutines.launch
import org.eazegraph.lib.charts.PieChart
import org.eazegraph.lib.models.PieModel
import kotlin.random.Random

class StatisticsFragment : Fragment() {

    private val viewModel: StatisticsFragmentViewModel by lazy {
        ViewModelProvider(requireActivity()).get(StatisticsFragmentViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_statistics, container, false)
        val list = view.findViewById<RecyclerView>(R.id.newlistView)
        val toolbar = view.findViewById<Toolbar>(R.id.toolbar)
        val drawerLayout = view.findViewById<DrawerLayout>(R.id.drawer_layout)
        val navigationView = view.findViewById<NavigationView>(R.id.nav_view)
        val pieChart = view.findViewById<PieChart>(R.id.piechart)

        recycleView = list
        recycleView.layoutManager = LinearLayoutManager(requireActivity())
        recycleView.setHasFixedSize(true)

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

        lifecycleScope.launch { headerData(view) }
        lifecycleScope.launch { sendsStatistics(view, pieChart) }
        showMostPopular(view)
        showUniqueClients(view)

        NavigationDrawerRoutes(navigationView, view, drawerLayout)
        return view
    }

    private fun headerData(view: View){

        databaseReference = userPath
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                val name = dataSnapshot.child("name").value.toString()
                val email = dataSnapshot.child("email").value.toString()

                viewModel.showHeaderData(name, email)

                view.findViewById<TextView>(R.id.nameTextView)?.setText(name)
                view.findViewById<TextView>(R.id.emailTextView)?.setText(email)
            }
            override fun onCancelled(error: DatabaseError) {}
        })
    }

    private fun sendsStatistics(view: View, pieChart: PieChart){

        databaseReference = addressBook
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                list.clear()
                if(dataSnapshot.exists()){
                    val amount = dataSnapshot.children.count().toString()

                    viewModel.getAmountAddressBook(amount)

                    view.findViewById<TextView>(R.id.sendsCount)?.setText(amount)

                    for (snap in dataSnapshot.children){
                        val addressBook = snap.getValue(AddressBook::class.java)
                        val name = snap.getValue(AddressBook::class.java)?.name
                        pieChart.addPieSlice(PieModel(name, 13f, generateRandomColor()))
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

    private fun showMostPopular(view: View){

        val mostPopularBook = view.findViewById<TextView>(R.id.most_popular_book)
        databaseReference = messagePath
        databaseReference.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val textCounts = HashMap<String, Int>()
                for(snap in snapshot.children){
                    val abName = snap.child("addressBook").value.toString()
                    if (textCounts.containsKey(abName)) {
                        val count = textCounts[abName]!!
                        textCounts[abName] = count + 1
                    } else {
                        textCounts[abName] = 1
                    }
                }
                var mostPopularText = ""
                var maxCount = 0
                for ((text, count) in textCounts) {
                    if (count > maxCount) {
                        maxCount = count
                        mostPopularText = text
                    }
                }
                mostPopularBook.setText(mostPopularText)
            }
            override fun onCancelled(error: DatabaseError) {}
        })
    }

    private fun showUniqueClients(view: View){

        val uniqueClients = view.findViewById<TextView>(R.id.unique_clients)
        databaseReference = clientsPath
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val emailSet = HashSet<String>()
                for (snap in snapshot.children) {
                    val email = snap.child("email").value.toString()
                    emailSet.add(email)
                }
                val uniqueEmailCount = emailSet.size
                uniqueClients.setText(uniqueEmailCount.toString())
            }
            override fun onCancelled(error: DatabaseError) {}
        })
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
        val templates = R.id.nav_templates

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
                templates -> {
                    Navigation.findNavController(view).navigate(R.id.action_statisticsFragment_to_templateFragment)
                    true
                }
                else -> false
            }
        }
    }

    companion object {

        private var list: ArrayList<AddressBook> = arrayListOf()
        private lateinit var databaseReference: DatabaseReference
        private lateinit var recycleView: RecyclerView

        object Path {
            internal val clientsPath = FirebaseDatabase.getInstance()
                .getReference(FirebaseConstants.USER_KEY)
                .child(FirebaseAuth.getInstance().currentUser!!.uid)
                .child(FirebaseConstants.CLIENTS_KEY)
            internal val messagePath = FirebaseDatabase.getInstance()
                .getReference(FirebaseConstants.USER_KEY)
                .child(FirebaseAuth.getInstance().currentUser!!.uid)
                .child(FirebaseConstants.MESSAGE_KEY)
            internal val addressBook = FirebaseDatabase.getInstance()
                .getReference(FirebaseConstants.USER_KEY)
                .child(FirebaseAuth.getInstance().currentUser!!.uid)
                .child(FirebaseConstants.ADDRESS_BOOK_KEY)
            internal val userPath = FirebaseDatabase.getInstance()
                .getReference(FirebaseConstants.USER_KEY)
                .child(FirebaseAuth.getInstance().currentUser!!.uid)
        }
    }
}

package com.zalomsky.sendto.presentation.user.statistics

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.view.menu.MenuView.ItemView
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import com.zalomsky.sendto.R

class StatisticsFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    @SuppressLint("WrongConstant")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        
        val view= inflater.inflate(R.layout.fragment_statistics, container, false)

        val drawerLayout = view.findViewById<DrawerLayout>(R.id.drawer_layout)
        val navigationView = view.findViewById<NavigationView>(R.id.nav_view)
        val toolbar = view.findViewById<Toolbar>(R.id.toolbar)

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

        val profile = R.id.nav_profile
        val send = R.id.nav_my_send
        val statistics = R.id.nav_statistics

        navigationView.setNavigationItemSelectedListener {
            when (it.itemId){
                profile -> {
                    Navigation.findNavController(view).navigate(R.id.action_statisticsFragment_to_profileFragment)
                    true
                }
                send -> {
                    Navigation.findNavController(view).navigate(R.id.action_statisticsFragment_to_sendFragment)
                    true
                }
                statistics -> {
                    drawerLayout.closeDrawer(Gravity.START, true)
                    true
                }
                else -> false
            }
        }
        return view
    }
}
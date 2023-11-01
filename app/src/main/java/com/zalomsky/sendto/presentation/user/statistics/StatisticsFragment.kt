package com.zalomsky.sendto.presentation.user.statistics

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.Navigation
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.zalomsky.sendto.R
import com.zalomsky.sendto.databinding.FragmentStatisticsBinding
import com.zalomsky.sendto.presentation.common.auth.AuthFragmentViewModel

class StatisticsFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private var _binding: FragmentStatisticsBinding? = null
    private val binding get() = _binding!!

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

/*        authFragmentViewModel.email.observe(requireActivity(), {
            binding.haha.text = it
        })*/

        binding.haha.text = auth.currentUser?.email

        NavigationDrawerRoutes(navigationView, view, drawerLayout)
        return view
    }

    private fun NavigationDrawerRoutes(
        navigationView: NavigationView,
        view: View,
        drawerLayout: DrawerLayout
    ){
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
                    Navigation.findNavController(view).navigate(R.id.action_statisticsFragment_to_sendTypeFragment2)
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

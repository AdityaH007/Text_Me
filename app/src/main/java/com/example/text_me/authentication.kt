package com.example.text_me

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.auth.FirebaseAuth

class authentication : AppCompatActivity(),FirebaseAuth.AuthStateListener{
    private lateinit var viewPager2: ViewPager2
    private lateinit var tabLayout: TabLayout
    private lateinit var ViewPagerAdapter:AuthenticationPagerAdapter
    private val Titles= arrayListOf("login","sign up")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authentication)
        viewPager2=findViewById(R.id.ViewPagerAuth)
        tabLayout=findViewById(R.id.TabLayoutAuth)
        ViewPagerAdapter=AuthenticationPagerAdapter(this,)
        viewPager2.adapter=ViewPagerAdapter
        TabLayoutMediator(tabLayout,viewPager2){tab,position->
            tab.text=Titles[position]

        }.attach()
    }
    class AuthenticationPagerAdapter(fragmentActivity: FragmentActivity):FragmentStateAdapter(fragmentActivity)
    {
        override fun getItemCount(): Int {
            return 2
        }

        override fun createFragment(position: Int): Fragment {
           return when(position){

               0->login()
               1->signup()
               else->login()
           }
        }

    }

    override fun onStart() {
        super.onStart()
        FirebaseAuth.getInstance().addAuthStateListener(this)
        if(FirebaseAuth.getInstance().currentUser!=null)
        {
            startMainActivity()
        }
    }


    override fun onAuthStateChanged(p0: FirebaseAuth) {
        if(p0.currentUser!=null)
        {
            startMainActivity()
        }
    }


    override fun onStop() {
        super.onStop()
        FirebaseAuth.getInstance().removeAuthStateListener(this)
    }

    private fun startMainActivity() {
        val intent=Intent(this@authentication,MainActivity::class.java)
        startActivity(intent)
    }
}
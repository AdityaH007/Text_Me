package com.example.text_me

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import android.widget.Toolbar
import androidx.appcompat.widget.DialogTitle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.PagerTitleStrip
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.auth.FirebaseAuth
import com.example.text_me.MainActivity.AppPagerAdapter as AppPagerAdapter1

class MainActivity : AppCompatActivity() {

    private lateinit var viewPager2: ViewPager2
    private lateinit var tabLayout: TabLayout
    private lateinit var toolbar: androidx.appcompat.widget.Toolbar
    private lateinit var appPagerAdapter: AppPagerAdapter1
    private lateinit var auth:FirebaseAuth
    private lateinit var showcontacts:FloatingActionButton
    private var titles= arrayListOf("chats","status","calls")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        viewPager2=findViewById(R.id.altop)
        tabLayout=findViewById(R.id.tlayout)
        auth= FirebaseAuth.getInstance()
        toolbar=findViewById(R.id.tbar)
        showcontacts=findViewById(R.id.contacts)
        toolbar.title="text me"
        setSupportActionBar(toolbar)
        appPagerAdapter= AppPagerAdapter1(fragmentActivity = this)
        viewPager2.adapter= appPagerAdapter
        TabLayoutMediator(tabLayout,viewPager2){ tab,position->
            tab.text=titles[position]




        }.attach()
        showcontacts.setOnClickListener {
            val intent=Intent(this,menuActivity::class.java)
            intent.putExtra("OptionName","Contact")
            startActivity(intent)
        }
    }
    class AppPagerAdapter(fragmentActivity: FragmentActivity):FragmentStateAdapter(fragmentActivity) {
        override fun getItemCount(): Int {
           return 3
        }

        override fun createFragment(position: Int): Fragment {
         return when(position)
         {
             0-> chats()
             1-> status()
             2->calls()
             else -> chats()
         }

        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){

            R.id.prl->{val intent=Intent(this,menuActivity::class.java)
                intent.putExtra("OptionName","Profile")
            startActivity(intent)}
            R.id.abt->{
                val intent=Intent(this,menuActivity::class.java)
                intent.putExtra("OptionName","About")
                startActivity(intent)
            }
            R.id.lg->{
                auth.signOut()
                val intent=Intent(this,authentication::class.java)
                intent.putExtra("OptionName","Logout")
                startActivity(intent)
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
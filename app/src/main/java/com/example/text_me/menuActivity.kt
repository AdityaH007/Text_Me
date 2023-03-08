package com.example.text_me

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager.LayoutParams.*
import android.widget.FrameLayout
import androidx.appcompat.widget.ContentFrameLayout
import androidx.appcompat.widget.Toolbar

class menuActivity : AppCompatActivity() {
    private lateinit var toolbar: Toolbar
    private lateinit var frameLayout: FrameLayout
    private lateinit var OptionValue:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        window.addFlags(FLAG_KEEP_SCREEN_ON)
        toolbar=findViewById(R.id.tblr)
        frameLayout=findViewById(R.id.framelyt)
        if (intent!=null)
        {
            OptionValue=intent.getStringExtra("OptionName").toString()
            when(OptionValue)
            {
                "Profile"->{
                    supportFragmentManager.beginTransaction().replace(R.id.framelyt,Profile()).commit()
                    toolbar.title="Profile"

                }
                "About"->{
                    supportFragmentManager.beginTransaction().replace(R.id.framelyt,About()).commit()
                    toolbar.title="About"

                }
                "Logout"->{

                }
            }
        }

    }
}
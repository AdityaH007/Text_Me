package com.example.text_me

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView

class splash : AppCompatActivity() {
    private var splashScreenTime=4000
    private lateinit var ImageView: ImageView
    private lateinit var topanim: Animation
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        ImageView=findViewById(R.id.oop)
        topanim=AnimationUtils.loadAnimation(this,R.anim.topanim)
        ImageView.animation=topanim
        Handler(Looper.getMainLooper()).postDelayed(
            {
                val intent=Intent(this@splash,authentication::class.java)
                startActivity(intent)
                finish()
            },splashScreenTime.toLong()
        )
    }
}
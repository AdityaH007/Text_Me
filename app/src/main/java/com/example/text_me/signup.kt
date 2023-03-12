package com.example.text_me


import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import com.example.text_me.R.id.Login
import com.google.android.material.textfield.TextInputEditText

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore




class signup : Fragment() {


    private  lateinit var email:TextInputEditText
    private lateinit var passwordo:TextInputEditText
    private lateinit var btn: Button
    private lateinit var auth: FirebaseAuth
    private lateinit var glb:Button
    private lateinit var str:FirebaseFirestore
    private lateinit var db:DocumentReference




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_signup, container, false)

        email=view.findViewById(R.id.etmmnk)

        passwordo=view.findViewById(R.id.etpassword)
        btn=view.findViewById(R.id.btn)

        glb=view.findViewById(R.id.ggl)
        auth= FirebaseAuth.getInstance()
        str= FirebaseFirestore.getInstance()
        btn.setOnClickListener {
            val emsil=email.text.toString()
            val pass=passwordo.text.toString()
            if(TextUtils.isEmpty(emsil))
            {
                email.error="email is required"
            }
            else if(TextUtils.isEmpty(pass))
            {
                passwordo.error="password is required"
            }
            else if(pass.length<8){
                passwordo.error="password must be greater than 8 characters"
            }
            else {

                CreateAccount(emsil,pass)
            }
        }

        return view
    }

    private fun CreateAccount( eml:String ,  pass:String) {
        auth.createUserWithEmailAndPassword(eml, pass).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val userinfo = auth.currentUser?.uid
                db = str.collection("users").document(userinfo.toString())

                val obj = mutableMapOf<String, String>()
                obj["user email"] = eml
                obj["user password"] = pass
                obj["name"]=""
                obj["status"]=""
                db.set(obj).addOnSuccessListener {
                    Log.d("on success", "Account created Successfully")

                }
            }
        }

    }
}



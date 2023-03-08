package com.example.text_me

import android.app.Activity
import android.app.Instrumentation.ActivityResult
import android.content.Intent
import android.nfc.Tag
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.android.material.tabs.TabLayout.TabGravity
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth


class login : Fragment() {

    private lateinit var email: TextInputEditText
    private lateinit var password:TextInputEditText
    private lateinit var progressBar: ProgressBar
    private lateinit var button: Button
    private lateinit var ggl:Button
    private lateinit var googleSignInOptions: GoogleSignInOptions
    private lateinit var GoogleSignInClient:GoogleSignInClient
    private lateinit var result:ActivityResultLauncher<Intent>
    val RC_SIGN_IN=1011


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
      val view= inflater.inflate(R.layout.fragment_login, container, false)
        email=view.findViewById(R.id.etlogin)
        password=view.findViewById(R.id.etpassword)
        button=view.findViewById(R.id.btn)
        ggl=view.findViewById(R.id.ggle)


        button.setOnClickListener {
            val eml= email.text.toString()
            val pass=password.text.toString()
            if(TextUtils.isEmpty(eml))
            {
                email.error="email is required"
            }
            else if(TextUtils.isEmpty(pass))
            {
                password.error="password is required"
            }
            else if(pass.length<8){
                password.error="password must be greater than 8 characters"
            }
            else {
                signin(eml,pass)


            }
        }

        ggl.setOnClickListener {
            createresquest()


        }
        result=registerForActivityResult(ActivityResultContracts.StartActivityForResult()){result->
            if(result.resultCode== Activity.RESULT_OK){
                val launchdata= result.data
                val task =GoogleSignIn.getSignedInAccountFromIntent(launchdata)

                    try
                    {
                        val account=task.getResult(ApiException::class.java)
                        Log.d("Gmail ID","firebaseAuthWith Google :$account")
                        firebaseAuthWithGoogle(account?.idToken)
                    }
                    catch (e:ApiException)
                    {
                        Log.w("Error","Google Sign IN Failed",e)
                    }

            }

        }
        return view
    }

    private fun createresquest() {
        googleSignInOptions=GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("Google ID")
            .requestEmail()
            .build()
    }

    private fun firebaseAuthWithGoogle(idToken: String?) {

    }

    private fun signin(em:String,pass:String) {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(em,pass).addOnCompleteListener {task->
            if(task.isSuccessful)
                Toast.makeText(context,"login succesful",Toast.LENGTH_LONG).show()

        }
    }

}


package com.example.text_me

import android.graphics.Bitmap
import android.os.Bundle
import android.text.Editable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.squareup.picasso.Picasso
import java.io.ByteArrayOutputStream
import kotlin.math.log


class Profile : Fragment() {
    private lateinit var profile:de.hdodenhof.circleimageview.CircleImageView
    private lateinit var editprf:ImageView
    private lateinit var name:TextView
    private lateinit var status:TextView
    private lateinit var editname:TextInputLayout
    private lateinit var etname:TextInputEditText
    private lateinit var etstatus:TextInputEditText
    private lateinit var editstatus:TextInputLayout
    private lateinit var updatebtn:Button
    private lateinit var  userid: String

    private lateinit var savebtn:Button
    private lateinit var auth:FirebaseAuth
    private lateinit var fstore:FirebaseFirestore
    private lateinit var db:DocumentReference
    private lateinit var iamge: ByteArray
    private lateinit var storageReference: StorageReference
   val register=registerForActivityResult(ActivityResultContracts.TakePicturePreview()){
        uploadImage(it)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_profile, container, false)

        profile=view.findViewById(R.id.profile_image)
        auth= FirebaseAuth.getInstance()
        editprf=view.findViewById(R.id.editimg)
        name=view.findViewById(R.id.name)
        status=view.findViewById(R.id.status)
        userid=auth.currentUser!!.uid

        editname=view.findViewById(R.id.nametr)
        etstatus=view.findViewById(R.id.etstatus)
        editstatus=view.findViewById(R.id.statustr)
        updatebtn=view.findViewById(R.id.updatebtn)
        savebtn=view.findViewById(R.id.savebtn)
        etname=view.findViewById(R.id.etname)

        fstore= FirebaseFirestore.getInstance()
        storageReference= FirebaseStorage.getInstance().reference.child("$userid/pfp")

        db=fstore.collection("userid").document(userid)
        db.addSnapshotListener { value, error ->
            if (error!=null)
            {
                Log.d("Error","Unable To fetch data")
            }
            else
            {
                name.text=value?.getString("name")
                status.text=value?.getString("status")
                Picasso.get().load(value?.getString("pfp")).into(profile)
            }
        }




        updatebtn.visibility=View.VISIBLE
        updatebtn.setOnClickListener {
            name.visibility=View.GONE
            status.visibility=View.GONE
            updatebtn.visibility=View.GONE
            editname.visibility=View.VISIBLE
            editstatus.visibility=View.VISIBLE
            savebtn.visibility=View.VISIBLE
            etname.text=Editable.Factory.getInstance().newEditable(name.text.toString())
            etstatus.text=Editable.Factory.getInstance().newEditable(status.text.toString())

        }
        savebtn.setOnClickListener {
            name.visibility=View.VISIBLE
            status.visibility=View.VISIBLE
            editname.visibility=View.GONE
            editstatus.visibility=View.GONE
            savebtn.visibility=View.GONE
            updatebtn.visibility=View.VISIBLE
            val obj= mutableMapOf<String,String>()
            obj["name"]=etname.text.toString()
            obj["status"]=etstatus.text.toString()
            db.set(obj).addOnSuccessListener {
                Log.d("Success","Data Successfully Updated")
            }

        }
        editprf.setOnClickListener {
            photo()
        }
        return view
    }

    private fun photo() {

        register.launch(null)
    }

    private fun uploadImage(it: Bitmap?) {
        val baos= ByteArrayOutputStream()
        it?.compress(Bitmap.CompressFormat.JPEG,50,baos)
        iamge=baos.toByteArray()
        storageReference.putBytes(iamge).addOnSuccessListener {
            storageReference.downloadUrl.addOnSuccessListener {
                val obj = mutableMapOf<String,String>()
                obj["pfp"]=it.toString()
                db.update(obj as Map<String,Any>).addOnSuccessListener {
                    Log.d("OnSuccess","pfp updated")
                }
            }
        }

    }


}
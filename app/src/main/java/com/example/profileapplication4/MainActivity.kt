package com.example.profileapplication4

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.profileapplication4.models.Employer
import com.example.profileapplication4.view.LogInActivity
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.btnStart).setOnClickListener {
            startActivity(Intent(this, LogInActivity::class.java))
        }

        FirebaseFirestore.getInstance().collection("employers")
            .whereEqualTo("city", "Шымкент")
            .whereEqualTo("district", "Сарығаш")
            .whereEqualTo("village", "Ұшқын")
            .get().addOnCompleteListener {
                if (it.isSuccessful) {
                    val employers = mutableListOf<Employer>()
                    for (document in it.result!!) {
                        Log.i(
                            "Search Result employer",
                            "searchOnByCityDistrictVillage: " + document.toObject(Employer::class.java).name
                        )
                        employers.add(document.toObject(Employer::class.java))
                    }

                }
            }


    }
}
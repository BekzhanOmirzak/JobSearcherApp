package com.example.profileapplication4.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.profileapplication4.R
import com.example.profileapplication4.adapters.ChatRoomAdapter
import com.example.profileapplication4.databinding.ActivityMessageBinding
import com.example.profileapplication4.models.ChatRoom
import com.example.profileapplication4.models.Employee
import com.example.profileapplication4.models.Employer
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.FirebaseFirestore

class MessageActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var binding: ActivityMessageBinding;
    private lateinit var drawerToggle: ActionBarDrawerToggle
    private lateinit var nav_view: NavigationView
    private lateinit var chatRoomAdapter: ChatRoomAdapter
    private lateinit var chatRooms: MutableList<ChatRoom>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMessageBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        drawerToggle = ActionBarDrawerToggle(
            this, binding.drawerLayout, binding.toolBar, R.string.open_drawer, R.string.close_drawer
        )
        binding.drawerLayout.addDrawerListener(drawerToggle)
        drawerToggle.syncState()

        nav_view = findViewById(R.id.nav_view)
        identifyTheUser()


        binding.navView.setNavigationItemSelectedListener(this)

        populatingRecViewWithData()

    }

    private fun populatingRecViewWithData() {
        chatRooms = arrayListOf()
        val reference = FirebaseDatabase.getInstance().getReference().child("chatrooms")
        reference.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                for (snapshop in snapshot.children) {
                    val chatRoom = snapshop.getValue(ChatRoom::class.java)
                    chatRoom?.let { chatRooms.add(it) }
                }
                chatRoomAdapter.updateChatRooms(chatRooms)
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })

    }


    private fun identifyTheUser() {

        val sharedPreferences = getSharedPreferences("db_name", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val user_ref = FirebaseAuth.getInstance().currentUser!!.uid
        FirebaseFirestore.getInstance().collection("employees")
            .document(user_ref).get().addOnCompleteListener {
                if (it.isSuccessful) {
                    val employee = it.result?.toObject(Employee::class.java)
                    if (employee != null) {
                        editor.clear()
                        editor.commit()
                        editor.putString("user", "employee")
                        editor.commit()
                        settingUpAdapter("employee")

                    }
                }
            }

        FirebaseFirestore.getInstance().collection("employers")
            .document(user_ref).get().addOnCompleteListener {
                if (it.isSuccessful) {
                    val employer = it.result?.toObject(Employer::class.java)
                    if (employer != null) {
                        editor.clear()
                        editor.commit()
                        editor.putString("user", "employer")
                        editor.commit()
                        settingUpAdapter("employer")
                    }
                }
            }

    }

    private fun settingUpAdapter(user_type: String) {
        chatRoomAdapter =
            ChatRoomAdapter(this, FirebaseAuth.getInstance().currentUser!!.uid, user_type)
        binding.recViewChatRoom.also {
            it.layoutManager = LinearLayoutManager(this)
            it.adapter = chatRoomAdapter
            it.setHasFixedSize(true)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menuProfile -> {
                startActivity(Intent(this, MyProfileActivity::class.java))
            }
            R.id.menuSearch -> {
                startActivity(Intent(this, SearchActivity::class.java))
            }

        }
        return true
    }

    private fun changeDetailsInDrawerLayout() {
        val header_view = nav_view.getHeaderView(0)
        val txtEmail = header_view.findViewById<TextView>(R.id.txtEmail)
        val txtNameSurname = header_view.findViewById<TextView>(R.id.txtNameSurName)
        val image_view = header_view.findViewById<ImageView>(R.id.imgProfile)

        val uid = FirebaseAuth.getInstance().currentUser!!.uid
        FirebaseFirestore.getInstance().collection("employees").document(uid)
            .get().addOnCompleteListener {
                if (it.isSuccessful) {
                    val employee = it.result?.toObject(Employee::class.java)
                    if (employee != null) {
                        txtEmail.setText(employee.email)
                        txtNameSurname.setText(employee.name + " " + employee.surname)
                        Glide.with(this).asBitmap().load(employee.image_url).into(image_view)
                    }
                }
            }

        FirebaseFirestore.getInstance().collection("employers").document(uid)
            .get().addOnCompleteListener {
                if (it.isSuccessful) {
                    val employer = it.result?.toObject(Employer::class.java)
                    if (employer != null) {
                        txtEmail.setText(employer.email)
                        txtNameSurname.setText(employer.name + " " + employer.surname)
                        Glide.with(this).asBitmap().load(employer.image_url).into(image_view)
                    }
                }
            }
    }

    override fun onResume() {
        super.onResume()
        changeDetailsInDrawerLayout()
    }

}
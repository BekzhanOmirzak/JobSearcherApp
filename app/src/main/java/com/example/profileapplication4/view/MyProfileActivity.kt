package com.example.profileapplication4.view

import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.profileapplication4.databinding.ActivityMyProfileBinding
import com.example.profileapplication4.models.Employee
import com.example.profileapplication4.models.Employer
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.ByteArrayOutputStream

class MyProfileActivity : AppCompatActivity(), ChoosePhotoDialog.OnPhotoReceived {

    private lateinit var binding: ActivityMyProfileBinding
    private var isPermitted = false

    private var imageBitMap: Bitmap? = null
    private lateinit var storageReference: StorageReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyProfileBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        storageReference = FirebaseStorage.getInstance().getReference("images")

        setUpDetailOfUser()

        binding.btnSave.setOnClickListener {
            updatingTheDetailsOfPerson()
        }

        binding.profileCircle.setOnClickListener {
            if (isPermitted) {
                val dialog = ChoosePhotoDialog()
                dialog.show(supportFragmentManager, "show a dialog")
            } else {
                asksForPermission()
            }
        }

    }

    private fun asksForPermission() {
        val permissions = arrayOf(
            android.Manifest.permission.READ_EXTERNAL_STORAGE,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
            android.Manifest.permission.CAMERA
        )

        if (ContextCompat.checkSelfPermission(
                this,
                permissions[0]
            ) == PackageManager.PERMISSION_GRANTED
            && ContextCompat.checkSelfPermission(
                this, permissions[1]
            ) == PackageManager.PERMISSION_GRANTED
            && ContextCompat.checkSelfPermission(
                this,
                permissions[2]
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            isPermitted = true
        } else {
            ActivityCompat.requestPermissions(this, permissions, 2)
        }


    }

    private fun updatingTheDetailsOfPerson() {
        binding.progressBar.visibility = View.VISIBLE
        val name = binding.edtName.text.toString()
        val surName = binding.edtSurName.text.toString()
        val phone_number = binding.edtPhoneNumber.text.toString()
        val city = binding.edtCity.text.toString()
        val district = binding.edtDistrict.text.toString()
        val village = binding.edtVillage.text.toString()

        val map = HashMap<String, String>()
        map.put("name", name)
        map.put("surname", surName)
        map.put("phone_number", phone_number)
        map.put("city", city)
        map.put("district", district)
        map.put("village", village)

        uploadingModifiedDataToFirebase(map)
        if (imageBitMap != null) {
            uploadingImage()
        }
    }

    private fun uploadingImage() {
        val outStream = ByteArrayOutputStream()
        imageBitMap?.compress(Bitmap.CompressFormat.JPEG, 100, outStream)
        val data = outStream.toByteArray()
        val imageReference =
            storageReference.child(FirebaseAuth.getInstance().currentUser!!.uid + ".jpg")
        imageReference.putBytes(data).addOnSuccessListener {
            imageReference.downloadUrl.addOnSuccessListener {
                val map = HashMap<String, String>()
                map.put("image_url", it.toString())
                uploadingModifiedDataToFirebase(map)
            }
        }.addOnFailureListener {
            Toast.makeText(this, "Exception is thrown", Toast.LENGTH_SHORT).show()
        }.addOnProgressListener {
            val progress = (100.0 * it.bytesTransferred) / it.totalByteCount
            Toast.makeText(this, "Progress ${progress} ", Toast.LENGTH_SHORT).show()
        }
    }

    private fun uploadingModifiedDataToFirebase(map: HashMap<String, String>) {
        val type_user = getSharedPreferences("db_name", MODE_PRIVATE).getString("user", null)
        Log.i("Bekzhan", "uploadingModifiedDataToFirebase: type_user inside myprofile"+type_user)
        if (type_user.equals("employee")) {
            FirebaseFirestore.getInstance().collection("employees")
                .document(FirebaseAuth.getInstance().currentUser!!.uid).set(map, SetOptions.merge())
                .addOnCompleteListener {
                    binding.progressBar.visibility = View.GONE
                }
        }
        if (type_user.equals("employer")) {
            FirebaseFirestore.getInstance().collection("employers")
                .document(FirebaseAuth.getInstance().currentUser!!.uid).set(map, SetOptions.merge())
                .addOnCompleteListener {
                    binding.progressBar.visibility = View.GONE
                }
        }
    }


    private fun setUpDetailOfUser() {
        binding.progressBar.visibility = View.VISIBLE
        val user_id = FirebaseAuth.getInstance().currentUser!!.uid
        FirebaseFirestore.getInstance().collection("employees")
            .document(user_id).get().addOnCompleteListener {
                if (it.isSuccessful) {
                    val employee = it.result?.toObject(Employee::class.java)
                    if (employee != null) {
                        setDetailOfEmployees(employee)
                    }
                }
            }

        FirebaseFirestore.getInstance().collection("employers")
            .document(user_id).get().addOnCompleteListener {
                if (it.isSuccessful) {
                    val employer = it.result?.toObject(Employer::class.java)
                    if (employer != null) {
                        setDetailOfEmployees(employer)
                    }
                }
            }
    }

    private fun setDetailOfEmployees(employee: Employee) {
        binding.edtCity.setText(employee.city)
        binding.edtName.setText(employee.name)
        binding.edtDistrict.setText(employee.district)
        binding.edtSurName.setText(employee.surname)
        binding.edtPhoneNumber.setText(employee.phone_number)
        binding.edtVillage.setText(employee.village)
        Glide.with(this).asBitmap().load(employee.image_url).into(binding.profileCircle);
        binding.progressBar.visibility = View.GONE
    }

    private fun setDetailOfEmployees(employee: Employer) {
        binding.edtCity.setText(employee.city)
        binding.edtName.setText(employee.name)
        binding.edtDistrict.setText(employee.district)
        binding.edtSurName.setText(employee.surname)
        binding.edtPhoneNumber.setText(employee.phone_number)
        binding.edtVillage.setText(employee.village)
        Glide.with(this).asBitmap().load(employee.image_url).into(binding.profileCircle);
        binding.progressBar.visibility = View.GONE
    }

    override fun getImagePath(uri: Uri?) {
        if (uri != null) {
            imageBitMap = null
            imageBitMap = MediaStore.Images.Media.getBitmap(this.contentResolver, uri)
            Glide.with(this).load(imageBitMap).into(binding.profileCircle)
        }
    }

    override fun getImageBitMap(bitmap: Bitmap?) {
        if (bitmap != null) {
            imageBitMap = null
            imageBitMap = bitmap
            Glide.with(this).load(imageBitMap).into(binding.profileCircle)
        }
    }


}
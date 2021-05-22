package com.example.profileapplication4.view

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.Intent.ACTION_GET_CONTENT
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore.ACTION_IMAGE_CAPTURE
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.example.profileapplication4.R

class ChoosePhotoDialog : DialogFragment() {

    private lateinit var onPhotoReceived: OnPhotoReceived

    companion object {
        private val CODE_PICK_IMAGE = 1
        private val CODE_TAKE_IMAGE = 2
    }


    interface OnPhotoReceived {
        fun getImagePath(uri: Uri?)
        fun getImageBitMap(bitmap: Bitmap?)
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        onPhotoReceived = context as OnPhotoReceived
    }


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = activity?.layoutInflater?.inflate(R.layout.change_photo_dialog, null, false)
        val dialog = AlertDialog.Builder(activity).setView(view)
        chooseImages(view!!)
        return dialog.create()
    }

    private fun chooseImages(view: View) {
        view.findViewById<TextView>(R.id.txtChoosePhoto).setOnClickListener {
            val intent = Intent()
            intent.setAction(ACTION_GET_CONTENT)
            intent.setType("image/*")
            startActivityForResult(intent, CODE_PICK_IMAGE)
        }

        view.findViewById<TextView>(R.id.txtTakePhoto).setOnClickListener {
            val intent = Intent()
            intent.setAction(ACTION_IMAGE_CAPTURE)
            startActivityForResult(intent, CODE_TAKE_IMAGE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == CODE_PICK_IMAGE && resultCode == Activity.RESULT_OK && data != null) {
            val selectedImageUri = data.data
            onPhotoReceived.getImagePath(selectedImageUri!!)
            Log.i("Bekzhan", "onActivityResult: $selectedImageUri")
            dismiss()
        } else {
            val bitmap = data?.extras?.get("data") as Bitmap
            onPhotoReceived.getImageBitMap(bitmap)
            Log.i("Bekzhan", "onActivityResult: $bitmap ")
            dismiss()
        }

    }


}
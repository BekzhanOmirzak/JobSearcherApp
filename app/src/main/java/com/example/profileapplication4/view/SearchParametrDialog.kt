package com.example.profileapplication4.view

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import com.example.profileapplication4.R

class SearchParametrDialog : DialogFragment() {

    private lateinit var onSearchParameters: OnSearchParameters


    interface OnSearchParameters {
        fun searchOnByCity(city: String)
        fun searchOnByCityDistrict(city: String, district: String)
        fun searchOnByCityDistrictVillage(city: String, district: String, village: String)

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            onSearchParameters = context as OnSearchParameters
        } catch (exception: ClassCastException) {

        }
    }


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = activity?.layoutInflater?.inflate(R.layout.search_parameters_dialog, null, false)
        val dialog = AlertDialog.Builder(activity)
            .setView(view);
        initViews(view)
        return dialog.create()
    }

    private fun initViews(view: View?) {

        val edtCity = view?.findViewById<EditText>(R.id.edtCity)
        val edtDistrict = view?.findViewById<EditText>(R.id.edtDistrict)
        val edtVillage = view?.findViewById<EditText>(R.id.edtVillage)



        view?.findViewById<Button>(R.id.btnSearch)?.setOnClickListener {
            val city = edtCity?.text.toString()
            val district = edtDistrict?.text.toString()
            val village = edtVillage?.text.toString()
            Log.i("Bekzhan ", "initViews: $city $district $village")
            onSearchParameters.searchOnByCityDistrictVillage(city, district, village)
            dismiss()
        }

    }

}
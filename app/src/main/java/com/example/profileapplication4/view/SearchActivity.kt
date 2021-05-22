package com.example.profileapplication4.view

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.profileapplication4.databinding.ActivitySearchBinding
import com.example.profileapplication4.adapters.FoundEmployeesAdapter
import com.example.profileapplication4.adapters.FoundEmployersAdapter
import com.example.profileapplication4.models.Employee
import com.example.profileapplication4.models.Employer
import com.google.firebase.firestore.FirebaseFirestore

class SearchActivity : AppCompatActivity(), SearchParametrDialog.OnSearchParameters {

    private lateinit var binding: ActivitySearchBinding
    private lateinit var employersAdapter: FoundEmployersAdapter
    private lateinit var employeesAdapter: FoundEmployeesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        employersAdapter = FoundEmployersAdapter(this)
        employeesAdapter = FoundEmployeesAdapter(this)

        binding.recView.also {
            it.layoutManager = LinearLayoutManager(this)
            it.setHasFixedSize(true)
        }
        binding.txtparametrs.setOnClickListener {
            val dialog = SearchParametrDialog()
            dialog.show(supportFragmentManager, "show a dialog")
        }

        val type_user = getSharedPreferences("db_name", MODE_PRIVATE).getString("user", null)

        Log.i("Bekzhan", "searchOnByCityDistrictVillage:  type User " + type_user)

    }

    override fun searchOnByCityDistrictVillage(city: String, district: String, village: String) {

        val type_user = getSharedPreferences("db_name", MODE_PRIVATE).getString("user", null)

        Log.i("Bekzhan", "searchOnByCityDistrictVillage:  type User " + type_user)

        if (type_user.equals("employee")) {
            FirebaseFirestore.getInstance().collection("employers")
                .whereEqualTo("city", city)
                .whereEqualTo("district", district)
                .whereEqualTo("village", village)
                .get().addOnCompleteListener {
                    if (it.isSuccessful) {
                        val employers = mutableListOf<Employer>()
                        for (document in it.result!!) {
                            if (document != null) {
                                Log.i(
                                    "Search Result employer",
                                    "searchOnByCityDistrictVillage: " + document.toObject(Employer::class.java).name
                                )
                                employers.add(document.toObject(Employer::class.java))
                                Log.i(
                                    "Bekzhan",
                                    "searchOnByCityDistrictVillage:  Bekzhan inside firease result" + employers[0].name
                                )
                            }
                        }
                        binding.recView.adapter = employersAdapter
                        employersAdapter.updateEmployersList(employers)
                    }
                }
        }
        if (type_user.equals("employer")) {

            FirebaseFirestore.getInstance().collection("employees")
                .whereEqualTo("city", city)
                .whereEqualTo("district", district)
                .whereEqualTo("village", village)
                .get().addOnCompleteListener {
                    if (it.isSuccessful) {
                        val employees = mutableListOf<Employee>()
                        for (document in it.result!!) {
                            if (document != null) {
                                Log.i(
                                    "Search Result employee",
                                    "searchOnByCityDistrictVillage: " + document.toObject(Employee::class.java).name
                                )
                                employees.add(document.toObject(Employee::class.java))
                            }

                            Log.i(
                                "Bekzhan",
                                "searchOnByCityDistrictVillage: inside the result employee " + employees[0].name
                            )
                        }
                        binding.recView.adapter = employeesAdapter
                        employeesAdapter.updateEmployeesList(employees)
                    }
                }
        }


    }

    override fun searchOnByCity(city: String) {
        TODO("Not yet implemented")
    }

    override fun searchOnByCityDistrict(city: String, district: String) {
        TODO("Not yet implemented")
    }
}
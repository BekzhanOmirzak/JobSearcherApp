package com.example.profileapplication4.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.profileapplication4.R
import com.example.profileapplication4.models.Employee
import com.example.profileapplication4.models.Employer
import com.example.profileapplication4.ui.loginActivity.Resource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject


class Repository @Inject constructor() {

    private val firebaseAuthInstance = FirebaseAuth.getInstance()
    private val fireStoreInstance = FirebaseFirestore.getInstance()

    private val liveDataResourceLoginIn = MutableLiveData<Resource<String?>>()
    private val liveDataResourceRegister = MutableLiveData<Resource<String?>>()

    fun userLogIn(email: String, password: String) {
        liveDataResourceLoginIn.value = Resource.loading(null)
        firebaseAuthInstance.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    liveDataResourceLoginIn.value =
                        Resource.success(firebaseAuthInstance.currentUser?.email)
                }
            }
            .addOnFailureListener {
                liveDataResourceLoginIn.value = Resource.error("Failure", null)
            }

    }

    fun observeLiveData(): LiveData<Resource<String?>> {
        return liveDataResourceLoginIn
    }


    fun registerTheUser(
        email: String,
        password: String,
        name: String,
        surname: String,
        userType: Int
    ) {
        liveDataResourceRegister.value = Resource.loading(null)
        firebaseAuthInstance.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful) {
                when (userType) {
                    R.id.radioJobSearcher -> {
                        val employee = Employee(
                            firebaseAuthInstance.currentUser!!.uid,
                            email,
                            name,
                            surname,
                            password
                        )
                        fireStoreInstance.collection("employees").document(employee.employee_id)
                            .set(employee)
                            .addOnCompleteListener {
                                if (it.isSuccessful) {
                                    liveDataResourceRegister.value =
                                        Resource.success(firebaseAuthInstance.currentUser!!.email)
                                }
                            }.addOnFailureListener {
                                liveDataResourceRegister.value =
                                    Resource.error(" Error registering the user$it", null)
                            }
                    }

                    R.id.radioJobGiver -> {
                        val employer = Employer(
                            firebaseAuthInstance.currentUser!!.uid,
                            email,
                            password,
                            name,
                            surname
                        )
                        fireStoreInstance.collection("employers").document(employer.employer_id)
                            .set(employer).addOnCompleteListener {
                                if (it.isSuccessful) {
                                    liveDataResourceRegister.value =
                                        Resource.success(firebaseAuthInstance.currentUser!!.email)
                                } else {
                                    liveDataResourceRegister.value =
                                        Resource.error(
                                            "Error registering the use ${it.exception}",
                                            null
                                        )
                                }
                            }.addOnFailureListener {
                                liveDataResourceRegister.value =
                                    Resource.error("Error registering the use $it", null)
                            }
                    }

                }

            } else {
                liveDataResourceRegister.value = Resource.error("$it", null)
            }
        }.addOnFailureListener {
            liveDataResourceRegister.value = Resource.error("$it", null)
        }
    }


    fun observingLiveDataRegisteringUser(): LiveData<Resource<String?>> {
        return liveDataResourceRegister
    }


}



package com.example.profileapplication4.models

data class Employer
    (
    var employer_id: String = "",
    val email: String = "",
    val password: String = "",
    val name: String = "",
    val surname: String = "No Surname",
    val phone_number: String = "No phone number",
    val city: String = "No city",
    val district: String = "No district",
    val village: String = "No village",
    val image_url: String = "https://i.stack.imgur.com/aqZGX.png"
)
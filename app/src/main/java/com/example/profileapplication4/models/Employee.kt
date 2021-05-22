package com.example.profileapplication4.models

data class Employee
     (
     var employee_id: String = "",
     val email: String = "",
     val name: String = "No name",
     val surname: String = "No surname",
     val password: String = "",
     val phone_number: String = "No phone_number",
     val city: String = "No city",
     val district: String = "No district",
     val village: String = "No village",
     val image_url:String="https://i.stack.imgur.com/aqZGX.png"
)
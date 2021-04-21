package com.apps.nemsapp.model

data class LoginModel(
    val circle_id: Int,
    val circle_name: String,
    val circle_no: String,
    val csrf_token: String,
    val id: Int,
    val status_code: Int,
    val status_message: String,
    val ulbId: String,
    val user_id: String,
    val user_type: String,
    val username: String,
    val ward_id: Int,
    val ward_name: String,
    val zone_id: Int
)
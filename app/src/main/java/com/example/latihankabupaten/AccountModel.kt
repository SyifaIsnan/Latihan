package com.example.latihankabupaten


val EXTRA_ACCOUNT_ID = "accountExtra"

data class AccountModel( //buat nyimpen data akun (harus sama kaya respon dari web)

    val id: String,
    val email: String,
    val first_name: String,
    val last_name: String,
    val avatar: String,

)

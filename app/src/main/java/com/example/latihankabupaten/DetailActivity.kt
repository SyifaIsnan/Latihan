package com.example.latihankabupaten

import android.accounts.Account
import android.app.Activity
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import com.example.latihankabupaten.databinding.ActivityDetailBinding
import com.example.latihankabupaten.databinding.ActivityHomeBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detail)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val accountList = mutableListOf<AccountModel>()

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if(intent.hasExtra("id")){
            val id: String = intent.getStringExtra("id").toString()

            lifecycleScope.launch(Dispatchers.IO) {
                val koneksi = URL("https://reqres.in/api/users/" + id).openConnection() as HttpURLConnection
                val inputString = koneksi.inputStream.bufferedReader().readText()
                val jsonObject = JSONObject(inputString).getJSONObject("data")

                val koneksigambar = URL(jsonObject.getString("avatar")).openConnection() as HttpURLConnection
                val stream = koneksigambar.inputStream
                val gambar = BitmapFactory.decodeStream(stream)
                withContext(Dispatchers.Main){

                    binding.foto.setImageBitmap(gambar)
                    binding.nama.text = jsonObject.getString("first_name") +  " " + jsonObject.getString("last_name")
                    binding.email.text = jsonObject.getString("email")


                }




            }
        }




    }
}
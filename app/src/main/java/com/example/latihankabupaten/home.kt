package com.example.latihankabupaten

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.latihankabupaten.databinding.ActivityHomeBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

class home : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding = ActivityHomeBinding.inflate(layoutInflater)   // kode ini untuk menghubungkan kode dengan desain tampilan dari file activity_home.xml.
        setContentView(binding.root)

        val accountList = mutableListOf<AccountModel>() //


        lifecycleScope.launch(Dispatchers.IO) {

            try{
                val koneksi = URL("https://reqres.in/api/users?page=1").openConnection() as HttpURLConnection // untuk mengkoneksi kan ke server web
                val inputString = koneksi.inputStream.bufferedReader().readText() // untuk mendapat data dari web
                val jsonObject = JSONObject(inputString) // kalau dari web awalnya "{" berarti pakai jsonObject, kalau dari web awalnya "[" berarti pakai jsonArray,
                val jsonArray = jsonObject.getJSONArray("data") //string data menyesuaikan nama properti webnya
                for (i in 0 until jsonArray.length()){  // untuk mengakses index dari data web
                    val jsonAccountObject = jsonArray.getJSONObject(i) //
                    accountList.add(
                        AccountModel(
                            id = jsonAccountObject.getString("id"),
                            email = jsonAccountObject.getString("email"),
                            first_name = jsonAccountObject.getString("first_name"),
                            last_name = jsonAccountObject.getString("last_name"),
                            avatar = jsonAccountObject.getString("avatar"),
                        )
                    )
                }


                val TAG = "home"
                Log.d(TAG, "onCreate: $inputString")
                withContext(Dispatchers.Main){
                    binding.RecyclerView.layoutManager = LinearLayoutManager(this@home) //untuk mengatur tampilan layout, linearlayout berarti ke bawah
                    binding.RecyclerView.adapter = AccountAdapter(this@home, accountList) //untuk merubah data dari kkotlin ke xml

                }

            } catch (e: Exception){
                withContext(Dispatchers.Main){
                    Toast.makeText(this@home, "Gagal!", Toast.LENGTH_SHORT).show()  //this@home itu buat tampilan yang saat ini
                }
            }

        }
    }
}
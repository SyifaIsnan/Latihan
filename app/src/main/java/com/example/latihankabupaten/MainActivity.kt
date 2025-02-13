package com.example.latihankabupaten

import android.content.Intent
import android.os.Bundle
import android.renderscript.ScriptGroup.Binding
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.latihankabupaten.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button.setOnClickListener{
            val username = binding.editTextText.text.toString()
            val password = binding.editTextNumberPassword.text.toString()

            if(username == "admin" && password == "12345"){
                Toast.makeText(this, "Berhasil login!", Toast.LENGTH_SHORT).show()

                val gantihal = Intent(this, home::class.java)
                startActivity(gantihal)
            }



        }

    }
}
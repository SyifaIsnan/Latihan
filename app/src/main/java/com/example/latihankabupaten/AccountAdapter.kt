package com.example.latihankabupaten

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.net.HttpURLConnection
import java.net.URL

class AccountAdapter(val context: Context, val accountList: MutableList<AccountModel>): RecyclerView.Adapter<AccountAdapter.ViewHolder>() {  //buat ngerubah data kotlin jadi tampilan xml




    class ViewHolder(view: View):RecyclerView.ViewHolder(view) { //view holder = buat nyimpen komponen
        val email = view.findViewById<TextView>(R.id.email)
        val nama = view.findViewById<TextView>(R.id.nama)
        val foto = view.findViewById<ImageView>(R.id.foto)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder { //untuk membuat viewholder
        val layout = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false) //merubah data list item jadi kotlin
        return ViewHolder(layout) //membuat viewholder
    }

    override fun getItemCount(): Int {
        return accountList.size //supaya adapter tau berapa jumlah datanya
    }

    private fun updateUI(holder: ViewHolder, account: AccountModel) = runBlocking {
        launch(Dispatchers.IO){
            val koneksi = URL(account.avatar).openConnection() as HttpURLConnection
            val stream = koneksi.inputStream //nangkep data gambar
            val gambar = BitmapFactory.decodeStream(stream) //ngerubah gambar yang masih random jadi urut
            (context as Activity).runOnUiThread {
                val resources = context.resources
                holder.foto.setImageBitmap(gambar)
                holder.nama.text = account.first_name + " " + account.last_name
                holder.email.text = account.email

            }

        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val account = accountList[position] //memposisi data sesuai urutan atau index
        updateUI(holder, account) //untuk mengupdate ui sesuai data yand didapat


        holder.itemView.setOnClickListener{
            itemClick(account.id)
        }
    }

    private fun itemClick(id: String) {

        val intent = Intent(context, DetailActivity::class.java)
        intent.putExtra("id", id)
        (context as Activity).startActivity(intent)

    }



}
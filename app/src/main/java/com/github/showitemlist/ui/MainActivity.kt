package com.github.showitemlist.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.github.showitemlist.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main)
        var tvShowRepositories: TextView = findViewById(R.id.show_repositry)
        tvShowRepositories.setOnClickListener {
            val intent = Intent(this, ItemListMainActivity::class.java)
            startActivity(intent)
        }


    }
}
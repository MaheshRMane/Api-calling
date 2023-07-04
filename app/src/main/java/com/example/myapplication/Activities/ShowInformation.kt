package com.example.myapplication.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.myapplication.databinding.ActivityShowBinding

class ShowInformation : AppCompatActivity() {

    lateinit var binding: ActivityShowBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShowBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent = intent.extras

        binding.apply {
            Glide.with(this@ShowInformation).load(intent?.getString("image")).into(ivImageView)
            title.text = intent?.getString("title")
            price.text = intent?.getString("price")
            Description.text = intent?.getString("description")
            category.text = intent?.getString("category")
        }
    }

}
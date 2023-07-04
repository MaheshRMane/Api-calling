package com.example.myapplication.Activities

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.example.myapplication.Api.RetrofitInstance
import com.example.myapplication.Model.Data
import com.example.myapplication.R
import com.example.myapplication.databinding.MainactivityBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : ComponentActivity() {

    private lateinit var binding: MainactivityBinding
    private var progressBar: Dialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSubmit.setOnClickListener {
            if (validation()) {
                showProgressBar()
                savedData()
            }
        }
    }

    private fun savedData() {

        val dataRequest = Data(
            binding.etCategory.text.toString(),
            binding.etDiscription.text.toString(),
            "https://i.pravatar.cc",
            binding.etPrice.text.toString(),
            binding.etTitle.text.toString()
        )
        val call: Call<Data> = RetrofitInstance.apiInterface.getData(dataRequest)
        call.enqueue(object : Callback<Data?> {
            override fun onResponse(call: Call<Data?>, response: Response<Data?>) {

                try {
                    if (response.code() == 200) {
                        cancelProgressBar()

                        val responseData = response.body()
                        val intent = Intent(this@MainActivity, ShowInformation::class.java)
                        intent.putExtra("title", responseData?.title)
                        intent.putExtra("category", responseData?.category)
                        intent.putExtra("price", responseData?.price)
                        intent.putExtra("description", responseData?.description)
                        intent.putExtra("image", responseData?.image)
                        startActivity(intent)

                        Toast.makeText(this@MainActivity, "product added successfully", Toast.LENGTH_SHORT).show()
                    }
                } catch (e: Exception) {
                    e.stackTrace
                }
            }
            override fun onFailure(call: Call<Data?>, t: Throwable) {
                Toast.makeText(this@MainActivity, t.localizedMessage, Toast.LENGTH_SHORT).show()
            }
        })
    }


    private fun validation(): Boolean {

        if (binding.etTitle.text.isEmpty()) {
            Toast.makeText(this@MainActivity, "enter the title", Toast.LENGTH_SHORT).show()
            return false
        } else if (binding.etPrice.text.isEmpty()) {
            Toast.makeText(this@MainActivity, "enter the price", Toast.LENGTH_SHORT).show()
            return false
        } else if (binding.etDiscription.text.isEmpty()) {
            Toast.makeText(this@MainActivity, "enter the description", Toast.LENGTH_SHORT).show()
            return false
        } else if (binding.etCategory.text.isEmpty()) {
            Toast.makeText(this@MainActivity, "enter the category", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    private fun showProgressBar() {
        progressBar = Dialog(this@MainActivity)
        progressBar?.setContentView(R.layout.progress_bar)
        progressBar?.show()
    }

    private fun cancelProgressBar() {
        if (progressBar != null) {
            progressBar?.dismiss()
            progressBar = null
        }
    }
}
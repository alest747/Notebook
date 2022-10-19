package com.example.bloknot

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.example.bloknot.databinding.ActivityMainBinding
import com.example.bloknot.db.MyDbManager
import com.example.bloknot.fragments.DataFragModel


class MainActivity : AppCompatActivity() {
    lateinit var binding:ActivityMainBinding
    private val myDbManager = MyDbManager(this)
    private val dataFragModel:DataFragModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dataFragModel.message.observe(this, {
            it
        if(it.toString() != "") binding.buttonEnter.visibility = View.VISIBLE})

    }


    fun onClickActivityRec(view: View){
        val intent = Intent(this, RecActivity::class.java)
        startActivity(intent)
    }

}
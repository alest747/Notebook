package com.example.bloknot

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import com.example.bloknot.databinding.ActivityMainBinding
import com.example.bloknot.db.MyDbManager
import com.example.bloknot.fragments.DataFragModel
import com.example.bloknot.fragments.FragmentMain
import com.example.bloknot.fragments.FragmentReg


class MainActivity : AppCompatActivity() {
    lateinit var binding:ActivityMainBinding
    private val myDbManager = MyDbManager(this)
    private val dataFragModel:DataFragModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



    }

    override fun onResume() {
        super.onResume()
        myDbManager.openDb()
        val dataList = myDbManager.readDbData()
        for(item in dataList ){
        }
    }

    fun onClickSave(view: View){
        myDbManager.openDb()
        val dataList = myDbManager.readDbData()
        for(item in dataList ){
        }


    }

    override fun onDestroy() {
        super.onDestroy()
        myDbManager.closeDb()
    }

    fun onClickActivityRec(view: View){
        val intent = Intent(this, RecActivity::class.java)
        startActivity(intent)
    }

    fun onClickReg(view: View){
    }
}
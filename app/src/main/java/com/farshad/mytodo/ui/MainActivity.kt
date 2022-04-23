package com.farshad.mytodo.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import androidx.room.Room
import androidx.room.RoomDatabase
import com.farshad.mytodo.arch.ToBuyViewModel
import com.farshad.mytodo.database.AppDatabase
import com.farshad.mytodo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var binding: ActivityMainBinding?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding?.root)

        //mainActivity initialize the data for us as we enter the application
        val viewModel:ToBuyViewModel by viewModels()
        viewModel.init(AppDatabase.getDatabase(this))

    }//FUN


























    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}
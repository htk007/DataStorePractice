package com.example.datastoreexample

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.*
import com.example.datastoreexample.databinding.ActivityMainBinding
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var dataStoreManager: DataStoreManager
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this@MainActivity)[MainViewModel::class.java]
        dataStoreManager = DataStoreManager(this)

        checkThemeMode()
        checkStatus()

        binding.apply {
            modeSwitch.setOnCheckedChangeListener { _, isChecked ->
                lifecycleScope.launch {
                    when (isChecked) {
                        true -> viewModel.setTheme(true)
                        false -> viewModel.setTheme(false)
                    }
                }
            }
            buttonSaveStatus.setOnClickListener {
                lifecycleScope.launch {
                var statusString = editTextStatus.text.toString()
                    if(statusString.isNotEmpty()){
                        viewModel.setStatus(statusString)
                    }else{
                        Toast.makeText(applicationContext,"NOT EMPTY",Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun checkStatus(){
        binding.apply {
           viewModel.getStatus.observe( this@MainActivity){statusValue ->
               textViewStatus.text = statusValue.toString()
           }
        }
    }

    private fun checkThemeMode() {
        binding.apply {
            viewModel.getTheme.observe(this@MainActivity) { isDarkMode ->
                when (isDarkMode) {
                    true -> {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                        modeSwitch.isChecked = true
                        modeSwitch.text="Dark Mode"
                        imgStatus.setAnimation(R.raw.night)
                    }
                    false -> {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                        modeSwitch.isChecked = false
                        modeSwitch.text="Light Mode"
                        imgStatus.setAnimation(R.raw.day)
                    }
                }
            }
        }
    }
}
package com.maxssoft.mytappoints.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.maxssoft.mytappoints.R
import com.maxssoft.mytappoints.databinding.ActivityMainBinding

/**
 * Главный экран приложения, содержит тулбар и контейнер для фрагментов
 */
class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        binding.init()
    }

    private fun ActivityMainBinding.init() {
        setContentView(root)
        setSupportActionBar(toolbar)

        findNavController(R.id.nav_host_fragment_content_main).run {
            appBarConfiguration = AppBarConfiguration(graph)
            setupActionBarWithNavController(this@run, appBarConfiguration)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return findNavController(R.id.nav_host_fragment_content_main).navigateUp(appBarConfiguration) ||
            super.onSupportNavigateUp()
    }
}
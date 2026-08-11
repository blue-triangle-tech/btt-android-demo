package com.bluetriangle.bluetriangledemo.layout

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.lifecycle.ViewModelProvider
import com.bluetriangle.bluetriangledemo.ConfigurationViewModel
import com.bluetriangle.bluetriangledemo.R
import com.bluetriangle.bluetriangledemo.databinding.ActivityConfigurationLayoutBinding

class ConfigurationLayoutActivity : AppCompatActivity() {
    private lateinit var binding: ActivityConfigurationLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConfigurationLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        applyStatusBarInset()

        binding.lifecycleOwner = this
        binding.viewModel = ViewModelProvider(this).get(ConfigurationViewModel::class.java)

        // No top inset here: this sits below the toolbar, which already clears the status bar.
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, 0, systemBars.right, systemBars.bottom)
            insets
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }

    private fun applyStatusBarInset() {
        val toolbarPaddingTop = binding.toolbar.paddingTop
        ViewCompat.setOnApplyWindowInsetsListener(binding.toolbar) { view, insets ->
            val statusBar = insets.getInsets(WindowInsetsCompat.Type.statusBars())
            view.updatePadding(top = toolbarPaddingTop + statusBar.top)
            insets
        }
    }
}
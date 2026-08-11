package com.bluetriangle.bluetriangledemo.layout

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.bluetriangle.analytics.Tracker
import com.bluetriangle.bluetriangledemo.DemoApplication
import com.bluetriangle.bluetriangledemo.R
import com.bluetriangle.bluetriangledemo.databinding.ActivityStoreBinding
import com.bluetriangle.bluetriangledemo.tests.MemoryMonitor
import com.bluetriangle.bluetriangledemo.utils.copyToClipboard
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StoreActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStoreBinding
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityStoreBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        applyStatusBarInset()

        val navView: BottomNavigationView = binding.navView

        (application as DemoApplication).memoryMonitor.memoryWarningListener = object:MemoryMonitor.MemoryWarningListener {
            override fun onMemoryWarning(memoryWarning: MemoryMonitor.MemoryWarning) {
                runOnUiThread {
                    AlertDialog.Builder(this@StoreActivity)
                        .setTitle(R.string.memory_warning_title)
                        .setMessage(R.string.memory_warning_message)
                        .setPositiveButton(R.string.memory_warning_ok) { dialog, _ ->
                            dialog.dismiss()
                        }
                        .show()
                }
            }
        }

        binding.sessionid.text = Tracker.instance!!.configuration.sessionId
        binding.sessionid.setOnClickListener {
            it.context.copyToClipboard("Session ID", Tracker.instance!!.configuration.sessionId ?: "")
        }

        navController = findNavController(R.id.nav_host_fragment_activity_store)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_products,
                R.id.navigation_cart,
                R.id.navigation_profile,
                R.id.navigation_settings
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    /**
     * From Android 15 the window is laid out edge to edge, so the toolbar has to keep clear of
     * the status bar itself. On older releases the decor view consumes the inset and this is a
     * no-op. The bottom navigation applies the navigation bar inset on its own.
     */
    private fun applyStatusBarInset() {
        val toolbarPaddingTop = binding.toolbar.paddingTop
        ViewCompat.setOnApplyWindowInsetsListener(binding.toolbar) { view, insets ->
            val statusBar = insets.getInsets(WindowInsetsCompat.Type.statusBars())
            view.updatePadding(top = toolbarPaddingTop + statusBar.top)
            insets
        }
    }

}
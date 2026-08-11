package com.bluetriangle.bluetriangledemo.layout

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.webkit.WebView
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import com.bluetriangle.bluetriangledemo.utils.BTTWebViewClient
import com.bluetriangle.bluetriangledemo.DemoApplication
import com.bluetriangle.bluetriangledemo.R
import com.bluetriangle.bluetriangledemo.utils.TitleChromeClient
import com.bluetriangle.bluetriangledemo.databinding.ActivityHybridDemoLayoutBinding


class HybridDemoLayoutActivity : AppCompatActivity() {
    private var optionsMenu: Menu? = null

    private var binding: ActivityHybridDemoLayoutBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHybridDemoLayoutBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        setSupportActionBar(binding?.toolbar)
        setTitle(R.string.hybrid_demo)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        applyWindowInsets()

        binding?.webView?.webViewClient = BTTWebViewClient()
        binding?.webView?.webChromeClient = TitleChromeClient(this::setTitle)
        binding?.webView?.settings?.javaScriptEnabled = true
        binding?.webView?.settings?.domStorageEnabled = true
        binding?.webView?.settings?.allowFileAccess = true
        WebView.setWebContentsDebuggingEnabled(true)

        binding?.webView?.loadUrl(DemoApplication.DEMO_WEBSITE_URL)

        onBackPressedDispatcher.addCallback {
            binding?.webView?.apply {
                if (canGoBack()) {
                    goBack()
                    if(!canGoBack()) {
                        optionsMenu?.findItem(R.id.about_menu)?.isVisible = true
                    }
                } else {
                    finish()
                }
            }
        }
    }

    /**
     * From Android 15 the window is laid out edge to edge and android:statusBarColor is ignored,
     * so the toolbar has to paint the status bar strip and keep its content clear of it. The
     * WebView keeps clear of the navigation bar the same way.
     */
    private fun applyWindowInsets() {
        val binding = binding ?: return
        val toolbarPaddingTop = binding.toolbar.paddingTop
        ViewCompat.setOnApplyWindowInsetsListener(binding.toolbar) { view, insets ->
            val statusBar = insets.getInsets(WindowInsetsCompat.Type.statusBars())
            view.updatePadding(top = toolbarPaddingTop + statusBar.top)
            insets
        }
        ViewCompat.setOnApplyWindowInsetsListener(binding.webView) { view, insets ->
            val navigationBar = insets.getInsets(WindowInsetsCompat.Type.navigationBars())
            view.updatePadding(bottom = navigationBar.bottom)
            insets
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.webview_menu, menu)
        optionsMenu = menu
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            if(binding?.webView?.canGoBack() == true) {
                binding?.webView?.goBack()

                if(binding?.webView?.canGoBack() == false) {
                    optionsMenu?.findItem(R.id.about_menu)?.isVisible = true
                }
            } else {
                finish()
            }
            return true
        } else if(item.itemId == R.id.about_menu) {
            binding?.webView?.loadUrl("https://trackerdemo.github.io/hybrid-demo-info/")
            item.isVisible = false
        }
        return super.onOptionsItemSelected(item)
    }
}
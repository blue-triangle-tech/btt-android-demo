package com.bluetriangle.bluetriangledemo.layout

import android.os.Bundle
import android.view.MenuItem
import android.webkit.WebView
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import com.bluetriangle.bluetriangledemo.data.CartRepository
import com.bluetriangle.bluetriangledemo.utils.BTTWebViewClient
import com.bluetriangle.bluetriangledemo.utils.DEFAULT_SITE_ID
import com.bluetriangle.bluetriangledemo.utils.TitleChromeClient
import com.bluetriangle.bluetriangledemo.databinding.ActivityHybridDemoLayoutBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class WebCheckOutActivity : AppCompatActivity() {

    @Inject
    lateinit var cartRepository: CartRepository

    private var binding: ActivityHybridDemoLayoutBinding? = null

    private val closeOnConfirmation = Runnable {
        if (!isFinishing) finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHybridDemoLayoutBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        setSupportActionBar(binding?.toolbar)
        title = ""
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        applyWindowInsets()

        val cartId = intent.getLongExtra(CART_ID, 0)

        binding?.webView?.webViewClient = BTTWebViewClient { url ->
            if (url?.contains("confirmation.html") == true) {
                cartRepository.clearCartId()
                binding?.webView?.removeCallbacks(closeOnConfirmation)
                binding?.webView?.postDelayed(closeOnConfirmation, CONFIRMATION_CLOSE_DELAY)
            }
        }
        binding?.webView?.webChromeClient = TitleChromeClient(this::setTitle)
        binding?.webView?.settings?.javaScriptEnabled = true
        binding?.webView?.settings?.domStorageEnabled = true
        binding?.webView?.settings?.allowFileAccess = true
        WebView.setWebContentsDebuggingEnabled(true)

        binding?.webView?.loadUrl(
            "https://ganesh-aloha.github.io/sdk_demo_page/cart.html?siteId=${DEFAULT_SITE_ID}&cartId=${cartId}&redirectToHome=false"
        )

        onBackPressedDispatcher.addCallback {
            binding?.webView?.apply {
                if (canGoBack()) {
                    goBack()
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressedDispatcher.onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        binding?.webView?.removeCallbacks(closeOnConfirmation)
        binding = null
        super.onDestroy()
    }

    companion object {
        const val CART_ID = "cart_id"
        private const val CONFIRMATION_CLOSE_DELAY = 8000L
    }
}

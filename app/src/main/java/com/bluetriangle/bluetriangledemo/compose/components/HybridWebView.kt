package com.bluetriangle.bluetriangledemo.compose.components

import android.webkit.WebView
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.viewinterop.AndroidView
import com.bluetriangle.bluetriangledemo.utils.BTTWebViewClient
import com.bluetriangle.bluetriangledemo.utils.TitleChromeClient

@Composable
fun HybridWebView(url: String, setTitle: (String?) -> Unit, onUrlChange: (String?) -> Unit) {

    var backEnabled by rememberSaveable {
        mutableStateOf(false)
    }
    
    var webView: WebView? = null
    AndroidView(factory = { context ->
        WebView(context).apply {
            webView = this
            webViewClient = BTTWebViewClient {
                onUrlChange(it)
                backEnabled = canGoBack()
            }
            webChromeClient = TitleChromeClient(setTitle)
            settings.javaScriptEnabled = true
            settings.domStorageEnabled = true
            settings.allowFileAccess = true
            WebView.setWebContentsDebuggingEnabled(true)
        }
    }, update = {
        webView = it
        it.loadUrl(url)
    })

    BackHandler(backEnabled) {
        webView?.goBack()
    }
}
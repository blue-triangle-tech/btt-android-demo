package com.bluetriangle.bluetriangledemo.compose.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.bluetriangle.bluetriangledemo.compose.components.HybridWebView
import com.bluetriangle.bluetriangledemo.compose.components.InsetAwareTopAppBar
import com.bluetriangle.bluetriangledemo.compose.theme.BlueTriangleComposeDemoTheme
import com.bluetriangle.bluetriangledemo.data.CartRepository
import com.bluetriangle.bluetriangledemo.utils.DEFAULT_SITE_ID
import com.bluetriangle.bluetriangledemo.utils.postDelayedUI
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class WebCheckOutComposeActivity : ComponentActivity() {

    @Inject
    lateinit var cartRepository: CartRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var title by rememberSaveable {
                mutableStateOf("")
            }

            val cartNo by rememberSaveable {
                mutableLongStateOf(intent.getLongExtra(CART_ID, 0))
            }

            val url =
                "https://ganesh-aloha.github.io/sdk_demo_page/cart.html?siteId=${DEFAULT_SITE_ID}&cartId=${cartNo}&redirectToHome=false"

            BlueTriangleComposeDemoTheme {
                Scaffold(topBar = {
                    InsetAwareTopAppBar(title = {
                        Text(
                            text = title,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }, navigationIcon = {
                        IconButton(onClick = {
                            onBackPressedDispatcher.onBackPressed()
                        }, modifier = Modifier.padding(8.dp)) {
                            Icon(
                                Icons.AutoMirrored.Filled.ArrowBack,
                                "Back",
                                tint = MaterialTheme.colors.onPrimary
                            )
                        }
                    })
                }) {
                    Column(
                        modifier = Modifier
                            .padding(it)
                            .fillMaxSize()
                    ) {
                        HybridWebView(url, { title = it ?: FALL_BACK_URL }) { currentUrl ->
                            if (currentUrl?.contains("confirmation.html") == true) {
                                cartRepository.clearCartId()
                                postDelayedUI(6000) {
                                    if (!isFinishing) finish()
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    companion object {
        const val CART_ID = "cart_id"
        const val FALL_BACK_URL =
            "https://ganesh-aloha.github.io/sdk_demo_page/index.html?siteId=${DEFAULT_SITE_ID}&redirectToHome=true"
    }
}
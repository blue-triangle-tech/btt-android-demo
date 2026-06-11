package com.bluetriangle.bluetriangledemo.compose.screens

import android.content.Intent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.asFlow
import com.bluetriangle.analytics.compose.BttTimerEffect
import androidx.compose.ui.tooling.preview.Preview
import com.bluetriangle.bluetriangledemo.R
import com.bluetriangle.bluetriangledemo.utils.ErrorHandler
import com.bluetriangle.bluetriangledemo.compose.ManualTimerEffect
import com.bluetriangle.bluetriangledemo.compose.components.ErrorAlertDialog
import com.bluetriangle.bluetriangledemo.compose.theme.outline
import com.bluetriangle.bluetriangledemo.data.Product
import com.bluetriangle.bluetriangledemo.ui.products.ProductsViewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideSubcomposition
import com.bumptech.glide.integration.compose.RequestState

@Composable
fun ProductsScreen(productsViewModel: ProductsViewModel = hiltViewModel()) {
    BttTimerEffect(screenName = "Product Tab")
    ManualTimerEffect(screenName = "ProductsTabManualTimer")
    val products by productsViewModel.products.asFlow().collectAsState(listOf())
    val context = LocalContext.current

    ProductsScreenContent(
        products = products,
        onProductClick = { product ->
            context.startActivity(Intent(context, ProductDetailsActivity::class.java).apply {
                putExtra("product", product)
            })
        },
        errorHandler = productsViewModel.errorHandler,
        gridView = false
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ProductsScreenContent(
    products: List<Product>,
    onProductClick: (Product) -> Unit,
    errorHandler: ErrorHandler? = null,
    gridView: Boolean = false
) {
    if (gridView)
        ProductGrid(products) { onProductClick(it) }
    else
        ProductList(products) { onProductClick(it) }

    errorHandler?.let { ErrorAlertDialog(errorHandler = it) }
}

@Composable
fun ProductList(products: List<Product>, onProductClick: (Product) -> Unit) {
    LazyColumn(
        Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        items(products.size) {
            val product = products[it]
            ProductListItem(product) { onProductClick(product) }
        }
    }
}

@Composable
fun ProductGrid(products: List<Product>, onProductClick: (Product) -> Unit) {
    Column(
        Modifier
            .padding(8.dp)
            .fillMaxSize()
            .fillMaxHeight()
    ) {
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 150.dp),
            Modifier.weight(1f),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(products.size) {
                val product = products[it]
                ProductGridItem(product) {
                    onProductClick(product)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class, ExperimentalGlideComposeApi::class)
@Composable
fun ProductListItem(item: Product, onProductClick: () -> Unit) {
    Card(
        onClick = onProductClick,
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth(),
        elevation = 0.dp,
        border = BorderStroke(1.dp, MaterialTheme.colors.outline),
        shape = RoundedCornerShape(8.dp),
        content = {
            Row(Modifier.padding(8.dp), verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .height(80.dp)
                        .width(80.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .border(
                            1.dp,
                            MaterialTheme.colors.outline,
                            RoundedCornerShape(8.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    GlideSubcomposition(
                        model = item.image,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        when (state) {
                            RequestState.Loading -> Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator(
                                    modifier = Modifier.size(24.dp),
                                    strokeWidth = 2.dp
                                )
                            }

                            RequestState.Failure -> Image(
                                painter = painterResource(R.drawable.ic_error),
                                contentDescription = null,
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Fit
                            )

                            is RequestState.Success -> Image(
                                painter = painter,
                                contentDescription = item.description,
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Fit
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.width(10.dp))
                Column(verticalArrangement = Arrangement.Center) {
                    Text(
                        text = "${item.id}, ${item.name}",
                        style = TextStyle(fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
                    )
                    Spacer(modifier = Modifier.padding(2.dp))
                    Text(
                        text = "$${"%.2f".format(item.price)}",
                        style = TextStyle(fontSize = 14.sp)
                    )
                }
            }
        })
}

@OptIn(ExperimentalMaterialApi::class, ExperimentalGlideComposeApi::class)
@Composable
fun ProductGridItem(item: Product, onProductClick: () -> Unit) {
    Card(
        onClick = onProductClick,
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colors.surface),
        border = BorderStroke(1.dp, MaterialTheme.colors.outline),
        shape = RoundedCornerShape(8.dp),
        backgroundColor = MaterialTheme.colors.background,
    ) {
        Column(Modifier.padding(8.dp)) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(1.dp)
                    .height(128.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .border(
                        1.dp,
                        MaterialTheme.colors.outline,
                        RoundedCornerShape(8.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                GlideSubcomposition(
                    model = item.image,
                    modifier = Modifier.fillMaxSize()
                ) {
                    when (state) {
                        RequestState.Loading -> Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(32.dp),
                                strokeWidth = 2.dp
                            )
                        }

                        RequestState.Failure -> Image(
                            painter = painterResource(R.drawable.ic_error),
                            contentDescription = null,
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Fit
                        )

                        is RequestState.Success -> Image(
                            painter = painter,
                            contentDescription = item.description,
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Fit
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = item.name, maxLines = 2, overflow = TextOverflow.Ellipsis,
                modifier = Modifier.height(20.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = String.format("$%.2f", item.price), maxLines = 1)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProductsScreenPreview() {
    val productsState = remember {
        mutableStateOf(
            listOf(
                Product(
                    id = 2,
                    name = "iPhone X",
                    description = "SIM-Free, Model A19211 6.5-inch Super Retina HD display with OLED technology A12 Bionic chip with ...",
                    image = "https://trackerdemo.github.io/hybrid-demo-info/images/iphone_x.png",
                    price = 899.0
                ),
                Product(
                    id = 8,
                    name = "Microsoft Surface Laptop 4",
                    description = "Style and speed. Stand out on HD video calls backed by Studio Mics. Capture ideas on the vibrant touchscreen.",
                    image = "https://trackerdemo.github.io/hybrid-demo-info/images/microsoft_surface.jpg",
                    price = 1499.0
                ),
                Product(
                    id = 14,
                    name = "Fog Scent Xpressio Perfume",
                    description = "Product details of Best Fog Scent Xpressio Perfume 100ml For Men cool long lasting perfumes for Men",
                    image = "https://trackerdemo.github.io/hybrid-demo-info/images/fog_scent_xpressio_perfume.jpg",
                    price = 13.0
                ),
                Product(
                    id = 22,
                    name = "Apple Watch Series 9 (GPS, 45mm)",
                    description = "Smartwatch with always-on Retina display, fitness tracking, blood oxygen and ECG apps, plus seamless iPhone integration and fast charging.",
                    image = "https://trackerdemo.github.io/hybrid-demo-info/images/apple%20watch%20series%209%20(gps,%2045mm).jpg",
                    price = 429.0
                ),
                Product(
                    id = 23,
                    name = "Sony WH-1000XM5",
                    description = "Industry-leading wireless noise-canceling headphones with 30 hours of battery life, ultra-clear hands-free calls, and immersive high-resolution sound.",
                    image = "https://trackerdemo.github.io/hybrid-demo-info/images/sony%20wh-1000xm5.jpg",
                    price = 399.0
                ),
                Product(
                    id = 24,
                    name = "Apple MacBook Air (M3, 2024)",
                    description = "Ultra-thin, ultra-light laptop with Apple’s M3 chip, 13 Liquid Retina display, 18-hour battery life, and a fanless design for silent performance.",
                    image = "https://trackerdemo.github.io/hybrid-demo-info/images/apple%20macbook%20air%20(m3,%202024).jpg",
                    price = 1099.0
                )
            )
        )
    }

    ProductsScreenContent(
        products = productsState.value,
        onProductClick = {},
        gridView = false
    )
}
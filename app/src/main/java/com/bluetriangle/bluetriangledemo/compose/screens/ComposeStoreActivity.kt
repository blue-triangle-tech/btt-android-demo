package com.bluetriangle.bluetriangledemo.compose.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.bluetriangle.analytics.Tracker
import com.bluetriangle.bluetriangledemo.DemoApplication
import com.bluetriangle.bluetriangledemo.R
import com.bluetriangle.bluetriangledemo.compose.components.AppBottomNavigationBar
import com.bluetriangle.bluetriangledemo.compose.components.InsetAwareTopAppBar
import com.bluetriangle.bluetriangledemo.compose.components.NavHostContainer
import com.bluetriangle.bluetriangledemo.compose.components.NavItem
import com.bluetriangle.bluetriangledemo.compose.theme.BlueTriangleComposeDemoTheme
import com.bluetriangle.bluetriangledemo.utils.copyToClipboard
import dagger.hilt.android.AndroidEntryPoint
import java.util.UUID

@AndroidEntryPoint
class ComposeStoreActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BlueTriangleComposeDemoTheme {
                val title = rememberSaveable {
                    mutableStateOf("")
                }

                val showBackIcon = rememberSaveable {
                    mutableStateOf(false)
                }

                val navController = rememberNavController()
                val navItems = getNavItemsList(navController)
                Scaffold(topBar = {
                    InsetAwareTopAppBar(
                        title = { Text(text = title.value) },
                        // Null rather than an empty slot: a non-null slot still reserves the
                        // navigation icon's width, indenting the title on the tab destinations.
                        navigationIcon = if (showBackIcon.value) {
                            {
                                IconButton(onClick = { onBackPressedDispatcher.onBackPressed() }) {
                                    Icon(
                                        Icons.AutoMirrored.Filled.ArrowBack,
                                        contentDescription = "Back"
                                    )
                                }
                            }
                        } else null)
                }, bottomBar = {
                    AppBottomNavigationBar(navController = navController, navItems = navItems)
                }) {
                    Column(
                        modifier = Modifier
                            .padding(it)
                            .fillMaxSize()
                    ) {
                        SiteIDBar()
                        NavHostContainer(
                            title,
                            showBackIcon,
                            navController = navController,
                            navItems = navItems
                        )
                    }
                }
            }
        }

    }

    override fun onStart() {
        super.onStart()
        DemoApplication.checkAndAddDelay()
    }

    override fun onResume() {
        super.onResume()
        DemoApplication.checkAndAddDelay()
    }

    @Composable
    fun SiteIDBar() {
        val sessionIdAccessibility = stringResource(id = R.string.sessionid_accessibility)
        val context = LocalContext.current
        Column(modifier = Modifier.background(MaterialTheme.colors.primary)) {
            Row(
                modifier = Modifier
                    .padding(16.dp, 8.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = "Session ID: ",
                    fontSize = 14.sp,
                    color = MaterialTheme.colors.onPrimary
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = Tracker.instance?.configuration?.sessionId ?: "",
                    fontSize = 14.sp,
                    color = MaterialTheme.colors.onPrimary,
                    modifier = Modifier
                        .semantics {
                            contentDescription = sessionIdAccessibility
                        }
                        .clickable {
                            context.copyToClipboard(
                                "Session ID",
                                Tracker.instance?.configuration?.sessionId ?: ""
                            )
                        }
                )
            }
        }
    }
}

fun getNavItemsList(navController: NavHostController): List<NavItem> {
    return listOf(
        NavItem(
            "Products",
            icon = {
                Icon(
                    painterResource(id = R.drawable.baseline_apps_24),
                    contentDescription = "Products",
                    tint = it
                )
            },
            "product",
            destinations = listOf(
                NavItem.Destination("Product", "product/home") { ProductsScreen() }
            )
        ),
        NavItem(
            "Cart", icon = {
                Icon(
                    Icons.Outlined.ShoppingCart,
                    contentDescription = "Cart",
                    tint = it
                )
            }, "cart",
            destinations = listOf(
                NavItem.Destination("Cart", "cart/home") {
                    CartScreen({
                        navController.navigate("cart/checkout/${UUID.randomUUID()}")
                    })
                },
                NavItem.Destination(
                    "Checkout",
                    "cart/checkout/{checkoutId}",
                    true
                ) { CheckoutScreen(it.arguments?.getString("checkoutId") ?: "") }
            )),
        NavItem(
            "Profile", icon = {
                Icon(
                    Icons.Filled.Person,
                    contentDescription = "Profile",
                    tint = it
                )
            }, "profile",
            destinations = listOf(
                NavItem.Destination("Profile", "profile/home") {
                    ProfileScreen(
                        onOrderHistoryClick = { navController.navigate("profile/orders") },
                        onFavouritesClick = { navController.navigate("profile/favourites") },
                        onRecentActivityClick = { navController.navigate("profile/recent_activity") }
                    )
                },
                NavItem.Destination(
                    "Order History",
                    "profile/orders",
                    true
                ) { OrderHistoryScreen() },
                NavItem.Destination(
                    "Order History",
                    "profile/recent_activity",
                    true
                ) {
                    RecentActivityScreen(
                        onForwardToFavourites = { navController.navigate("profile/favourites") }
                    )
                },
                NavItem.Destination(
                    "Favourites",
                    "profile/favourites",
                    true
                ) { FavouritesScreen() }
            )),
        NavItem(
            "Settings", icon = {
                Icon(
                    Icons.Filled.Settings,
                    contentDescription = "Settings",
                    tint = it
                )
            }, "settings",
            destinations = listOf(
                NavItem.Destination("Settings", "settings/home") { SettingsScreen() }
            ))
    )
}

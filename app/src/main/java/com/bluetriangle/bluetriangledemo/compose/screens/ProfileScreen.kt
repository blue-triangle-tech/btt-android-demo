package com.bluetriangle.bluetriangledemo.compose.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bluetriangle.bluetriangledemo.compose.ManualTimerEffect
import com.bluetriangle.bluetriangledemo.compose.theme.BlueTriangleComposeDemoTheme
import com.bluetriangle.bluetriangledemo.compose.theme.outline
import com.bluetriangle.bluetriangledemo.data.DummyProfileData
import com.bluetriangle.bluetriangledemo.data.ProductAssetsRepository
import com.bluetriangle.bluetriangledemo.data.UserProfile

@Composable
fun ProfileScreen(
    onOrderHistoryClick: () -> Unit,
    onFavouritesClick: () -> Unit,
    onRecentActivityClick: () -> Unit
) {
    //ManualTimerEffect(screenName = "ProfileScreenManualTimer")

    val context = LocalContext.current
    val favouritesCount = remember { ProductAssetsRepository.favourites(context).size }

    ProfileScreenContent(
        onOrderHistoryClick = onOrderHistoryClick,
        onFavouritesClick = onFavouritesClick,
        onRecentActivityClick = onRecentActivityClick,
        favouritesCount = favouritesCount
    )
}

@Composable
fun ProfileScreenContent(
    onOrderHistoryClick: () -> Unit,
    onFavouritesClick: () -> Unit,
    onRecentActivityClick: () -> Unit,
    favouritesCount: Int,
    profile: UserProfile = DummyProfileData.profile
) {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        ProfileHeader(profile)
        ProfileStats(profile)
        ProfileMenuItem(
            icon = Icons.AutoMirrored.Filled.List,
            title = "Order History",
            subtitle = "${DummyProfileData.orders.size} orders placed",
            onClick = onOrderHistoryClick
        )
        ProfileMenuItem(
            icon = Icons.Filled.Favorite,
            title = "Favourites",
            subtitle = "$favouritesCount saved items",
            onClick = onFavouritesClick
        )
        ProfileMenuItem(
            icon = Icons.Filled.DateRange,
            title = "Recent Activity",
            subtitle = "Orders, then favourites",
            onClick = onRecentActivityClick
        )
    }
}

@Composable
private fun ProfileHeader(profile: UserProfile) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(88.dp)
                .background(MaterialTheme.colors.primary, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = profile.initials,
                style = TextStyle(
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colors.onPrimary
                )
            )
        }
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = profile.name,
            style = TextStyle(
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colors.onSurface
            )
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = profile.email,
            style = TextStyle(fontSize = 14.sp, color = MaterialTheme.colors.onSurface)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = profile.loyaltyTier,
            style = TextStyle(
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colors.secondary
            )
        )
    }
}

@Composable
private fun ProfileStats(profile: UserProfile) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = 0.dp,
        border = BorderStroke(1.dp, MaterialTheme.colors.outline),
        shape = RoundedCornerShape(8.dp),
        backgroundColor = MaterialTheme.colors.background
    ) {
        Column(Modifier.padding(16.dp)) {
//            ProfileDetailRow("Phone", profile.phone)
//            Spacer(modifier = Modifier.height(8.dp))
            ProfileDetailRow("Member since", profile.memberSince)
        }
    }
}

@Composable
private fun ProfileDetailRow(label: String, value: String) {
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Text(
            text = label,
            style = TextStyle(
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colors.onSurface
            )
        )
        Text(
            text = value,
            style = TextStyle(fontSize = 14.sp, color = MaterialTheme.colors.onSurface)
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun ProfileMenuItem(
    icon: ImageVector,
    title: String,
    subtitle: String,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        elevation = 0.dp,
        border = BorderStroke(1.dp, MaterialTheme.colors.outline),
        shape = RoundedCornerShape(8.dp),
        backgroundColor = MaterialTheme.colors.background
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(icon, contentDescription = title, tint = MaterialTheme.colors.primary)
            Spacer(modifier = Modifier.width(16.dp))
            Column(Modifier.weight(1f)) {
                Text(
                    text = title,
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colors.onSurface
                    )
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = subtitle,
                    style = TextStyle(fontSize = 13.sp, color = MaterialTheme.colors.onSurface)
                )
            }
            Icon(
                Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = null,
                tint = MaterialTheme.colors.primary
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    BlueTriangleComposeDemoTheme {
        ProfileScreenContent(
            onOrderHistoryClick = {},
            onFavouritesClick = {},
            onRecentActivityClick = {},
            favouritesCount = DummyProfileData.sampleFavourites.size
        )
    }
}

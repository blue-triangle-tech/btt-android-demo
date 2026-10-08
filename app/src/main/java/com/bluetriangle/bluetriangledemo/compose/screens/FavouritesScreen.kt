package com.bluetriangle.bluetriangledemo.compose.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bluetriangle.bluetriangledemo.compose.ManualTimerEffect
import com.bluetriangle.bluetriangledemo.compose.components.NetworkImage
import com.bluetriangle.bluetriangledemo.compose.theme.BlueTriangleComposeDemoTheme
import com.bluetriangle.bluetriangledemo.compose.theme.outline
import com.bluetriangle.bluetriangledemo.data.DummyProfileData
import com.bluetriangle.bluetriangledemo.data.FavouriteItem
import com.bluetriangle.bluetriangledemo.data.ProductAssetsRepository

@Composable
fun FavouritesScreen() {
    //ManualTimerEffect(screenName = "FavouritesScreenManualTimer")

    val context = LocalContext.current
    val favourites = remember { ProductAssetsRepository.favourites(context) }

    FavouritesScreenContent(favourites)
}

@Composable
fun FavouritesScreenContent(favourites: List<FavouriteItem>) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 150.dp),
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(favourites.size) {
            FavouriteGridItem(favourites[it])
        }
    }
}

@Composable
private fun FavouriteGridItem(item: FavouriteItem) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = 0.dp,
        border = BorderStroke(1.dp, MaterialTheme.colors.outline),
        shape = RoundedCornerShape(8.dp),
        backgroundColor = MaterialTheme.colors.background
    ) {
        Column(Modifier.padding(8.dp)) {
            NetworkImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(128.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .border(1.dp, MaterialTheme.colors.outline, RoundedCornerShape(8.dp)),
                model = item.image,
                contentDescription = item.name,
                contentScale = ContentScale.Fit,
                indicatorSize = 32.dp
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    Icons.Filled.Favorite,
                    contentDescription = "Favourite",
                    tint = MaterialTheme.colors.secondary,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.size(4.dp))
                Text(
                    text = item.category,
                    maxLines = 1,
                    style = TextStyle(fontSize = 12.sp, color = MaterialTheme.colors.onSurface)
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = item.name,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.height(40.dp),
                style = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colors.onSurface
                )
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = String.format("$%.2f", item.price),
                maxLines = 1,
                style = TextStyle(fontSize = 14.sp, color = MaterialTheme.colors.onSurface)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = if (item.inStock) "In stock" else "Out of stock",
                maxLines = 1,
                style = TextStyle(
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = if (item.inStock) Color(0xFF2E7D32) else Color(0xFFC62828)
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FavouritesScreenPreview() {
    BlueTriangleComposeDemoTheme {
        FavouritesScreenContent(DummyProfileData.sampleFavourites)
    }
}

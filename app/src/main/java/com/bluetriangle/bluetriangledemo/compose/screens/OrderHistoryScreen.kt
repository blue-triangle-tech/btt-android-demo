package com.bluetriangle.bluetriangledemo.compose.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
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
import com.bluetriangle.bluetriangledemo.data.Order

@Composable
fun OrderHistoryScreen(orders: List<Order> = DummyProfileData.orders) {
    ManualTimerEffect(screenName = "OrderHistoryScreenManualTimer")

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(orders.size) {
            OrderListItem(orders[it])
        }
    }
}

@Composable
private fun OrderListItem(order: Order) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = 0.dp,
        border = BorderStroke(1.dp, MaterialTheme.colors.outline),
        shape = RoundedCornerShape(8.dp),
        backgroundColor = MaterialTheme.colors.background
    ) {
        Row(Modifier.padding(8.dp), verticalAlignment = Alignment.CenterVertically) {
            NetworkImage(
                modifier = Modifier
                    .size(72.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .border(1.dp, MaterialTheme.colors.outline, RoundedCornerShape(8.dp)),
                model = order.image,
                contentDescription = order.itemSummary,
                contentScale = ContentScale.Fit
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column(Modifier.weight(1f)) {
                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = order.id,
                        style = TextStyle(
                            fontSize = 15.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colors.onSurface
                        )
                    )
                    OrderStatusLabel(order.status)
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = order.itemSummary,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    style = TextStyle(fontSize = 14.sp, color = MaterialTheme.colors.onSurface)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "${order.placedOn}  •  ${order.itemCount} item(s)  •  ${
                        String.format("$%.2f", order.total)
                    }",
                    style = TextStyle(fontSize = 13.sp, color = MaterialTheme.colors.onSurface)
                )
            }
        }
    }
}

@Composable
private fun OrderStatusLabel(status: String) {
    val color = when (status) {
        "Delivered" -> Color(0xFF2E7D32)
        "Cancelled", "Returned" -> Color(0xFFC62828)
        else -> MaterialTheme.colors.secondary
    }
    Text(
        text = status,
        modifier = Modifier
            .clip(RoundedCornerShape(4.dp))
            .background(color.copy(alpha = 0.15f))
            .padding(horizontal = 8.dp, vertical = 2.dp),
        style = TextStyle(fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = color)
    )
}

@Preview(showBackground = true)
@Composable
fun OrderHistoryScreenPreview() {
    BlueTriangleComposeDemoTheme {
        OrderHistoryScreen()
    }
}

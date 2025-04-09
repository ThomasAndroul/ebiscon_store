package com.example.ebisconstore.category

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ebisconstore.category.ProductsCardViewDimens.cardElevation
import com.example.ebisconstore.category.ProductsCardViewDimens.cardHeight
import com.example.ebisconstore.category.ProductsCardViewDimens.cardRoundCorner
import com.example.ebisconstore.category.ProductsCardViewDimens.smallGap
import com.example.ebisconstore.category.ProductsCardViewDimens.smallText

@Composable
fun ProductsCardView(
    navigateToCategory: (String) -> Unit
) {
    Row(
        modifier = Modifier.padding(smallGap)
    ) {
        Card(
            colors = CardDefaults.cardColors(containerColor = Color.Black),
            modifier = Modifier.height(cardHeight)
                .clickable { navigateToCategory("All Products") },
            shape = RoundedCornerShape(cardRoundCorner),
            elevation = CardDefaults.cardElevation(defaultElevation = cardElevation)
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "All Products",
                    lineHeight = smallText,
                    fontSize = smallText,
                    color = Color.White
                )
            }
        }
    }
}

object ProductsCardViewDimens {
    val smallGap = 8.dp
    val smallText = 16.sp
    val cardElevation = 4.dp
    val cardHeight = 80.dp
    val cardRoundCorner = 8.dp
}

@Preview(showBackground = true)
@Composable
fun ProductsCardViewPreview() {
    ProductsCardView(
        navigateToCategory = {}
    )
}
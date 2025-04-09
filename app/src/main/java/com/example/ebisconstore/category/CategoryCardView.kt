package com.example.ebisconstore.category

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import com.example.ebisconstore.category.CategoryCardViewDimens.cardElevation
import com.example.ebisconstore.category.CategoryCardViewDimens.cardRoundedCorner
import com.example.ebisconstore.category.CategoryCardViewDimens.cardSize
import com.example.ebisconstore.category.CategoryCardViewDimens.smallGap
import com.example.ebisconstore.category.CategoryCardViewDimens.smallText
import java.util.Locale

@Composable
fun CategoryCardView(
    category: String,
    navigateToCategory: (String) -> Unit
) {
    Column(
        modifier = Modifier.padding(smallGap),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            colors = CardDefaults.cardColors(containerColor = Color.Black),
            modifier = Modifier.size(cardSize)
                .clickable { navigateToCategory(category) },
            shape = RoundedCornerShape(cardRoundedCorner),
            elevation = CardDefaults.cardElevation(defaultElevation = cardElevation)
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = category.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() },
                    lineHeight = smallText,
                    fontSize = smallText,
                    color = Color.White
                )
            }
        }
    }
}

object CategoryCardViewDimens {
    val smallGap = 8.dp
    val smallText = 16.sp
    val cardSize = 150.dp
    val cardRoundedCorner = 8.dp
    val cardElevation = 4.dp
}

@Preview(showBackground = true)
@Composable
fun CategoryCardViewPreview() {
    CategoryCardView(
        category = "Category",
        navigateToCategory = {}
    )
}
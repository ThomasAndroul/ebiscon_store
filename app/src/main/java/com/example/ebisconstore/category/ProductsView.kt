package com.example.ebisconstore.category

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.ebisconstore.category.ProductsViewDimens.cardElevation
import com.example.ebisconstore.category.ProductsViewDimens.imageSize
import com.example.ebisconstore.category.ProductsViewDimens.mediumGap
import com.example.ebisconstore.category.ProductsViewDimens.mediumText
import com.example.ebisconstore.category.ProductsViewDimens.roundCorner
import com.example.ebisconstore.category.ProductsViewDimens.smallGap
import com.example.ebisconstore.category.ProductsViewDimens.smallText
import com.example.ebisconstore.category.ProductsViewDimens.tinyGap

@Composable
fun ProductsView(
    product : Product,
    navigateToDetail : (Product) -> Unit
){
    ElevatedCard(
        modifier = Modifier
            .padding(smallGap)
            .fillMaxWidth()
            .clickable { navigateToDetail(product) },
        shape = RoundedCornerShape(roundCorner),
        elevation = CardDefaults.elevatedCardElevation(cardElevation),
        colors = CardDefaults.elevatedCardColors(
            containerColor = Color.White
        )
    ) {
        Column {
            Image(
                painter = rememberAsyncImagePainter(product.image),
                contentDescription = "Food Image",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(imageSize)
            )
            Column(
                modifier = Modifier
                    .padding(smallGap)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = product.title,
                        fontSize = mediumText,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    Text(
                        text = product.price.toString() + "€",
                        fontSize = smallText,
                        color = Color.Gray
                    )
                }

                Spacer(modifier = Modifier.height(smallGap))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = product.category,
                        color = Color.Red,
                        fontWeight = FontWeight.Bold,
                        fontSize = smallText,
                        modifier = Modifier
                            .padding(horizontal = mediumGap, vertical = tinyGap)
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = "Rating",
                            tint = Color.Green
                        )
                        Text(
                            text = product.rating.rate.toString(),
                            color = Color.Black,
                            fontSize = smallText,
                            fontWeight = FontWeight.Medium
                        )
                        Text(
                            text = "(${product.rating.count} reviews)",
                            color = Color.Gray,
                            fontSize = smallText
                        )
                    }
                }
            }
        }
    }
}

object ProductsViewDimens {
    val tinyGap = 6.dp
    val smallGap = 8.dp
    val mediumGap = 12.dp
    val smallText = 14.sp
    val mediumText = 18.sp
    val imageSize = 150.dp
    val roundCorner = 8.dp
    val cardElevation = 4.dp
}

@Preview(showBackground = true)
@Composable
fun ProductsViewPreview() {
    ProductsView(product = Product(
        id = 1,
        title = "Product 1",
        price = 10.0,
        description = "Description 1",
        category = "Category 1",
        image = "https://via.placeholder.com/150",
        rating = Rating(
            rate = 4.5,
            count = 10
        )
    ),
        navigateToDetail = {}
    )
}
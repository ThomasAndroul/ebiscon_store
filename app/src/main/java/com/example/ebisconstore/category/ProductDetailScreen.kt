package com.example.ebisconstore.category

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter

@Composable
fun ProductDetailScreen(
    product: Product,
    updateProduct: (Product, Boolean, () -> Unit, (String) -> Unit) -> Unit,
    onBack: () -> Unit
) {
    var title by remember { mutableStateOf(product.title) }
    var price by remember { mutableStateOf(product.price.toString()) }
    var description by remember { mutableStateOf(product.description) }
    var category by remember { mutableStateOf(product.category) }

    var showDialog by remember { mutableStateOf(false) }
    var currentField by remember { mutableStateOf("") }
    var currentValue by remember { mutableStateOf("") }

    fun openDialog(field: String, value: String) {
        currentField = field
        currentValue = value
        showDialog = true
    }

    val context = LocalContext.current

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Edit $currentField") },
            text = {
                TextField(
                    value = currentValue,
                    onValueChange = { currentValue = it },
                    singleLine = true
                )
            },
            confirmButton = {
                TextButton(onClick = {
                    when (currentField) {
                        "title" -> title = currentValue
                        "price" -> price = currentValue
                        "description" -> description = currentValue
                        "category" -> category = currentValue
                    }
                    showDialog = false
                }) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = rememberAsyncImagePainter(product.image),
            contentDescription = "Product Image",
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = title,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier.clickable { openDialog("title", title) }
        )

        Text(
            text = "$price €",
            fontSize = 20.sp,
            color = Color.Gray,
            modifier = Modifier.clickable { openDialog("price", price) }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { openDialog("category", category) }
        ) {
            Text(
                text = "Category:",
                fontWeight = FontWeight.Bold
            )
            Text(
                text = category,
                color = Color.Red,
                fontSize = 16.sp
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { openDialog("description", description) }
        ) {
            Text(
                text = "Description:",
                fontWeight = FontWeight.Bold
            )
            Text(
                text = description,
                fontSize = 16.sp
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                val updatedProduct = product.copy(
                    title = title,
                    price = price.toDoubleOrNull() ?: product.price,
                    description = description,
                    category = category
                )
                updateProduct(updatedProduct, true, {
                    Toast.makeText(context, "Updated successfully", Toast.LENGTH_SHORT).show()
                    onBack()
                }, { error ->
                    Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
                })
            }
        ) {
            Text("Update Product")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProductDetailScreenPreview() {
    ProductDetailScreen(
        product = Product(
            id = 1,
            title = "Product 1",
            price = 10.0,
            description = "Description 1",
            category = "Category 1",
            image = "image",
            rating = Rating(
                rate = 4.5,
                count = 10
            )
        ),
        updateProduct = { _, _, _, _ -> },
        onBack = {}
    )
}
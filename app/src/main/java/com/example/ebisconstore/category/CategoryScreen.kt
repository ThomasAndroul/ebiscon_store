package com.example.ebisconstore.category

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ebisconstore.ESToAppBar

@Composable
fun CategoryScreen(
    uiState: ProductUIState,
    navigateToCategory: (String) -> Unit,
    onBack: () -> Unit
) {

    Scaffold(
        contentWindowInsets = WindowInsets(0.dp),
        containerColor = Color.White,
        topBar = {
            ESToAppBar(
                title = "Categories",
                onBack = { onBack() }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Choose a category",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            when {
                uiState.loading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
                }

                uiState.error != null -> {
                    Text(text = uiState.error.toString(), color = Color.Red)
                }

                else -> {
                    val categories = uiState.products.groupBy { it.category }
                    Column(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(2),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 16.dp)
                        ) {
                            categories.forEach { (category) ->
                                item {
                                    CategoryCardView(
                                        category = category,
                                        navigateToCategory = navigateToCategory
                                    )
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        ProductsCardView(
                            navigateToCategory = navigateToCategory
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CategoryScreenPreview() {
    CategoryScreen(
        uiState = ProductUIState(),
        navigateToCategory = {},
        onBack = {}
    )
}
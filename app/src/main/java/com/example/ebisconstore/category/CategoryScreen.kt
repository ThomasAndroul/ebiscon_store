package com.example.ebisconstore.category

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun CategoryScreen(
    viewModel: ProductsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Available Products",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
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
                LazyColumn(
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    categories.forEach { (category) ->
                        item {
                            Text(
                                text = category.replaceFirstChar { it.uppercase() },
                                color = Color.Black,
                                style = MaterialTheme.typography.headlineLarge,
                                modifier = Modifier.padding(start = 8.dp, bottom = 8.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}
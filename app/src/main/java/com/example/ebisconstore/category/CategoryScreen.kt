package com.example.ebisconstore.category

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Button
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
import com.example.ebisconstore.category.CategoryScreenDimens.mediumGap
import com.example.ebisconstore.category.CategoryScreenDimens.smallGap
import com.example.ebisconstore.category.CategoryScreenDimens.titleSize
import com.example.ebisconstore.category.CategoryScreenDimens.zeroGap

@Composable
fun CategoryScreen(
    uiState: ProductUIState,
    navigateToCategory: (String) -> Unit,
    onBack: () -> Unit,
    clearUserToken: () -> Unit
) {

    Scaffold(
        contentWindowInsets = WindowInsets(zeroGap),
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
                .padding(smallGap),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Choose a category",
                fontSize = titleSize,
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
                                .padding(top = mediumGap)
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
                        Spacer(modifier = Modifier.height(smallGap))
                        ProductsCardView(
                            navigateToCategory = navigateToCategory
                        )
                        Button(
                            onClick = {
                                clearUserToken()
                                Log.d("LoginScreen", "User token cleared. Please log in again.")
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Clear User Token")
                        }
                    }
                }
            }
        }
    }
}

object CategoryScreenDimens {
    val zeroGap = 0.dp
    val smallGap = 8.dp
    val mediumGap = 16.dp
    val titleSize = 32.sp
}

@Preview(showBackground = true)
@Composable
fun CategoryScreenPreview() {
    CategoryScreen(
        uiState = ProductUIState(),
        navigateToCategory = {},
        onBack = {},
        clearUserToken = {}
    )
}
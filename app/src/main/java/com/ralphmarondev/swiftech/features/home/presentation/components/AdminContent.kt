package com.ralphmarondev.swiftech.features.home.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ralphmarondev.swiftech.features.home.domain.model.Options

@Composable
fun ColumnScope.AdminContent(
    options: List<Options>
) {
    Text(
        text = "Manage",
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .align(Alignment.Start),
        color = MaterialTheme.colorScheme.secondary
    )

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 4.dp)
    ) {
        items(
            count = options.size
        ) { index ->
            OptionsCard(
                name = options[index].name,
                image = options[index].image,
                onClick = options[index].onClick
            )
        }
    }
}
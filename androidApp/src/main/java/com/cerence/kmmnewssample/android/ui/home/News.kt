package com.cerence.kmmnewssample.android.ui.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumedWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Scale
import com.cerence.kmmnewssample.android.R
import com.cerence.kmmnewssample.android.ui.components.HomeAppBar
import com.cerence.kmmnewssample.android.ui.components.Loader
import com.cerence.kmmnewssample.domain.domain_model.NewsDataModel
import com.cerence.kmmnewssample.presentation.NewsState
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun News(state: NewsState,
         drawerState: DrawerState,
         scope: CoroutineScope,
         destinationsNavigator: DestinationsNavigator,
         openDrawer: () -> Unit = {}) {

    // to keep track if the animation is playing
    // and play pause accordingly
    var isPlaying by remember {
        mutableStateOf(true)
    }
    // for speed
    var speed by remember {
        mutableStateOf(1f)
    }

    Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
        HomeAppBar(
            titleRes = R.string.app_heading,
            actionIcons = { },
            navigationIcon = {
                IconButton(onClick = {
                    scope.launch { if (drawerState.isClosed) drawerState.open() else drawerState.close() }
                }) {
                    Icon(imageVector = Icons.Default.Menu, contentDescription = null)
                }
            }
        )
    }) { innerPadding ->
        val listState = rememberLazyListState()
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .consumedWindowInsets(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp),
            state = listState,
            contentPadding = PaddingValues(16.dp)
        ) {
            item {
                Spacer(modifier = Modifier.windowInsetsTopHeight(WindowInsets.safeDrawing))
            }

            when (state) {
                is NewsState.Error -> {
                    isPlaying = !isPlaying
                    item {
                        Text(text = state.errorMsg, style = MaterialTheme.typography.headlineMedium)
                    }
                }

                is NewsState.Idle -> {
                    isPlaying = !isPlaying
                }

                is NewsState.Loading -> {
                    placeHolder(isPlaying, speed)
                }

                is NewsState.Success -> {
                    isPlaying = !isPlaying
                    headlines(state.list, destinationsNavigator)
                }
            }
        }
    }
}

private fun LazyListScope.placeHolder(isPlaying: Boolean, speed: Float) {
    item {
        Column(modifier = Modifier.size(100.dp,100.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center) {
            Loader(isPlaying,speed)
        }
    }
}

private fun LazyListScope.headlines(
    list: List<NewsDataModel>,
    destinationsNavigator: DestinationsNavigator
) {
    items(list) { item ->
        HeadlinesCard(item, destinationsNavigator)
    }
}

@Composable
fun HeadlinesCard(item: NewsDataModel, destinationsNavigator: DestinationsNavigator) {
    Card(
        modifier = Modifier
            .height(280.dp)
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(4.dp),
        shape = MaterialTheme.shapes.medium,
        border = BorderStroke(0.2.dp, MaterialTheme.colorScheme.surface)
    ) {

        Column {
            AsyncImage(
                modifier = Modifier
                    .height(150.dp)
                    .fillMaxWidth(),
                model = ImageRequest.Builder(LocalContext.current)
                    .scale(Scale.FILL).data(item.urlToImage).crossfade(true).build(),
                contentDescription = item.title,
                contentScale = ContentScale.Crop
            )

            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = item.title,
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = item.author ?: "",
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = item.source.name.ifEmpty { "" },
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold
                    )
                )
            }
        }
    }
}
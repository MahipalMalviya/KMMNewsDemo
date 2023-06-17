package com.cerence.kmmnewssample.android.ui.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
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
import com.cerence.kmmnewssample.domain.domain_model.NewsDataModel
import com.cerence.kmmnewssample.presentation.NewsEvent
import com.cerence.kmmnewssample.presentation.NewsState
import com.cerence.kmmnewssample.presentation.NewsViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.koin.androidx.compose.getViewModel

@RootNavGraph(start = true)
@Destination
@Composable
fun Home(
    destinationsNavigator: DestinationsNavigator,
    viewModel: NewsViewModel = getViewModel()
) {
    NavigationDrawer(destinationsNavigator, viewModel)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun NavigationDrawer(
    destinationsNavigator: DestinationsNavigator,
    viewModel: NewsViewModel = getViewModel()
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = true,
        drawerContent = {
            ModalDrawerSheet {
                Text("Drawer title", modifier = Modifier.padding(16.dp))
                Divider()
                NavigationDrawerItem(
                    label = { Text(text = "News") },
                    selected = false,
                    onClick = { }
                )
            }
        },
    ) {
        //screen content
        LaunchedEffect(key1 = Unit, block = {
            viewModel.newsIntent(NewsEvent.GetHeadlines)
        })

        val state by viewModel.state.collectAsState()
        HomePage(state, destinationsNavigator = destinationsNavigator, drawerState, scope)
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
private fun HomePage(
    state: NewsState,
    destinationsNavigator: DestinationsNavigator,
    drawerState: DrawerState,
    scope: CoroutineScope
) {
    Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
        HomeAppBar(
            titleRes = R.string.app_heading,
            actionIcons = { },
            navigationIcon = {
                IconButton(onClick = {
                    scope.launch { if (drawerState.isClosed) drawerState.open() else drawerState.close() }
                }) {
                    Icon(imageVector = Icons.Default.Menu,contentDescription = null)
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
                    item {
                        Text(text = state.errorMsg, style = MaterialTheme.typography.headlineMedium)
                    }
                }

                is NewsState.Idle -> {}
                is NewsState.Loading -> {
                    placeHolder()
                }

                is NewsState.Success -> {
                    headlines(state.list, destinationsNavigator)
                }
            }
        }
    }
}

private fun LazyListScope.placeHolder() {
    item {
        CircularProgressIndicator()
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

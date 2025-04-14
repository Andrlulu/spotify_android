package com.laioffer.spotify.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.laioffer.spotify.R
import com.laioffer.spotify.datamodel.Album
import com.laioffer.spotify.datamodel.Section


@Composable
fun HomeScreen(viewModel: HomeViewModel) { // from HomeViewModel
    // Compose does't require to use MutableState<T>, to hold state; it supports other
    // observable types. Must convert another observable type to a State<T> type.
    // collectAsState() update the homeScreenContent to show different UI based on the HomeUiState
    val uiState by viewModel.uiState.collectAsState()

    //passing in uiState to HomeScreenContent
    HomeScreenContent(uiState = uiState)
}

@Composable
fun HomeScreenContent(uiState: HomeUiState) {
    LazyColumn(modifier = Modifier.padding(16.dp)) {
        item {
            HomeScreenHeader()
        }
        //handle different uiState in the HomeScreenContent
        when {
            uiState.isLoading -> { //if isLoading is true, during the loading
                item {
                    LoadingSection(stringResource(id = R.string.screen_loading))
                }

            }
            else -> { // showing album sections
                items(uiState.feed) { item ->
                    AlbumSection(section = item)
                }
            }
        }
    }
}
@Composable
private fun AlbumSection(section: Section) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Text(
            text = section.sectionTitle,
            style = MaterialTheme.typography.h5.copy(fontWeight = FontWeight.Bold),
            color = Color.White
        )
        LazyRow(
            modifier = Modifier.padding(top = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(section.albums) { item ->
                AlbumCover(item)
            }
        }

    }
}

@Composable
private fun AlbumCover(album: Album) {
    Column {
        Box(modifier = Modifier.size(160.dp)) {
            AsyncImage(
                model = album.cover,
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.FillBounds
            )
            Text(
                text = album.name,
                color = Color.White,
                modifier = Modifier
                    .padding(bottom = 4.dp, start = 2.dp)
                    .align(Alignment.BottomStart),
            )
        }

        Text(
            text = album.artists,
            modifier = Modifier.padding(top = 4.dp),
            style = MaterialTheme.typography.body2.copy(fontWeight = FontWeight.Bold),
            color = Color.LightGray,
        )
    }
}


@Composable
private fun LoadingSection(text: String) {
    Row(modifier = Modifier.padding(vertical = 8.dp)) {
        Text(
            text = text,
            style = MaterialTheme.typography.body2,
            color = Color.White
        )
    }
}


@Composable
fun HomeScreenHeader() {
    Column {
        Text(
            //R.string.menu_home is resource value string menu_home
            stringResource(id = R.string.menu_home),
            style = MaterialTheme.typography.h4,
            color = Color.White
        )
        Spacer(modifier = Modifier.height(16.dp))
    }
}
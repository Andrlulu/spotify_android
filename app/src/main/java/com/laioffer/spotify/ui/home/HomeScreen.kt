package com.laioffer.spotify.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreen(homeViewModel: HomeViewModel) {
//    Column {// wrap content
//        Column(
//            modifier = Modifier
//                .background(Color.Gray)
//        ){
//            Box(Modifier.height(20.dp).width(30.dp).background(Color.Green))
//            Box(Modifier.height(20.dp).width(40.dp).background(Color.White))
//            Box(Modifier.height(20.dp).width(50.dp).background(Color.Red))
//        }
//    }
    LazyRow {// if the item was not included in the screen, no need to create.
        //example: youtube video scrolling
        item {
            Box(Modifier.height(20.dp).width(300.dp).background(Color.Green))
        }
        item {
            Box(Modifier.height(20.dp).width(400.dp).background(Color.White))
        }
        item {
            Box(Modifier.height(20.dp).width(500.dp).background(Color.Red))
        }
    }

//    Row {// wrap content
//        Box(Modifier.height(20.dp).width(30.dp).background(Color.Green))
//        Box(Modifier.height(20.dp).width(40.dp).background(Color.White))
//    }

    Box {

    }
}
package com.laioffer.spotify

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.darkColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import coil.compose.AsyncImage
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.laioffer.spotify.database.DatabaseDao
import com.laioffer.spotify.datamodel.Album
import com.laioffer.spotify.datamodel.Section
import com.laioffer.spotify.network.NetworkApi
import com.laioffer.spotify.network.NetworkModule
import com.laioffer.spotify.player.PlayerBar
import com.laioffer.spotify.player.PlayerViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import javax.inject.Inject

// customized extend AppCompatActivity
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    // creating tag for debuging purpose, using log
    private val TAG = "lifecycle"

    // field injection:
    @Inject
    lateinit var api: NetworkApi // field injection
    @Inject
    lateinit var databaseDao: DatabaseDao // field injection

    private val playerViewModel: PlayerViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "We are at onCreate()")

        setContentView(R.layout.activity_main)
        // set up bottom navigation view
        val navView = findViewById<BottomNavigationView>(R.id.nav_view)

        // navHost, navController
        // using navController to change the fragment in navHost
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment

        val navController = navHostFragment.navController

        // set the navi graph for navController
        navController.setGraph(R.navigation.nav_graph)

        NavigationUI.setupWithNavController(navView, navController)

        // https://stackoverflow.com/questions/70703505/navigationui-not-working-correctly-with-bottom-navigation-view-implementation
        navView.setOnItemSelectedListener {
            NavigationUI.onNavDestinationSelected(it, navController)
            navController.popBackStack(it.itemId, inclusive = false)
            true
        }

        val playerBar = findViewById<ComposeView>(R.id.player_bar)
        playerBar.apply {
            setContent {
                MaterialTheme(colors = darkColors()) {
                    PlayerBar(viewModel = playerViewModel)
                }
            }
        }
        // test database with dummy album
//        GlobalScope.launch {
//            withContext(Dispatchers.IO) {
//                val album = Album(
//                    id = 1,
//                    name =  "Hexagonal",
//                    year = "2008",
//                    cover = "https://upload.wikimedia.org/wikipedia/en/6/6d/Leessang-Hexagonal_%28cover%29.jpg",
//                    artists = "Lesssang",
//                    description = "Leessang (Korean: 리쌍) was a South Korean hip hop duo, composed of Kang Hee-gun (Gary or Garie) and Gil Seong-joon (Gil)"
//                )
//                databaseDao.favoriteAlbum(album)
//            }
//        }

        // Test Retrofit
        // After dependcy injection, no longer need to create retrofit

//        val retrofit = NetworkModule.provideRetrofit()
//        //interface networkApi
//        val api: NetworkApi = retrofit.create(NetworkApi::class.java)
//        // return data type defined in the netowrkApi interface
//        val responseCall: Call<List<Section>> = api.getHomeFeed()

        // slow task, use coroutine to multi thread handling
        GlobalScope.launch(Dispatchers.IO) {
//            val response = responseCall.execute()
//            val sections = response.body()
            // can directly use api from the lateinit/ field injection
            // inject NetworkApi into MainActivity api field
            //    @Inject
            //    lateinit var api: NetworkApi
            val response = api.getHomeFeed().execute().body()
            Log.d("Network", response.toString())
        }

    }
}

@Composable
fun AlbumCover() {
    Column {
        Box(modifier = Modifier.size(160.dp)) {
            // https://upload.wikimedia.org/wikipedia/en/d/d1/Stillfantasy.jpg
//            Image(painter = , contentDescription = )
            AsyncImage(
                model = "https://upload.wikimedia.org/wikipedia/en/d/d1/Stillfantasy.jpg",
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.FillBounds
            )
            Text(
                text = "Still Fantasy",
                modifier = Modifier
                    .padding(start = 2.dp, bottom = 4.dp) // left is start, right is end
                    .align(Alignment.BottomStart),
                style = MaterialTheme.typography.body2.copy(fontWeight = FontWeight.Bold),
                color = Color.White
            )
        }

        Text(
            text = "Jay Chou",
            modifier = Modifier.padding(top = 4.dp, start = 2.dp),
            style = MaterialTheme.typography.body2.copy(fontWeight = FontWeight.Bold),
            color = Color.LightGray
        )
    }
}
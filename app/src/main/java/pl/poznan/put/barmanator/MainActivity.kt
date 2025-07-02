package pl.poznan.put.barmanator

import android.app.Activity
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pl.poznan.put.barmanator.ui.theme.BarmanatorTheme

import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.zIndex
import androidx.core.view.WindowCompat
import pl.poznan.put.barmanator.data.Database
import pl.poznan.put.barmanator.data.Drink
import pl.poznan.put.barmanator.screens.HomeScreen
import androidx.navigation.compose.*
import coil.compose.AsyncImage
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.plcoding.composegooglesignincleanarchitecture.presentation.sign_in.GoogleAuthUiClient
import com.plcoding.composegooglesignincleanarchitecture.presentation.sign_in.SignInResult
import com.plcoding.composegooglesignincleanarchitecture.presentation.sign_in.UserData
import io.ktor.util.Identity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import pl.poznan.put.barmanator.screens.DrinkListScreen
import pl.poznan.put.barmanator.utils.LiquidBg
import pl.poznan.put.barmanator.utils.LocalDatabase
import com.google.android.gms.auth.api.identity.Identity as id





class MainActivity : ComponentActivity() {

    private lateinit var database: Database
    private lateinit var googleAuthUiClient: GoogleAuthUiClient

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        googleAuthUiClient = GoogleAuthUiClient(
            context = this,
            oneTapClient = id.getSignInClient(this)
        )


        val isTablet = resources.configuration.smallestScreenWidthDp >= 600
        if (!isTablet) {
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }

        database = Database(this)



        setContent {


            CompositionLocalProvider(LocalDatabase provides database) {

                BarmanatorTheme {
                    MainScreen(database.getDrinks(),googleAuthUiClient)
                }
            }
        }
    }
}



data class TabItem(
    val title: String,
    val iconRes: Int
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(drinks: List<Drink>,googleAuthUiClient: GoogleAuthUiClient ) {
    val tabs = listOf(
        TabItem("Home", R.drawable.home),
        TabItem("Favourite",R.drawable.full_star),
        TabItem("All", R.drawable.all),
        TabItem("Ordinary", R.drawable.drink),
        TabItem("Shots", R.drawable.shot),
        TabItem("Cocktails", R.drawable.shaker),
        TabItem("Punches", R.drawable.punch),
        TabItem("Others", R.drawable.others)
    )
    val pagerState = rememberPagerState(
        initialPage = 0,
        pageCount = { tabs.size }
    )
    val coroutineScope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)



    val context = LocalContext.current

    val firestore = Firebase.firestore





    Box(modifier = Modifier.fillMaxSize()) {

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .zIndex(0f)
        )

        LiquidBg(
            waveColor = MaterialTheme.colorScheme.secondaryContainer,
            modifier = Modifier
                .fillMaxSize()
        )

        ModalNavigationDrawer(
            drawerState = drawerState,
            drawerContent = {
                ModalDrawerSheet {
                    Spacer(Modifier.height(16.dp))
                    tabs.forEachIndexed { index, tab ->
                        val selected = pagerState.currentPage == index
                        NavigationDrawerItem(
                            label = { Text(
                                tab.title,
                                color = if (selected)
                                    MaterialTheme.colorScheme.onPrimary
                                else
                                    MaterialTheme.colorScheme.primary
                            ) },
                            selected = selected,
                            onClick = {
                                coroutineScope.launch {
                                    pagerState.animateScrollToPage(index)
                                    drawerState.close()
                                }
                            },
                            icon = {
                                Icon(
                                    modifier = Modifier.size(48.dp),
                                    painter = painterResource(id = tab.iconRes),
                                    contentDescription = tab.title,
                                    tint = if (selected)
                                        MaterialTheme.colorScheme.onPrimary
                                    else
                                        MaterialTheme.colorScheme.primary
                                )
                            }
                        )
                    }
                }
            }
        ) {

            var userData by remember { mutableStateOf<UserData?>(null) }
            var errorMessage by remember { mutableStateOf<String?>(null) }


            val dat = LocalDatabase.current

            if (userData == null) dat.favourites
            else
                firestore.collection("users").document(userData!!.userId).get().addOnSuccessListener {
                    document -> val favs = document.get("favoriteDrinks") as? List<String> ?: emptyList()
                    dat.favourites = favs.toMutableStateList()

                    GlobalScope.launch {
                        for( fav in dat.favourites)
                        {


                            println(fav)
                            try {
                                dat.SearchDrink(fav)
                            } catch (e: Exception) {
                                println("Exception searching for drink ")
                            }

                        }

                    }


                }




            Scaffold(
                containerColor = Color.Transparent,
                modifier = Modifier.windowInsetsPadding(WindowInsets.safeDrawing),
                topBar = {
                    CenterAlignedTopAppBar(
                        title = { Text(tabs[pagerState.currentPage].title) },
                        navigationIcon = {
                            IconButton(onClick = {
                                coroutineScope.launch { drawerState.open() }
                            }) {
                                Icon(
                                    Icons.Filled.Menu,
                                    contentDescription = "Menu",
                                    tint = MaterialTheme.colorScheme.onPrimaryContainer
                                )
                            }
                        },


                        actions = {

                            val context = LocalContext.current
                            val coroutineScope = rememberCoroutineScope()



                            val launcher = rememberLauncherForActivityResult(
                                contract = ActivityResultContracts.StartIntentSenderForResult()
                            ) { result ->
                                if (result.resultCode == Activity.RESULT_OK) {
                                    val intent = result.data ?: return@rememberLauncherForActivityResult
                                    coroutineScope.launch {
                                        val signInResult = googleAuthUiClient.signInWithIntent(intent)

                                        if (signInResult.data != null) {
                                            userData = signInResult.data
                                            errorMessage = null
                                        } else {
                                            errorMessage = signInResult.errorMessage
                                            userData = null
                                        }
                                    }
                                } else {
                                    errorMessage = "Logowanie anulowane"
                                }
                            }

                            IconButton(
                                onClick = {
                                    coroutineScope.launch {

                                        if(userData == null){
                                        val intentSender = googleAuthUiClient.signIn()
                                        if (intentSender != null) {
                                            launcher.launch(IntentSenderRequest.Builder(intentSender).build())
                                        } else {
                                            errorMessage = "Nie można rozpocząć logowania"
                                        }}else{

                                            userData == null
                                            googleAuthUiClient.signOut()
                                            Firebase.auth.signOut()

                                        }
                                    }
                                }
                            ) {
                                if(userData == null){
                                Icon(
                                    painter = painterResource(id = R.drawable.login_icon),
                                    contentDescription = "Zaloguj się przez Google",
                                    tint = MaterialTheme.colorScheme.onPrimaryContainer
                                )}else{
                                    AsyncImage(
                                        model = userData!!.profilePictureUrl,
                                        placeholder = painterResource(R.drawable.hourglass),
                                        contentDescription = null,
                                        modifier = Modifier.size(192.dp,192.dp),
                                        contentScale = ContentScale.Crop
                                    )
                                }

                            }


                        }
                    )
                },
                bottomBar = {
                    ScrollableTabRow(
                        edgePadding = 0.dp,
                        selectedTabIndex = pagerState.currentPage
                    ) {
                        tabs.forEachIndexed { index, tab ->
                            Tab(
                                selected = pagerState.currentPage == index,
                                onClick = {
                                    coroutineScope.launch {
                                        pagerState.animateScrollToPage(index)
                                    }
                                },
                                text = { Text(tab.title) },
                                icon = {
                                    Icon(
                                        modifier = Modifier.size(48.dp),
                                        painter = painterResource(id = tab.iconRes),
                                        contentDescription = tab.title,
                                        tint = MaterialTheme.colorScheme.primary
                                    )
                                }
                            )
                        }
                    }
                }
            ) { paddingValues ->
                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                ) { page ->
                    when (tabs[page].title) {
                        "Home" -> HomeScreen(Modifier)
                        "All" -> DrinkListScreen(drinks, Modifier)
                        "Ordinary" -> DrinkListScreen(
                            drinks,
                            Modifier,
                            filter = { it.category.contains("Ordinary") })
                        "Favourite"-> {

                            if (userData == null)
                                Text(
                                    "Log in to see your favourite drinks",
                                    modifier = Modifier
                                        .size(265.dp, 256.dp)
                                        .align(Alignment.Center)
                                        .fillMaxWidth(),
                                    color = Color.White
                                )
                            else

                                DrinkListScreen(
                                    drinks,
                                    Modifier,
                                    filter = { dat.favourites.contains(it.name) }

                                )
                        }



                        "Shots" -> DrinkListScreen(
                            drinks,
                            Modifier,
                            filter = { it.category.contains("Shot") })

                        "Cocktails" -> DrinkListScreen(
                            drinks,
                            Modifier,
                            filter = { it.category.contains("Cocktail") })

                        "Punches" -> DrinkListScreen(
                            drinks,
                            Modifier,
                            filter = { it.category.contains("Punch") })

                        "Others" -> DrinkListScreen(
                            drinks,
                            Modifier,
                            filter = { it.category.contains("Other") })
                    }
                }
            }
        }
    }
}

@Composable
fun GoogleSignInButton(
    googleAuthUiClient: GoogleAuthUiClient,
    onSignInResult: (SignInResult) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    // Launcher do odbioru wyniku One Tap
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartIntentSenderForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val intent = result.data ?: return@rememberLauncherForActivityResult
            // Wywołaj suspending fun w coroutine
            CoroutineScope(Dispatchers.Main).launch {
                val signInResult = googleAuthUiClient.signInWithIntent(intent)
                onSignInResult(signInResult)
            }
        } else {
            // Logowanie anulowane lub błąd
            onSignInResult(SignInResult(null, "Logowanie anulowane"))
        }
    }

    Button(
        onClick = {
            CoroutineScope(Dispatchers.Main).launch {
                val intentSender = googleAuthUiClient.signIn()
                if (intentSender != null) {
                    launcher.launch(IntentSenderRequest.Builder(intentSender).build())
                } else {
                    onSignInResult(SignInResult(null, "Nie można rozpocząć logowania"))
                }
            }
        },
        modifier = modifier
    ) {
        Icon(
            painter = painterResource(id = R.drawable.login_icon),
            contentDescription = "Google Sign-In",
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = "Zaloguj się przez Google")
    }
}

private fun Unit.launch(function: CoroutineScope.() -> Unit) {}





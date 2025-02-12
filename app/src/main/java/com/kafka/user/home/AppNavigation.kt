package com.kafka.user.home

//noinspection UsingMaterialAndMaterial3Libraries
import android.app.Activity
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.AnimatedContentTransitionScope.SlideDirection.Companion.End
import androidx.compose.animation.AnimatedContentTransitionScope.SlideDirection.Companion.Start
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.navigation.bottomSheet
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.window.DialogProperties
import androidx.core.view.WindowCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.dialog
import androidx.navigation.compose.navigation
import androidx.navigation.navDeepLink
import androidx.navigation.toRoute
import com.kafka.base.debug
import com.kafka.homepage.Homepage
import com.kafka.homepage.recent.RecentItemsScreen
import com.kafka.homepage.recent.RecentViewModel
import com.kafka.item.detail.ItemDetail
import com.kafka.item.detail.ItemDetailViewModel
import com.kafka.item.detail.description.DescriptionDialog
import com.kafka.item.files.Files
import com.kafka.item.files.FilesViewModel
import com.kafka.library.LibraryScreen
import com.kafka.library.downloads.DownloadsViewModel
import com.kafka.library.favorites.FavoriteViewModel
import com.kafka.navigation.LocalNavigator
import com.kafka.navigation.NavigationEvent
import com.kafka.navigation.Navigator
import com.kafka.navigation.deeplink.Config
import com.kafka.navigation.graph.RootScreen
import com.kafka.navigation.graph.Screen
import com.kafka.reader.ReaderScreen
import com.kafka.reader.ReaderViewModel
import com.kafka.reader.online.OnlineReader
import com.kafka.reader.online.OnlineReaderViewModel
import com.kafka.reader.pdf.PdfReaderViewModel
import com.kafka.search.SearchScreen
import com.kafka.search.SearchViewModel
import com.kafka.summary.SummaryScreen
import com.kafka.summary.SummaryViewModel
import com.kafka.user.playback.PlaybackViewModel
import com.kafka.webview.WebView
import com.rekhta.ui.auth.AuthViewModel
import com.rekhta.ui.auth.LoginScreen
import com.rekhta.ui.profile.ProfileScreen
import com.rekhta.ui.profile.ProfileViewModel
import com.rekhta.ui.profile.feedback.FeedbackScreen
import com.rekhta.ui.profile.feedback.FeedbackViewModel
import com.sarahang.playback.ui.playback.speed.PlaybackSpeedViewModel
import com.sarahang.playback.ui.playback.timer.SleepTimerViewModel
import com.sarahang.playback.ui.sheet.PlaybackSheet
import com.sarahang.playback.ui.sheet.ResizablePlaybackSheetLayoutViewModel
import kotlinx.coroutines.flow.Flow
import me.tatarka.inject.annotations.Assisted
import me.tatarka.inject.annotations.Inject
import ui.common.theme.theme.LocalTheme
import ui.common.theme.theme.shouldUseDarkColors

typealias AppNavigation = @Composable (NavHostController) -> Unit

@Composable
@Inject
internal fun AppNavigation(
    @Assisted navController: NavHostController,
    modifier: Modifier = Modifier,
    navigator: Navigator = LocalNavigator.current,
    addHome: addHome,
    addSearch: addSearch,
    addItemDetailGroup: addItemDetailGroup,
    addLibrary: addLibrary,
    addProfile: addProfile,
    addFeedback: addFeedback,
    addLogin: addLogin,
    addPlayer: addPlayer,
    addWebView: addWebView,
    addRecentItems: addRecentItems,
) {
    CollectEvent(navigator.queue) { event ->
        when (event) {
            is NavigationEvent.Destination -> {
                when (val screen = event.route) {
                    is Screen.ItemDetail -> {
                        navController.navigate(Screen.ItemDetail.route(screen.itemId))
                    }

                    is Screen.ItemDescription -> {
                        navController.navigate(Screen.ItemDescription.route(screen.itemId))
                    }

                    Screen.Feedback -> {
                        navController.navigate(Screen.Feedback.route)
                    }

                    Screen.Player -> {
                        navController.navigate(Screen.Player.route)
                    }

                    else -> {
                        navController.navigate(screen)
                    }
                }
            }

            is NavigationEvent.Back -> {
                debug { "Back pressed" }
                navController.navigateUp()
            }

            else -> Unit
        }
    }

    SwitchStatusBarsOnPlayer(navController = navController)

    NavHost(
        modifier = modifier.fillMaxSize(),
        navController = navController,
        startDestination = RootScreen.Home,
        enterTransition = { enter() },
        exitTransition = { fadeOut() },
        popEnterTransition = { fadeIn() },
        popExitTransition = { exit() }
    ) {
        navigation<RootScreen.Home>(startDestination = Screen.Home) {
            addHome()
            addItemDetailGroup(navController)
            addLibrary()
            addProfile()
            addFeedback()
            addSearch()
            addLogin()
            addPlayer()
            addWebView()
            addRecentItems()
        }

        navigation<RootScreen.Search>(startDestination = Screen.Search()) {
            addSearch()
            addItemDetailGroup(navController)
            addPlayer()
            addWebView()
        }

        navigation<RootScreen.Library>(startDestination = Screen.Library) {
            addLibrary()
            addItemDetailGroup(navController)
            addSearch()
            addPlayer()
            addWebView()
            addLogin()
            addProfile()
        }
    }
}

typealias addItemDetailGroup = NavGraphBuilder.(NavController) -> Unit

@Inject
internal fun NavGraphBuilder.addItemDetailGroup(
    @Assisted navController: NavController,
    addItemDetail: addItemDetail,
    addItemDescription: addItemDescription,
    addFiles: addFiles,
    addReader: addReader,
    addOnlineReader: addOnlineReader,
    addSummary: addSummary,
) {
    addItemDetail()
    addItemDescription()
    addFiles()
    addReader()
    addOnlineReader(navController)
    addSummary()
}

typealias addHome = NavGraphBuilder.() -> Unit

@Inject
internal fun NavGraphBuilder.addHome(homepage: Homepage) {
    composable<Screen.Home> {
        homepage()
    }
}

typealias addSearch = NavGraphBuilder.() -> Unit

@Inject
internal fun NavGraphBuilder.addSearch(viewModelFactory: (SavedStateHandle) -> SearchViewModel) {
    composable<Screen.Search>(
        deepLinks = listOf(
            navDeepLink<Screen.Search>(basePath = "${Config.BASE_URL}search"),
            navDeepLink<Screen.Search>(basePath = "${Config.BASE_URL_ALT}search")
        )
    ) {
        val searchViewModel = viewModel { viewModelFactory(createSavedStateHandle()) }
        SearchScreen(searchViewModel)
    }
}

typealias addPlayer = NavGraphBuilder.() -> Unit

@Inject
internal fun NavGraphBuilder.addPlayer(
    viewModelFactory: () -> PlaybackViewModel,
    resizableViewModelFactory: () -> ResizablePlaybackSheetLayoutViewModel,
    sleepTimerViewModelFactory: () -> SleepTimerViewModel,
    playbackSpeedViewModelFactory: () -> PlaybackSpeedViewModel
) {
    bottomSheet(Screen.Player.route) {
        val navigator = LocalNavigator.current
        val viewModel = viewModel { viewModelFactory() }

        PlaybackSheet(
            onClose = { navigator.goBack() },
            goToItem = { viewModel.goToAlbum() },
            goToCreator = { viewModel.goToCreator() },
            playerTheme = viewModel.playerTheme,
            useDarkTheme = LocalTheme.current.shouldUseDarkColors(),
            resizableViewModelFactory = resizableViewModelFactory,
            sleepTimerViewModelFactory = sleepTimerViewModelFactory,
            playbackSpeedViewModelFactory = playbackSpeedViewModelFactory,
        )
    }
}

typealias addLibrary = NavGraphBuilder.() -> Unit

@Inject
internal fun NavGraphBuilder.addLibrary(
    favoriteViewmodelFactory: () -> FavoriteViewModel,
    downloadsViewmodelFactory: () -> DownloadsViewModel,
) {
    composable<Screen.Library> {
        val favoriteViewModel = viewModel { favoriteViewmodelFactory() }
        val downloadsViewModel = viewModel { downloadsViewmodelFactory() }
        LibraryScreen(
            favoriteViewModel = favoriteViewModel,
            downloadsViewModel = downloadsViewModel
        )
    }
}

typealias addItemDetail = NavGraphBuilder.() -> Unit

@Inject
internal fun NavGraphBuilder.addItemDetail(
    viewModelFactory: (SavedStateHandle) -> ItemDetailViewModel,
) {
    composable(
        route = Screen.ItemDetail.route,
        deepLinks = listOf(
            navDeepLink<Screen.ItemDetail>("${Config.BASE_URL}item"),
            navDeepLink<Screen.ItemDetail>("${Config.BASE_URL_ALT}item")
        )
    ) {
        val viewModel = viewModel { viewModelFactory(createSavedStateHandle()) }
        ItemDetail(viewModel)
    }
}

typealias addItemDescription = NavGraphBuilder.() -> Unit

@Inject
internal fun NavGraphBuilder.addItemDescription(
    viewModelFactory: (SavedStateHandle) -> ItemDetailViewModel,
) {
    bottomSheet(Screen.ItemDescription.route) {
        val viewModel = viewModel { viewModelFactory(createSavedStateHandle()) }
        DescriptionDialog(viewModel)
    }
}

typealias addFiles = NavGraphBuilder.() -> Unit

@Inject
internal fun NavGraphBuilder.addFiles(viewModelFactory: (SavedStateHandle) -> FilesViewModel) {
    composable<Screen.Files> {
        val viewModel = viewModel { viewModelFactory(createSavedStateHandle()) }
        Files(viewModel)
    }
}

typealias addReader = NavGraphBuilder.() -> Unit

@Inject
internal fun NavGraphBuilder.addReader(
    readerViewModelFactory: (SavedStateHandle) -> ReaderViewModel,
    pdfReaderViewModelFactory: () -> PdfReaderViewModel,
) {
    composable<Screen.Reader> {
        val viewModel = viewModel { readerViewModelFactory(createSavedStateHandle()) }
        val pdfReaderViewModel = viewModel { pdfReaderViewModelFactory() }
        ReaderScreen(viewModel, pdfReaderViewModel)
    }
}

typealias addLogin = NavGraphBuilder.() -> Unit

@Inject
internal fun NavGraphBuilder.addLogin(viewModelFactory: () -> AuthViewModel) {
    composable<Screen.Login> {
        val viewModel = viewModel { viewModelFactory() }
        LoginScreen(viewModel)
    }
}

typealias addProfile = NavGraphBuilder.() -> Unit

@Inject
internal fun NavGraphBuilder.addProfile(viewModelFactory: () -> ProfileViewModel) {
    dialog<Screen.Profile>(dialogProperties = DialogProperties(usePlatformDefaultWidth = false)) {
        val viewModel = viewModel { viewModelFactory() }
        ProfileScreen(viewModel)
    }
}

typealias addFeedback = NavGraphBuilder.() -> Unit

@Inject
internal fun NavGraphBuilder.addFeedback(viewModelFactory: () -> FeedbackViewModel) {
    bottomSheet(route = Screen.Feedback.route) {
        val viewModel = viewModel { viewModelFactory() }
        FeedbackScreen(viewModel)
    }
}

typealias addRecentItems = NavGraphBuilder.() -> Unit

@Inject
internal fun NavGraphBuilder.addRecentItems(viewModelFactory: () -> RecentViewModel) {
    composable<Screen.RecentItems> {
        val viewModel = viewModel { viewModelFactory() }
        RecentItemsScreen(viewModel)
    }
}

typealias addSummary = NavGraphBuilder.() -> Unit

@Inject
internal fun NavGraphBuilder.addSummary(viewModelFactory: (SavedStateHandle) -> SummaryViewModel) {
    composable<Screen.Summary> {
        val viewModel = viewModel { viewModelFactory(createSavedStateHandle()) }
        SummaryScreen(viewModel)
    }
}

typealias addWebView = NavGraphBuilder.() -> Unit

@Inject
internal fun NavGraphBuilder.addWebView() {
    composable<Screen.Web> { backStackEntry ->
        val navigator = LocalNavigator.current
        WebView(backStackEntry.toRoute<Screen.Web>().url, navigator::goBack)
    }
}

typealias addOnlineReader = NavGraphBuilder.(NavController) -> Unit

@Inject
internal fun NavGraphBuilder.addOnlineReader(
    @Assisted navController: NavController,
    viewModelFactory: (SavedStateHandle) -> OnlineReaderViewModel,
) {
    composable<Screen.OnlineReader> {
        val currentDestination = navController.currentDestination?.route
        val viewModel = viewModel { viewModelFactory(createSavedStateHandle()) }

        OnlineReader(viewModel) { fileId ->
            navController.navigate(Screen.Reader(fileId)) {
                popUpTo(currentDestination.orEmpty()) { inclusive = true }
            }
        }
    }
}

fun AnimatedContentTransitionScope<NavBackStackEntry>.enter(): EnterTransition {
    val initialNavGraph = initialState.destination.parent?.route
    val targetNavGraph = targetState.destination.parent?.route

    if (initialNavGraph != targetNavGraph) {
        return fadeIn()
    }

    return slideIntoContainer(Start) { (it / 1.5).toInt() } + fadeIn()
}

fun AnimatedContentTransitionScope<NavBackStackEntry>.exit(): ExitTransition {
    val initialNavGraph = initialState.destination.parent?.route
    val targetNavGraph = targetState.destination.parent?.route

    if (initialNavGraph != targetNavGraph) {
        return fadeOut()
    }

    return slideOutOfContainer(End) { (it / 1.5).toInt() } + fadeOut()
}

@Composable
internal fun SwitchStatusBarsOnPlayer(navController: NavHostController) {
    val currentRoute by navController.currentBackStackEntryAsState()
    val destination = currentRoute?.destination?.route?.substringBefore("/")
    val isPlayerUp = destination == "player"

    val view = LocalView.current
    val useDarkTheme = LocalTheme.current.shouldUseDarkColors()

    DisposableEffect(isPlayerUp, useDarkTheme) {
        val activity = view.context as Activity
        WindowCompat.getInsetsController(activity.window, activity.window.decorView).apply {
            isAppearanceLightStatusBars = if (isPlayerUp) useDarkTheme else !useDarkTheme
        }

        onDispose {

        }
    }
}

@Composable
fun <T> CollectEvent(
    flow: Flow<T>,
    lifecycle: Lifecycle = LocalLifecycleOwner.current.lifecycle,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    collector: (T) -> Unit,
): Unit = LaunchedEffect(lifecycle, flow) {
    lifecycle.repeatOnLifecycle(minActiveState) {
        flow.collect {
            collector(it)
        }
    }
}
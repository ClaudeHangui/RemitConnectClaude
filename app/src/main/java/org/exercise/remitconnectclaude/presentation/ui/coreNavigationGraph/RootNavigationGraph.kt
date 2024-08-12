
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import org.exercise.remitconnectclaude.presentation.ui.homeNavigation.HomeScreen

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun RootNavigationGraph(
    navHostController: NavHostController) {
    NavHost(
        navController = navHostController,
        route = Graph.ROOT,
        startDestination = Graph.HOME
    ) {
        composable(Graph.HOME) {
            val navBarNavController = rememberAnimatedNavController()
            HomeScreen(navController = navBarNavController)
        }
    }
}

object Graph {
    const val ROOT = "root_graph"
    const val HOME = "main_graph"
}
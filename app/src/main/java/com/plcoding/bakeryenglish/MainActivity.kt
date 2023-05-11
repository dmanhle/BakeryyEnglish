package com.plcoding.bakeryenglish

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.plcoding.bakeryenglish.core.RouteScreen
import com.plcoding.bakeryenglish.presentation.screen.*
import com.plcoding.bakeryenglish.presentation.screen.components.FlashCardPage
import com.plcoding.bakeryenglish.presentation.screen.components.dialog.SuccesfullyLearing
import com.plcoding.bakeryenglish.presentation.viewmodel.DictonaryViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity () : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        Log.d("AAA","Nội động từ:${dtDefinitions}")
//        Log.d("AAA","Ngoại động từ:${ndtDefinitions}")
       // window.setSoftInputMode(WindowManager.LayoutParams.)

        val list = listOf(RouteScreen.Home,RouteScreen.Search,RouteScreen.CreateLesson,RouteScreen.User)
        setContent {
            val navController = rememberNavController();
            val dictonaryViewModel:DictonaryViewModel = hiltViewModel();

            // catch event when user open your keyboard. Scroll layout to maxvalue
            // khi người dùng mở bàn phím thì layout sẽ được đẩy xuống dưới cùng\;



//            dictonaryViewModel.getWord("hello");
            dictonaryViewModel.getWordApi("sex")
            Scaffold(
                bottomBar = {
                    BottomNavigation(
                        modifier = Modifier.heightIn(50.dp),
                        backgroundColor = Color.White,
                        elevation = 3.dp,
                    ) {
                        val navBackStackEntry by navController.currentBackStackEntryAsState()
                        val currentDestination = navBackStackEntry?.destination
                        list.forEach { item->
                          BottomNavigationItem(
                              selected = currentDestination?.hierarchy?.any { it.route == item.route } == true,
                              onClick = {
                                  navController.navigate(item.route) {
                                      // Pop up to the start destination of the graph to
                                      // avoid building up a large stack of destinations
                                      // on the back stack as users select items
                                      popUpTo(navController.graph.findStartDestination().id) {
                                          saveState = true
                                      }
                                      // Avoid multiple copies of the same destination when
                                      // reselecting the same item
                                      launchSingleTop = true
                                      // Restore state when reselecting a previously selected item
                                      restoreState = true
                                  }
                              },
                              label = {""},
                              icon = {
                                  Icon(
                                  imageVector = item.imageVector,
                                  contentDescription = "",
                                  modifier = Modifier
                                      .size(27.dp)
                                      .height(40.dp)
                                  )
                              },
                              modifier = Modifier
                                  .align(Alignment.CenterVertically)
                                  .padding(5.dp)
                          )
                        }
                    }
                }
            ) {
                Column(modifier = Modifier.padding(it)) {
                    NavHost(navController = navController, startDestination = RouteScreen.Home.route)
                    {
                        composable(RouteScreen.Home.route ) {
                            HomeScreen(navController)
                        }
                        composable(RouteScreen.Search.route){
                            SearchPage()
                        }
                        composable(
                           route =  RouteScreen.CreateLesson.route+"?lessonID={lessonID}",
                           arguments = listOf(
                               navArgument("lessonID") {
                                   type = NavType.IntType
                                   defaultValue = -3
                               }
                           )
                        ){
                            CreateLessonPage(navController)
                        }
                        composable(RouteScreen.User.route){
                            MyProfilePage()
                        }
                        composable(RouteScreen.SuccessfullyLearning.route){
                            SuccesfullyLearing(navController)
                        }
                        composable(
                            route = RouteScreen.IntroduceLesson.route +
                                    "?lessonID={lessonID}",
                            arguments = listOf(
                                navArgument(
                                    name = "lessonID"
                                ) {
                                    type = NavType.IntType
                                    defaultValue = -2
                                }
                            )
                        ) {
                            IntroduceLesson(
                                navController = navController,
                            )
                        }
                        composable(
                            route = RouteScreen.LearningLesson.route +
                                    "?lessonID_learning={lessonID_learning}",
                            arguments = listOf(
                                navArgument(
                                    name = "lessonID_learning"
                                ) {
                                    type = NavType.IntType
                                    defaultValue = -1
                                }
                            )
                        ) {
                            LearningLessonPage(navController = navController)
                        }
                        composable(
                            route = RouteScreen.FlashCardPage.route +
                                    "?lessonID={lessonID}&list={list}",
                            arguments = listOf(
                                navArgument(
                                    name = "lessonID"
                                ) {
                                    type = NavType.IntType
                                    defaultValue = -1
                                },navArgument(
                                    name = "list"
                                ) {
                                    type = NavType.StringType
                                    defaultValue = "null"
                                }
                            )
                        ) {
                            FlashCardPage(navController = navController)
                        }
                    }

                }
            }
        }
    }
}

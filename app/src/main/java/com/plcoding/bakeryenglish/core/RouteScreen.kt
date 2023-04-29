package com.plcoding.bakeryenglish.core

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector


sealed class RouteScreen(
    val route:String,val imageVector: ImageVector
){
    object Home: RouteScreen("HomeScreen", Icons.Default.Home)
    object Search: RouteScreen("SearchScreen",Icons.Default.Search)
    object CreateLesson: RouteScreen("CreateLessonScreen",Icons.Default.Add)
    object User: RouteScreen("UserScreen",Icons.Default.Person)
    object IntroduceLesson:RouteScreen("IntroducLesoon",Icons.Default.Check)
    object LearningLesson:RouteScreen("LearingLesson",Icons.Default.Check)
    object SuccessfullyLearning:RouteScreen("SuccessfullyLearning",Icons.Default.Check)
}
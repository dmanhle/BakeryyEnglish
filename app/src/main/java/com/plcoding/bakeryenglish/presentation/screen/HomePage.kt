package com.plcoding.bakeryenglish.presentation.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddAlert
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.plcoding.bakeryenglish.R
import com.plcoding.bakeryenglish.core.RouteScreen
import com.plcoding.bakeryenglish.domain.model.Lesson
import com.plcoding.bakeryenglish.presentation.viewmodel.HomeViewModel

@Composable
fun HomeScreen(
    navController: NavController
) {
    val homeViewModel:HomeViewModel = hiltViewModel()
    val listLesson = homeViewModel.loadLesson.value;
    val painter: Painter = painterResource(id = R.drawable.logo)
    when(listLesson.isEmpty()){
        true ->{
           displayScreenBlank()
        }
        false ->{
            Scaffold(
                modifier = Modifier
                    .fillMaxSize()
                    .fillMaxWidth()
                    .fillMaxHeight(),
                topBar = {
                    TopAppBar(
                        title = { Text(text = "")},
                        navigationIcon = {
                            Image(painter = painter, contentDescription = "" )
                        },
                        actions = {
                            IconButton(onClick = { /*TODO*/ }) {
                                Icon(imageVector = Icons.Default.AddAlert, contentDescription ="")
                            }
                        },
                        elevation = 0.3.dp,
                        backgroundColor = Color.White,
                        modifier = Modifier.padding(start = 10.dp, end = 10.dp)
                    )
                }
            ){
                Column() {
                    Spacer(modifier = Modifier.height(15.dp))
                    Text(text = "Danh sách học phần", fontSize = 18.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(start = 15.dp))
                    Spacer(modifier = Modifier.height(15.dp))

                    LazyColumn(modifier = Modifier.padding(it)){
                        items(listLesson) {
                            CardLesson(lesson = it,navController = navController)
                            Spacer(modifier = Modifier.height(20.dp))
                        }
                    }
                }
            }
        }
    }
}
@Composable
fun displayScreenBlank(){
    Box(
        modifier = Modifier.background(color = Color.Gray.copy(alpha = 0.05f)),
        contentAlignment = Alignment.Center
    ){
       Text(
           text = "Bạn chưa tạo học phần nào",
           fontSize = 30.sp,
           color = Color.Gray.copy(alpha = 0.05f)
       )
    }
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CardLesson(lesson: Lesson,navController: NavController){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(160.dp)
            .padding(start = 15.dp, end = 15.dp),
        shape = RoundedCornerShape(8.dp),
        elevation = 4.dp,
        backgroundColor = Color.White,
        onClick = {
           navController.navigate(
               RouteScreen.IntroduceLesson.route+"?lessonID=${lesson.id}"
           )
        }
    ) {
       Box(
           contentAlignment = Alignment.Center
       ) {
           Column() {
               Text(
                   text = lesson.nameLesson,
                   fontWeight = FontWeight.Bold,
                   color = Color(0xFF008080)
               )
               Text(
                   text = "${lesson.listVocabularyLesson?.size.toString()} từ ngữ",
                   color = Color.Gray
               )
           }
       }
    }
}

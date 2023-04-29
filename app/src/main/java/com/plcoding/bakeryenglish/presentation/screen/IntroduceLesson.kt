package com.plcoding.bakeryenglish.presentation.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Pending
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.plcoding.bakeryenglish.core.RouteScreen
import com.plcoding.bakeryenglish.presentation.viewmodel.IntroduceLessonViewModel

@Composable
fun IntroduceLesson(navController: NavController) {
    val introduceLessonViewModel:IntroduceLessonViewModel = hiltViewModel();
    val lesson = introduceLessonViewModel.lesson.value;
    Scaffold(
     floatingActionButton = {
         
     },
     topBar = {
         TopAppBar(
             title = {
                     
             },
             navigationIcon = {
                  IconButton(onClick = {
                      navController.navigateUp()
                  }) {
                      Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "")
                  }            
             },
             actions = {
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(imageVector = Icons.Default.Settings, contentDescription = "" )
                }
             }, backgroundColor = Color.White,
             elevation = 0.00001.dp
         ) 
     }   
    ) {
        Column(Modifier.padding(it)) {
            Card(
                elevation = 5.dp,
                shape = RoundedCornerShape(5.dp),
                backgroundColor = Color.White,
                modifier = Modifier
                    .padding(20.dp)
                    .fillMaxWidth()
                    .height(200.dp)
            ) {
                Box(contentAlignment = Alignment.Center ){
                    Text(
                        text = lesson.nameLesson,
                        fontSize = 30.sp,
                        color = Color.Gray
                    )
                }
            }
            Spacer(modifier = Modifier.height(30.dp))
            Card(
                elevation = 2.dp,
                shape = RoundedCornerShape(2.dp),
                backgroundColor = Color.White,
                modifier = Modifier.padding(15.dp)
            ) {
                Row(
                    Modifier
                        .clickable {
                            navController.navigate(RouteScreen.LearningLesson.route + "?lessonID_learning=${lesson.id}")
                        }
                        .align(CenterHorizontally)
                ) {
                    Icon(imageVector = Icons.Default.Pending, contentDescription = "", modifier = Modifier
                        .weight(0.2f)
                        .align(Alignment.CenterVertically))
                    Column(
                        modifier = Modifier.weight(0.8f)
                    ) {
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(text = "Thẻ ghi nhớ", fontSize = 20.sp)
                        Text(text = "Ôn lại các thuật ngữ và định nghĩa", fontSize = 14.sp)
                        Spacer(modifier = Modifier.height(10.dp))
                    }
                }
            }
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
               Text(text = "Thẻ", fontSize = 18.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(start = 15.dp))
            }
            LazyColumn{
                items(lesson.listVocabularyLesson!!){
                    Card(
                        elevation = 5.dp,
                        shape = RoundedCornerShape(5.dp),
                        backgroundColor = Color.White,
                        modifier = Modifier
                            .padding(top = 5.dp, start = 15.dp, end = 15.dp, bottom = 10.dp)
                            .fillMaxWidth()
                            .height(80.dp)
                    ) {
                        Box(contentAlignment = Alignment.CenterStart ){
                           Column(modifier = Modifier.padding(10.dp)) {
                               Text(
                                   text = it.meaning,
                                   fontSize = 16.sp,
                                   color = Color.Black.copy(0.8f),
                                   fontWeight = FontWeight.W200
                               )
                               Spacer(modifier = Modifier.height(10.dp))
                               Text(
                                   text = it.word,
                                   fontSize = 16.sp,
                                   color = Color.DarkGray,
                                   fontFamily = FontFamily.SansSerif
                               )
                           }
                        }
                    }
                }
            }
        }
    }
}
package com.plcoding.bakeryenglish.presentation.screen

import android.util.Log
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
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.plcoding.bakeryenglish.R
import com.plcoding.bakeryenglish.core.RouteScreen
import com.plcoding.bakeryenglish.data.local.database.DictonaryDao
import com.plcoding.bakeryenglish.presentation.event.Event
import com.plcoding.bakeryenglish.presentation.viewmodel.HomeViewModel
import com.plcoding.bakeryenglish.presentation.viewmodel.IntroduceLessonViewModel

@Composable
fun IntroduceLesson(navController: NavController) {
    val introduceLessonViewModel:IntroduceLessonViewModel = hiltViewModel();
    val homeViewModel:HomeViewModel = hiltViewModel()
    val lesson = introduceLessonViewModel.lesson.value

    var isExpanedDropdownmenu by remember {
        mutableStateOf(false)
    }

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
                IconButton(onClick = { isExpanedDropdownmenu = !isExpanedDropdownmenu  }) {
                    Icon(imageVector = Icons.Default.Settings, contentDescription = "" )
                }
                 DropdownMenu(expanded = isExpanedDropdownmenu, modifier = Modifier.wrapContentSize(), onDismissRequest = {isExpanedDropdownmenu = false}) {
                     DropdownMenuItem(onClick = {
                         introduceLessonViewModel.onEvent(Event.DeleteLesson(lesson.id!!))
                         navController.navigate(RouteScreen.Home.route)
                         //navController.popBackStack()
                      }
                     ) {
                         Row() {
                             val image = painterResource(id = R.drawable.baseline_delete_24)
                             Icon(painter = image, contentDescription = "")
                             Text(
                                 text = "Xóa học phần",
                                 Modifier.size(20.dp),
                                 fontWeight = FontWeight.Bold,
                                 color = Color.Gray.copy(0.2f)
                             )
                         }
                     }
                     DropdownMenuItem(onClick = { navController.navigate(RouteScreen.CreateLesson.route+"?lessonID=${lesson.id}")}) {
                         val image = painterResource(id = R.drawable.baseline_edit_24)
                         Icon(painter = image, contentDescription = "")
                         Text(
                             text = "Sửa học phần",
                             Modifier.size(20.dp),
                             fontWeight = FontWeight.Bold,
                             color = Color.Gray.copy(0.2f)
                         )
                     }
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
            Spacer(modifier = Modifier.height(15.dp))
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
            Card(
                elevation = 2.dp,
                shape = RoundedCornerShape(2.dp),
                backgroundColor = Color.White,
                modifier = Modifier.padding(15.dp)
            ) {
                Row(
                    Modifier
                        .clickable {
                            navController.navigate(RouteScreen.FlashCardPage.route + "?lessonID=${lesson.id}")
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
                        Text(text = "Flashcard", fontSize = 20.sp)
                        Text(text = "Học bộ thẻ với các flashcard dễ nhớ", fontSize = 14.sp)
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
@Composable
fun DropDowMenuBox(onDismiss: () -> Unit, onClickDelete: () ->Unit, onEdit: () ->Unit,isExpand:Boolean) {
    DropdownMenu(expanded = isExpand, modifier = Modifier.wrapContentSize(), onDismissRequest = {onDismiss}) {
        DropdownMenuItem(onClick = { onClickDelete }) {
            Row() {
                val image = painterResource(id = R.drawable.baseline_delete_24)
                Icon(painter = image, contentDescription = "")
                Text(
                    text = "Xóa học phần",
                    Modifier.size(20.dp),
                    fontWeight = FontWeight.Bold,
                    color = Color.Gray.copy(0.2f)
                )
            }
        }
        DropdownMenuItem(onClick = {onEdit}) {
            val image = painterResource(id = R.drawable.baseline_edit_24)
            Icon(painter = image, contentDescription = "")
            Text(
                text = "Sửa học phần",
                Modifier.size(20.dp),
                fontWeight = FontWeight.Bold,
                color = Color.Gray.copy(0.2f)
            )
        }
    }
}

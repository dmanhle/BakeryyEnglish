package com.plcoding.bakeryenglish.presentation.screen

import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.plcoding.bakeryenglish.core.RouteScreen
import com.plcoding.bakeryenglish.domain.model.WordOfLesson
import com.plcoding.bakeryenglish.presentation.event.Event
import com.plcoding.bakeryenglish.presentation.screen.components.ComponentInsertVocabulary
import com.plcoding.bakeryenglish.presentation.viewmodel.CreateLessonViewModel
import com.google.accompanist.insets.*
import rememberImeState


@RequiresApi(Build.VERSION_CODES.R)
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CreateLessonPage(navController: NavController) {
    val createLessonViewModel:CreateLessonViewModel = hiltViewModel();
    val textFieldNameLesson = createLessonViewModel.textFieldCreateLesson;
//    listCompose chứa các các compose khi người dùng bấm thêm phần tử
    val composableList = createLessonViewModel.listComposable
//    list từ vựng người dùng nhập vào
    val getListVocaUserInput = createLessonViewModel._listWordOfLesson.value

//    Lấy context hiện tại
    val context = LocalContext.current;
//   Lấy scrollState lấy vị trí scroll
    val scrollState = rememberScrollState();
//    ImeState để phát hiện xem keyboard có được open;
    val imeState = rememberImeState()
    // xử lí khi keyboard overlap content
    LaunchedEffect(key1 = imeState.value){
        if(imeState.value){
            scrollState.animateScrollTo(scrollState.value+200)
        }else{
            scrollState.animateScrollTo(scrollState.value-100)
        }
    }


    ProvideWindowInsets {
        Scaffold(
            floatingActionButton = {
                FloatingActionButton(
                    onClick = {
                        composableList.add {
                            ComponentInsertVocabulary()
                        }
                    },
                    backgroundColor = Color.Blue,
                    contentColor = Color.White
                ){
                    Icon(Icons.Filled.Add,"")
                }
            },
            topBar = {
                TopAppBar(
                    title = {
                        Text(text = "Tạo học phần")
                    },
                    navigationIcon = {
                        IconButton(onClick = {
                        }) {
                            Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Navigation icon")
                        }
                    },
                    backgroundColor = Color.White,
                    actions ={
                        IconButton(onClick = {
                            if (
                                createLessonViewModel.textFieldCreateLesson.value.isEmpty() ||
                                createLessonViewModel.listWordOfLesson.value.isEmpty()
                            ){
                                Toast.makeText(context,"Vui lòng nhập đủ thông tin", Toast.LENGTH_SHORT).show()
                            }else{
                                createLessonViewModel.onEvent(Event.SaveVocabularyLesson())
                                navController.navigate(RouteScreen.Home.route)
                            }
                        }) {
                            Icon(imageVector = Icons.Default.Check, contentDescription = "description")
                        }
                    },

                    )
            },
            modifier = Modifier
                .fillMaxHeight()
                .background(Color.Gray.copy(0.05f))
                .systemBarsPadding()
        ){
            Box(modifier = Modifier
                .background(Color.Gray.copy(0.05f))
                .padding(10.dp)
                .verticalScroll(scrollState)
            ){
                Column(Modifier.imePadding()) {
                    TextField(
                        value = textFieldNameLesson.value.toString(),
                        onValueChange ={
                            createLessonViewModel.onEvent(Event.CreateLesson(it))
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.Gray.copy(0.05f))
                            .height(50.dp)
                            .fillMaxWidth(),
                        colors = TextFieldDefaults.textFieldColors(
                            backgroundColor = Color.Transparent
                        ),
                    )
                    Text(text = "Tiêu đề", fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(40.dp))

                    val listVocabulary = composableList.mapIndexed{ index, insertComponent ->
                        val state = rememberDismissState(
                            confirmStateChange = {
                                if (it == DismissValue.DismissedToStart) {
                                    composableList.remove(insertComponent)
                                    createLessonViewModel._listWordOfLesson.value.removeAt(index)
                                }
                                true
                            }
                        )
                        SwipeToDismiss(
                            state = state,
                            background = {
                                val color = when (state.dismissDirection) {
                                    DismissDirection.StartToEnd -> Color.Transparent
                                    DismissDirection.EndToStart -> Color.Red
                                    null -> Color.Transparent
                                }

                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .background(color = color)
                                        .padding(8.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Delete,
                                        contentDescription = "Delete",
                                        tint = Color.White,
                                        modifier = Modifier.align(Alignment.CenterEnd)
                                    )
                                }
                            },
                            dismissContent = {
                                val wordOfLesson = insertComponent()
                                if (getListVocaUserInput.isEmpty()) {
                                    getListVocaUserInput.add(wordOfLesson)
                                } else if (index < getListVocaUserInput.size) {
                                    getListVocaUserInput[index] = wordOfLesson
                                } else {
                                    getListVocaUserInput.add(wordOfLesson)
                                }
                            },
                            directions = setOf(DismissDirection.EndToStart)
                        )
                        Spacer(modifier = Modifier
                            .padding(top = 20.dp)
                            .fillMaxWidth())
                    }
                    Spacer(modifier = Modifier
                        .height(200.dp)
                        .fillMaxWidth())
                }
            }
        }
    }
}











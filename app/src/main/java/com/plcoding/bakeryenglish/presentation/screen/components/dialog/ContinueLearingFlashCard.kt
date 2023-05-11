package com.plcoding.bakeryenglish.presentation.screen.components.dialog

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.gson.Gson
import com.plcoding.bakeryenglish.core.RouteScreen
import com.plcoding.bakeryenglish.domain.model.WordOfLesson
import com.plcoding.bakeryenglish.presentation.event.Event
import com.plcoding.bakeryenglish.presentation.viewmodel.FlashCardViewModel
import com.plcoding.bakeryenglish.presentation.viewmodel.LearingLessonViewModel
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun onDialogCelebrate(navController: NavController){
    val dialogState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)

    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        dialogState.show()
    }
    ModalBottomSheetLayout(
        sheetState = dialogState,
        content = {

        },
        sheetContent = {
            Column {
                Text("Chúc mừng!")
                Text("Bạn đã hoàn thành học xong flashcard.")
                Button(
                    onClick = {
                        scope.launch {
                            dialogState.hide()
                        }
                        navController.navigate(RouteScreen.Home.route)
                        // Xử lý sự kiện khi người dùng bấm nút đồng ý
                    }
                ) {
                    Text("OK")
                }
            }
        }
    )
}

@Composable
fun AlertDialogDemo(navController:NavController,list:ArrayList<WordOfLesson>) {
    var showDialog by remember { mutableStateOf(false) }

    Column {
        AlertDialog(
            onDismissRequest = {
                showDialog = false
            },
            title = {
                Text(text = "Thông báo")
            },
            text = {
                Text(text = "Bạn còn ${list.size} từ vựng chưa học xong \n Bạn có muốn tiếp tục học tiếp ?")
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        //Do something when the user clicks the positive button
                        //val listRelearning = flashCardViewModel.listUnfinished.value;
                        //learingLessonViewModel.onEvent(Event.ReLearningFlashCard(listRelearning))
                        navController.navigate(RouteScreen.FlashCardPage.route+"?list=${Gson().toJson(list)}")
                    }
                ) {
                    Text(text = "Tiếp tục")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        // Do something when the user clicks the negative button
                        showDialog = false
                        navController.navigate(RouteScreen.Home.route)
                    }
                ) {
                    Text(text = "Cancel")
                }
            }
        )
    }
}


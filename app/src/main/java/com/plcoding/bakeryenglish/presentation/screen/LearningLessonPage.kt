package com.plcoding.bakeryenglish.presentation.screen
import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.pager.*
import com.plcoding.bakeryenglish.core.RouteScreen
import com.plcoding.bakeryenglish.presentation.event.Event
import com.plcoding.bakeryenglish.presentation.viewmodel.LearingLessonViewModel
import com.plcoding.bakeryenglish.R
import kotlinx.coroutines.launch

@SuppressLint("UnrememberedMutableState", "SuspiciousIndentation")
@OptIn(ExperimentalPagerApi::class, ExperimentalMaterialApi::class)
@Composable
fun LearningLessonPage(navController: NavController) {

    val learingLessonViewModel:LearingLessonViewModel = hiltViewModel()
    val lesson = learingLessonViewModel.lesson.value

    val pagerState = learingLessonViewModel.pagerState.value

    val pageCurrent = remember { mutableStateOf(0) }
    var pagePrevious = remember { mutableStateOf(-1) }


    var isShowDialogCorrect = remember {
        mutableStateOf(false)
    }
    var isShowDialogIncorrect = remember {
        mutableStateOf(false)
    }

    var clickableContinue = remember {
        mutableStateOf(false)
    }

    var currentProgress = remember {
        mutableStateOf(0.1f)
    }
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        floatingActionButton = {
        },
        topBar = {
            TopAppBar(
                title = {
                    Column(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "${pageCurrent.value} / ${lesson.listVocabularyLesson!!.size}",
                            modifier = Modifier
                                .align(CenterHorizontally)
                                .padding(end = 30.dp)
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.navigateUp()
                    }) {
                        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Navigation icon")
                    }
                },
                backgroundColor = Color.Transparent,
                actions ={
                    IconButton(onClick = {
                        navController.navigateUp()
                    }) {
                        Icon(imageVector = Icons.Default.Check, contentDescription = "description")
                    }
                },
                modifier = Modifier.background(Color.Transparent),
                elevation = 0.0001.dp
            )
        },
        modifier = Modifier
            .fillMaxHeight()
            .background(Color.Transparent)
    ){
        Column (modifier = Modifier.padding(it)){
            // hiển thị thanh giá trị progress
            MyLoadingIndicator(currentProgress.value)

            // hiển thị viewpager
            HorizontalPager(state = pagerState, count = lesson.listVocabularyLesson!!.size) { page ->
                pageCurrent.value = pagerState.currentPage+1

                // thiết lập giá trị hiển thị cho thanh progress = trang hiển tại chia cho tổng số trang
                currentProgress.value = (currentPage+1).toFloat() / lesson.listVocabularyLesson!!.size.toFloat()

                Card(
                    elevation = 4.dp,
                    shape = RoundedCornerShape(15.dp),
                    backgroundColor = Color.White,
                    modifier = Modifier.padding(start = 30.dp, end = 30.dp, top = 30.dp, bottom = 60.dp)
                ) {
                    Box(Modifier.fillMaxSize()) {
                        if(pageCurrent.value != pagePrevious.value){
                            learingLessonViewModel.onEvent(Event.RandomOptionOfAnswer(currentPage))
                            pagePrevious.value = pageCurrent.value
                        }
                        // khởi tạo một ramdon gồm 3 câu trả lời
                        Column() {
                            Spacer(modifier = Modifier
                                .height(50.dp)
                                .fillMaxWidth())
                            Text(
                                "Câu hỏi ${currentPage+1}: Nghĩa của từ ${lesson.listVocabularyLesson!![page].word} là ?",
                                modifier = Modifier.padding(15.dp),
                                fontWeight = FontWeight.W800,
                                fontSize = 18.sp
                            )
                            Card(modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 20.dp, start = 15.dp, end = 15.dp, bottom = 20.dp)
                                .height(60.dp)
                                .clickable {
                                    val yourAnswer =
                                        learingLessonViewModel.listRamdomOptionOfAnswer.value[0]
                                    val answerCorrect = lesson.listVocabularyLesson!![page].meaning
                                    if (yourAnswer == answerCorrect) {
                                        isShowDialogCorrect.value = true
                                        // when user answer correctly is next questio
                                    } else {
                                        isShowDialogIncorrect.value = true
                                    }
                                }
                            ) {
                                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                                    Text(
                                        text = learingLessonViewModel.listRamdomOptionOfAnswer.value[0],
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.W500
                                    )
                                }
                            }
                            Card(modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 20.dp, start = 15.dp, end = 15.dp, bottom = 20.dp)
                                .height(60.dp)
                                .clickable {
                                    val yourAnswer =
                                        learingLessonViewModel.listRamdomOptionOfAnswer.value[1]
                                    val answerCorrect = lesson.listVocabularyLesson!![page].meaning
                                    if (yourAnswer == answerCorrect) {
                                        isShowDialogCorrect.value = true

                                    } else {
                                        isShowDialogIncorrect.value = true
                                    }
                                }
                            ) {
                                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                                    Text(
                                        text = learingLessonViewModel.listRamdomOptionOfAnswer.value[1],
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.W500
                                    )
                                }
                            }
                            Card(modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 20.dp, start = 15.dp, end = 15.dp, bottom = 20.dp)
                                .height(60.dp)
                                .clickable {
                                    val yourAnswer =
                                        learingLessonViewModel.listRamdomOptionOfAnswer.value[2]
                                    val answerCorrect = lesson.listVocabularyLesson!![page].meaning
                                    if (yourAnswer == answerCorrect) {
                                        isShowDialogCorrect.value = true
                                    } else {
                                        isShowDialogIncorrect.value = true
                                    }
                                }
                            ) {
                                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                                    Text(
                                        text = learingLessonViewModel.listRamdomOptionOfAnswer.value[2],
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.W500
                                    )
                                }
                            }
                        }
                    }
                }
            }
            HorizontalPagerIndicator(
                pagerState = pagerState,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }
    }
    if(isShowDialogCorrect.value){
        CustomDialogCorrect(
            onDismissRequest = {
                isShowDialogCorrect.value = false;
                coroutineScope.launch {
                    if(pagerState.currentPage < pagerState.pageCount-1){
                        pagerState.scrollToPage(pagerState.currentPage+1);

                    } else if(pagerState.currentPage == pagerState.pageCount-1){
                        navController.navigate(RouteScreen.SuccessfullyLearning.route)
                    }
                }
            }
        )
    }
    if(isShowDialogIncorrect.value){
        CustomDialogIncorrect(){
            isShowDialogIncorrect.value = false
        }
    }
}

@Composable
fun CustomDialogCorrect(
    onDismissRequest: () -> Unit,
) {
    val painter: Painter = painterResource(id = R.drawable.baseline_check_24)
    Dialog(
        onDismissRequest = onDismissRequest,
    ) {
        Surface(
            shape = RoundedCornerShape(8.dp),
            color = Color.White,
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Image(painter = painter, contentDescription = "",Modifier.size(30.dp))
                Spacer(modifier = Modifier
                    .height(1.dp)
                    .fillMaxWidth()
                    .background(Color.Gray))
                Text(
                    text = "Kết quả đúng",
                    style = MaterialTheme.typography.h5,
                    color = MaterialTheme.colors.primary
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Tiếp tục nàooo !!",
                    style = MaterialTheme.typography.body1,
                    color = MaterialTheme.colors.onSurface
                )
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    TextButton(
                        onClick = { onDismissRequest }
                    ) {
                        Text(
                            text = "Tiếp tục",
                            style = MaterialTheme.typography.button,
                            color = MaterialTheme.colors.primary,
                        )
                    }
                }
            }
        }
    }
}
@Composable
fun CustomDialogIncorrect(
    onDismissRequest: () -> Unit,
) {
    val painter: Painter = painterResource(id = R.drawable.wrong_svgrepo_com)
    Dialog(
        onDismissRequest = onDismissRequest
    ) {
        Surface(
            shape = RoundedCornerShape(8.dp),
            color = Color.White,
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Image(painter = painter, contentDescription = "", modifier = Modifier.size(20.dp))
                Spacer(modifier = Modifier
                    .height(1.dp)
                    .fillMaxWidth()
                    .background(Color.Gray))
                Text(
                    text = "Kết quả sai",
                    style = MaterialTheme.typography.h5,
                    color = MaterialTheme.colors.primary
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Vui lòng chọn lại nhaaa !!",
                    style = MaterialTheme.typography.body1,
                    color = MaterialTheme.colors.onSurface
                )
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Tiếp tục lại",
                        style = MaterialTheme.typography.button,
                        color = MaterialTheme.colors.primary,
                        modifier = Modifier.clickable {
                            onDismissRequest
                        }
                    )
                }
            }
        }
    }
}
@Composable
fun MyLoadingIndicator(currentProgress:Float) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(2.dp)
            .background(color = Color.LightGray)
    ) {
        LinearProgressIndicator(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.primary,
            progress = currentProgress
        )
    }
}







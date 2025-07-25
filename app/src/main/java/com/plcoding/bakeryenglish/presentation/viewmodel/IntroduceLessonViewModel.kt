package com.plcoding.bakeryenglish.presentation.viewmodel

import androidx.compose.runtime.*
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plcoding.bakeryenglish.data.local.database.DictonaryDao
import com.plcoding.bakeryenglish.domain.model.Lesson
import com.plcoding.bakeryenglish.presentation.event.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class IntroduceLessonViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val dao: DictonaryDao
):ViewModel() {
    private var _lesson = mutableStateOf(Lesson(-1,"", emptyList()))
    val lesson:State<Lesson> = _lesson;

    var isExpanedDropdownmenu  = mutableStateOf(true)


    init {
        savedStateHandle.get<Int>("lessonID")?.let {
            if(it != -1){
                _lesson.value = dao.getLessonByID(it)
            }else{
            }
        }
    }
    fun onEvent(event: Event){
        when(event){
            is Event.DeleteLesson ->{
                dao.deleteLesson(ids = event.id)
            }

            else -> {}
        }
    }
}

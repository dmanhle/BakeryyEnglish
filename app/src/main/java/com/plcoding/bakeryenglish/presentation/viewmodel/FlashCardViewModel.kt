package com.plcoding.bakeryenglish.presentation.viewmodel

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.plcoding.bakeryenglish.data.local.database.Converter
import com.plcoding.bakeryenglish.data.local.database.DictonaryDao
import com.plcoding.bakeryenglish.domain.model.Lesson
import com.plcoding.bakeryenglish.domain.model.WordOfLesson
import com.plcoding.bakeryenglish.presentation.event.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.Serializable
import javax.inject.Inject

@HiltViewModel
class FlashCardViewModel @Inject constructor(
    private val dictonaryDao: DictonaryDao,
    private val savedStateHandle: SavedStateHandle,

) :ViewModel() {
    private var _lesson =  mutableStateOf(Lesson(-1,"", emptyList()))
    val lesson: State<Lesson> = _lesson


    @OptIn(ExperimentalPagerApi::class)
    val pagerState = mutableStateOf(PagerState(0))


    init {
        savedStateHandle.get<Int>("lessonID").let {
            if(it != -1 && it != null){
                _lesson.value = dictonaryDao.getLessonByID(it!!)
            }else{
                savedStateHandle.get<String>("list").let { data->
                   _lesson.value.listVocabularyLesson = Converter.fromJson(data.toString())
                }
            }
        }
    }
}
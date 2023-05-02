package com.plcoding.bakeryenglish.presentation.viewmodel

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plcoding.bakeryenglish.data.local.database.DictonaryDao
import com.plcoding.bakeryenglish.domain.model.Lesson
import com.plcoding.bakeryenglish.domain.model.WordOfLesson
import com.plcoding.bakeryenglish.presentation.event.Event
import com.plcoding.bakeryenglish.presentation.screen.components.ComponentInsertVocabulary
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateLessonViewModel @Inject constructor(
    private val dictonaryDao: DictonaryDao,
    savedStateHandle: SavedStateHandle
):ViewModel() {
   private var _textFieldCreateLesson = mutableStateOf("");
   val textFieldCreateLesson: State<String> = _textFieldCreateLesson;

   // chứa list từ vựng mà người dùng nhập tay vào
   var _listWordOfLesson = mutableStateOf(ArrayList<WordOfLesson>());
   var listWordOfLesson:State<List<WordOfLesson>> = _listWordOfLesson

//   list chứa các composable function
   var listComposable = mutableStateListOf<@Composable() () -> WordOfLesson>(
      { ComponentInsertVocabulary() },{ ComponentInsertVocabulary()}
   )
   var isControlEdit = -1;

   init {
      savedStateHandle.get<Int>("lessonID").let {
         if(it != -3){
            isControlEdit = it!!
            viewModelScope.launch {
               listComposable.clear()
               Log.d("AAA",it.toString())
               dictonaryDao.getLessonByID(it).listVocabularyLesson?.map {
                  listComposable.add { ComponentInsertVocabulary(it.word,it.meaning) }
               }
               _textFieldCreateLesson.value = dictonaryDao.getLessonByID(it).nameLesson
            }
         }
      }
   }

   fun onEvent(event: Event){
      when(event){
         is Event.CreateLesson -> {
            _textFieldCreateLesson.value = event.lesson;
         }
         is Event.SaveVocabularyLesson ->{
            viewModelScope.launch {
               if (textFieldCreateLesson.value.isNotEmpty() && listWordOfLesson.value.isNotEmpty()){
                  dictonaryDao.insertLesson(Lesson(null,textFieldCreateLesson.value,listWordOfLesson.value))
               }
            }
         }
         is Event.EditVocabularyLesson ->{
            viewModelScope.launch {
               dictonaryDao.updateLesson(event.lesson)
            }
         }
      }
   }
}
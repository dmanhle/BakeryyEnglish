package com.plcoding.bakeryenglish.presentation.event

sealed class Event {
   // Process Search
   class Search(val textInput:String):Event()
   class SelectedTab(val index:Int):Event()
   // - ProcessLesson
   class CreateLesson(val lesson: String):Event()
   class SaveVocabularyLesson():Event()

   // Handle HomeViewModel
   class LoadLesson():Event()
   class ClickToIntroduceLesson:Event()

   // Handle LearningLesson
   class RandomOptionOfAnswer(val indexAnswer:Int):Event()

   // Handle IntroduceLesson
   class DeleteLesson(val id:Int) : Event()
}
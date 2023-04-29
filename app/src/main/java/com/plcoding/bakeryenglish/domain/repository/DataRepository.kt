package com.plcoding.bakeryenglish.domain.repository

import com.plcoding.bakeryenglish.core.Resource
import com.plcoding.bakeryenglish.data.local.model.VocabularyLocal
import com.plcoding.bakeryenglish.data.local.remote.dto.WordInforDTO
import com.plcoding.bakeryenglish.domain.model.Lesson
import kotlinx.coroutines.flow.Flow

interface DataRepository {

     fun getVocabularyLocal(word:String): Flow<Resource<List<VocabularyLocal>>>

     fun getVocabularyApi(word: String):Flow<Resource<List<WordInforDTO>>>

     fun getLesson():Flow<Resource<List<Lesson>>>

}
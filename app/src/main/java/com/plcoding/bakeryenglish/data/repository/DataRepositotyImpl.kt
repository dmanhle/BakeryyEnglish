package com.plcoding.bakeryenglish.data.repository

import com.plcoding.bakeryenglish.core.Resource
import com.plcoding.bakeryenglish.data.local.database.DictonaryDao
import com.plcoding.bakeryenglish.data.local.model.VocabularyLocal
import com.plcoding.bakeryenglish.data.local.remote.DictonaryApi
import com.plcoding.bakeryenglish.data.local.remote.dto.WordInforDTO
import com.plcoding.bakeryenglish.domain.model.Lesson
import com.plcoding.bakeryenglish.domain.repository.DataRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class DataRepositotyImpl(
    private val dao: DictonaryDao,
    private val dictonaryApi: DictonaryApi
) :DataRepository{
    override fun getVocabularyLocal(
        word: String
    ): Flow<Resource<List<VocabularyLocal>>> = flow {
        emit(Resource.Loading<List<VocabularyLocal>>("Loading....."));
        val list = dao.loadWordInfor(word)
        emit(Resource.Success<List<VocabularyLocal>>(list))
    }

    override fun getVocabularyApi(word: String): Flow<Resource<List<WordInforDTO>>> = flow{
            emit(Resource.Loading<List<WordInforDTO>>("Loading "))
            var list = emptyList<WordInforDTO>()
            try {
                 list = dictonaryApi.getInforWordDTO(word);
            }catch (e:HttpException){
                emit(Resource.Error<List<WordInforDTO>>("Lỗi e: ${e.response()}"))
            }catch (io:IOException){
                emit(Resource.Error<List<WordInforDTO>>("Không có kết nối mạng"))
            }
            emit(Resource.Success(data = list))
        }

    override fun getLesson(): Flow<Resource<List<Lesson>>>  = flow {
        emit(Resource.Loading<List<Lesson>>("Loading..."))
        var list = emptyList<Lesson>()
        try {
           list = dao.getAllLesson()
        }catch (io:IOException){
            emit(Resource.Error<List<Lesson>>("Lỗi io"))
        }catch (e:HttpException){
            emit(Resource.Error<List<Lesson>>("Lỗi e"))
        }
        emit(Resource.Success<List<Lesson>>(list))
    }

    override fun updateLesson(lesson: Lesson) {
        dao.updateLesson(lesson)
    }
}
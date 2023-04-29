package com.plcoding.bakeryenglish.data.local.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.plcoding.bakeryenglish.data.local.model.VocabularyLocal
import com.plcoding.bakeryenglish.domain.model.Lesson

@Dao
interface DictonaryDao {
    @Query("SELECT * FROM vocabularylocal WHERE word IN (:word)")
    suspend fun loadWordInfor(word: String): List<VocabularyLocal>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLesson(lesson: Lesson)

    @Query("Select * from lesson")
    suspend fun getAllLesson():List<Lesson>;

    @Query("Select * from lesson where id = :ids " )
    suspend fun getLessonByID(ids:Int):Lesson

//    @Query("SELECT * FROM vocabularylocal WHERE word like '%' || :word || '%'")
//    suspend fun getListSuggestedWord(word: String):List<WordOfLesson>
}
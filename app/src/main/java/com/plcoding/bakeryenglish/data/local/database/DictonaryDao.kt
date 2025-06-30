package com.plcoding.bakeryenglish.data.local.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.plcoding.bakeryenglish.data.local.model.VocabularyLocal
import com.plcoding.bakeryenglish.domain.model.Lesson

@Dao
interface DictonaryDao {
    @Query("SELECT * FROM vocabularylocal WHERE word IN (:word)")
    fun loadWordInfor(word: String): List<VocabularyLocal>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertLesson(lesson: Lesson): Long

    @Query("Select * from lesson")
    fun getAllLesson():List<Lesson>;

    @Query("Select * from lesson where id = :ids " )
    fun getLessonByID(ids:Int):Lesson

    @Update
    fun updateLesson(lesson: Lesson): Int

    @Query("Delete from lesson where id = :ids")
    fun deleteLesson(ids: Int): Int
//    @Query("SELECT * FROM vocabularylocal WHERE word like '%' || :word || '%'")
//    fun getListSuggestedWord(word: String):List<WordOfLesson>
}
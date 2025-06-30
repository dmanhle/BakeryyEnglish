package com.plcoding.bakeryenglish.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.plcoding.bakeryenglish.data.local.database.Converter

@Entity
@TypeConverters(Converter::class)
data class Lesson(
    @PrimaryKey(autoGenerate = true)
    var id:Int?,
    var nameLesson:String,
    var listVocabularyLesson:List<WordOfLesson>?
) {
}
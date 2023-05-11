package com.plcoding.bakeryenglish.domain.model.part_of_speech

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.plcoding.bakeryenglish.domain.model.WordOfLesson

@Entity
class RelearingWord(
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    val idlesson:Int,
    val list:List<WordOfLesson>
) {

}
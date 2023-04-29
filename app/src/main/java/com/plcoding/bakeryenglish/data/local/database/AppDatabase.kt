package com.plcoding.bakeryenglish.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.plcoding.bakeryenglish.data.local.model.VocabularyLocal
import com.plcoding.bakeryenglish.domain.model.Lesson

@TypeConverters(Converter::class)
@Database(entities = [VocabularyLocal::class,Lesson::class], version = 1, exportSchema = true)
abstract class AppDatabase : RoomDatabase() {
    abstract fun dictonaryDao(): DictonaryDao
}
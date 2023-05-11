package com.plcoding.bakeryenglish.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.plcoding.bakeryenglish.data.local.database.AppDatabase
import com.plcoding.bakeryenglish.data.local.database.DictonaryDao
import com.plcoding.bakeryenglish.data.local.remote.DictonaryApi
import com.plcoding.bakeryenglish.data.repository.DataRepositotyImpl
import com.plcoding.bakeryenglish.domain.model.WordOfLesson
import com.plcoding.bakeryenglish.domain.repository.DataRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideDatabaseBuilder(app:Application):AppDatabase{
        return Room.databaseBuilder(
            app,
            AppDatabase::class.java, "dictonary"
        ).createFromAsset("database/dictonary.db").build()
    }
    @Provides
    @Singleton
    fun provideLessonDao(appDatabase: AppDatabase):DictonaryDao{
        return appDatabase.dictonaryDao();
    }
    @Provides
    @Singleton
    fun provideRepository(dao: DictonaryDao,api:DictonaryApi):DataRepository{
        return DataRepositotyImpl(dao,api);
    }

    @Provides
    @Singleton
    fun provideInstanceDictonay():DictonaryApi{
        return Retrofit.Builder()
            .baseUrl(DictonaryApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(DictonaryApi::class.java)
    }

    @Provides
    @Singleton
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun gson():Gson{
        return Gson()
    }

    @Provides
    @Singleton
    fun provideMyDataList(sharedPreferences: SharedPreferences, gson: Gson): ArrayList<WordOfLesson> {
        val myDataListJson = sharedPreferences.getString("myDataList", "") ?: ""
        val type = object : TypeToken<ArrayList<WordOfLesson>>() {}.type
        return gson.fromJson(myDataListJson, type) ?: arrayListOf()
    }

    @Provides
    @Singleton
    fun provideMyDataListPreference(sharedPreferences: SharedPreferences, gson: Gson): SharedPreferences.Editor {
        return sharedPreferences.edit()
            .putString("myDataList", gson.toJson(provideMyDataList(sharedPreferences, gson)))
    }

    @Provides
    @Singleton
    fun toJon(array: Array<WordOfLesson>,gson: Gson):String {
        return Gson().toJson(array)
    }

    @Provides
    @Singleton
    fun fromJson(json: String,gson: Gson): List<WordOfLesson> {
        val listType = object : TypeToken<List<WordOfLesson>>() {}.type
        return Gson().fromJson(json, listType)
    }

}
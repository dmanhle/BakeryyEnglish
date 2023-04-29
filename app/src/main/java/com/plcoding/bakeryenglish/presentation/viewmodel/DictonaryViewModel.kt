package com.plcoding.bakeryenglish.presentation.viewmodel


import android.media.AudioManager
import android.media.MediaPlayer
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plcoding.bakeryenglish.core.Resource
import com.plcoding.bakeryenglish.data.local.remote.dto.TypeOfAudio
import com.plcoding.bakeryenglish.domain.repository.DataRepository
import com.plcoding.bakeryenglish.presentation.state.SearchState
import com.plcoding.bakeryenglish.presentation.event.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class DictonaryViewModel @Inject constructor(
    private val dataRepository: DataRepository,
):ViewModel() {

    private var _searchQuery = mutableStateOf("")
    val searchQuery:State<String> = _searchQuery

    private var _indexTab = mutableStateOf(0)
    val indexTab:State<Int> = _indexTab

    private var _stateSearchTab = mutableStateOf(SearchState())
    val stateSearchTab:State<SearchState> = _stateSearchTab

    private var jobSearch:Job? = null


    fun onEvent(event: Event){
        when(event){
            is Event.Search ->{
                  _searchQuery.value = event.textInput
                  getWord(searchQuery.value)
            }
            is Event.SelectedTab ->{
                _indexTab.value = event.index
            }
        }
    }

    private fun getWord(word:String){
       jobSearch?.cancel()
       jobSearch = viewModelScope.launch {
           delay(1000)
           dataRepository.getVocabularyLocal(word).onEach {it->
               when(it){
                   is Resource.Success ->{
                       if(it.data != null){
                           _stateSearchTab.value = _stateSearchTab.value.copy(
                               isIndicatorLoading = false,
                               listVocabulary = it.data,
                           )
                       }
                       getWordApi(word)
                   }
                   is Resource.Loading ->{
                       _stateSearchTab.value = stateSearchTab.value.copy(
                           isIndicatorLoading = true
                       )
                   }
                   is Resource.Error ->{

                   }
               }
           }.launchIn(this)
       }
    }

    // This function will get audio and update link audio into StateSearch
    fun getWordApi(word: String){
        viewModelScope.launch {
            dataRepository.getVocabularyApi(word).onEach { it ->
                when(it){
                    is Resource.Success ->{
                        if(it.data?.isEmpty() == false){
                            _stateSearchTab.value = _stateSearchTab.value.copy(
                                audio = it.data[0].toAudio()
                            )
                        }else{
                            _stateSearchTab.value = _stateSearchTab.value.copy(
                                audio = TypeOfAudio("","")
                            )
                        }
                    }
                    is Resource.Loading ->{

                    }
                    is Resource.Error ->{
                      Log.d("AAA",it.message.toString())
                    }
                }
            }.launchIn(this)
        }
    }
    fun playAudio(url:String){
        viewModelScope.launch {
            val mediaPlayer = MediaPlayer()
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC)
            try {
                mediaPlayer.setDataSource(url)
                mediaPlayer.prepare()
                mediaPlayer.start()
            } catch (e: IOException) {
                e.printStackTrace()
            }
            Log.v("CCC","Music is streaming")
        }
    }
}
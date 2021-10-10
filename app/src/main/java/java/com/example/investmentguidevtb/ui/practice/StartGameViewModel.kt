package com.example.investmentguidevtb.ui.practice

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.investmentguidevtb.data.source.UserSegmentationDataManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StartGameViewModel @Inject constructor(
    private val userDataManager: UserSegmentationDataManager
): ViewModel() {

    val requestToAddRiskData = MutableLiveData<Float>()
    val requestToAddDifficultData = MutableLiveData<Float>()
    val requestToNotifyAboutDataAvailable = MutableLiveData<Boolean>()


    fun requestDataAndHandledStatus() = viewModelScope.launch{
        requestToAddRiskData.value = userDataManager.getRisk()
        requestToAddDifficultData.value = userDataManager.getDifficulty()
        requestToNotifyAboutDataAvailable.value = userDataManager.getSegmentationPassed()
    }

}
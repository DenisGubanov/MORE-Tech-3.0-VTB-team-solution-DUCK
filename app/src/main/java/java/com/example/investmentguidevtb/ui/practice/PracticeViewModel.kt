package com.example.investmentguidevtb.ui.practice

import androidx.lifecycle.*
import com.example.investmentguidevtb.data.repository.PracticeRepository
import com.example.investmentguidevtb.ui.Event
import com.example.investmentguidevtb.ui.practice.models.GameEndFeedback
import com.example.investmentguidevtb.ui.practice.models.GameSituation
import com.example.investmentguidevtb.ui.practice.models.GameSolution
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PracticeViewModel @Inject constructor(
    private val rep: PracticeRepository
): ViewModel(){

    var prevCapital = 0
    var currentCapital = 0
    var risk = 0f
    var difficult = 0f
    var vtb = 2
    var inflation = 0.05f
    var showStepFeedback = true
    var numberOfSituationsBeforeNaturalEnd = 24

    private val _requestToUpdateSituation = MutableLiveData<GameSituation>()
    val requestToUpdateSituation: LiveData<GameSituation> = _requestToUpdateSituation

    private val _requestToShowStepFeedback = MutableLiveData<Event<GameSolution>>()
    val requestToShowStepFeedback: LiveData<Event<GameSolution>> = _requestToShowStepFeedback

    private val _requestToShowToastContent = MutableLiveData<String>()
    val requestToShowToastContent: LiveData<String> = _requestToShowToastContent

    val requestToChangeCardVisible: LiveData<Boolean> = rep.requestToChangeCardVisible

    val requestToUpdateCapital = MutableLiveData<Int>()

    val requestToEndGame: LiveData<GameEndFeedback> = rep.requestToEndGame


    private fun loadGameSituation() {
        if (isGameEnd()) {
            createEndFeedback()
        } else {
            loadStandardSituation()
        }
    }

    private fun createEndFeedback() {
        viewModelScope.launch {
            rep.requestEndFeedback(
                risk,
                24 - numberOfSituationsBeforeNaturalEnd
            )
        }
    }
    private fun loadStandardSituation() {
        viewModelScope.launch {
            val result = rep.requestSolutionFromServer(
                risk,
                difficult,
                currentCapital - prevCapital,
                vtb
            )
            if (result == null) {
                _requestToShowToastContent.value =
                    "Загрузка провалилась, повторите попытку позже"
            } else {
                numberOfSituationsBeforeNaturalEnd--
                _requestToUpdateSituation.value = result!!
            }
        }
    }

    private fun startInflation() {
        prevCapital = currentCapital
        currentCapital = (currentCapital * (1 - inflation)).toInt()
        requestToUpdateCapital.value = currentCapital
    }

    fun processSelectedSolution(solutionIndex: Int) {
        val selectedSolution = requestToUpdateSituation.value?.solutions?.get(solutionIndex)

        if (selectedSolution == null) {
            // пытаемся загрузить данные, если пользователь карточку передвинул, а выборов нет
            loadGameSituation()
        } else if (showStepFeedback){
            // создание фидбека при наличии контента
            selectedSolution.feedback?.also {
                _requestToShowStepFeedback.value = Event(selectedSolution!!)
            }

            // обработка
            risk = (risk + selectedSolution.delta.risk) / 2
            vtb -= if (selectedSolution.delta.vtb) 1 else 0
            prevCapital = currentCapital
            currentCapital += selectedSolution.delta.capital

            startInflation()

            // запрос на загрузку новой карточки
            loadGameSituation()
        }
    }
    private fun isGameEnd() =
         (currentCapital >=100 || numberOfSituationsBeforeNaturalEnd == 0)

}
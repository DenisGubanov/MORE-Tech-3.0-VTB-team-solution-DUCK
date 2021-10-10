package com.example.investmentguidevtb.data.source

import com.example.investmentguidevtb.ui.practice.models.GameEndFeedback
import com.example.investmentguidevtb.ui.profile.ProfileFragment
import javax.inject.Inject

class FeedbackBuilder @Inject constructor(
    private val userSegmentationDataManager: UserSegmentationDataManager
){

    suspend fun buildGameEndFeedback(
        risk: Float,
        numberOfDaysFromBeginningToEnd: Int):
    GameEndFeedback {

        val mainGoal = userSegmentationDataManager.getMainGoal()
        val mainGoalPicturesList = goalsAndPictures[mainGoal]

        val goalResultPicture = when(numberOfDaysFromBeginningToEnd) {
            in 0..9 -> mainGoalPicturesList?.get(0)
            in 10..16 -> mainGoalPicturesList?.get(1)
            in 17..24 -> mainGoalPicturesList?.get(2)
            else -> ""
        }

        val goalResultDescription = when(numberOfDaysFromBeginningToEnd) {
            in 0..24 -> textForWining
            else -> textForLosing
        }

        val riskResultPicture = when {
            risk > 0.7 -> mainGoalPicturesList?.get(0)
            risk > 0.4 -> mainGoalPicturesList?.get(1)
            else -> mainGoalPicturesList?.get(2)
        }

        val riskResultDescription = "${(risk * 100).toInt()}% твои действий можно считать рискованными. Плохо это или хорош? - А кто его знает!"

        return GameEndFeedback(goalResultPicture, goalResultDescription, riskResultPicture, riskResultDescription)
    }

    companion object {

        val textForLosing = "Тебе не удалось достичь своей цели, но ты приобрёл нечто большее - опыт и знания, с помощью которых ты сможешь достичь своей цели!\n" +
                " Запускай игру, продолжаем познавать инвестиции!"
        val textForWining = "Ты накопил на свою мечту! Поздравляю! Ты можешь попробовать улучшить свой результат, либо начать инвестировать в реальной жизни с помощью приложения ВТБ Мои инвестиции."

        val risksResult = listOf(
            "https://stocktrainer.ru/wp-content/uploads/Riski-na-fondovom-ryinke.jpg",
            "https://incrussia.ru/wp-content/uploads/2019/03/iStock-918704584-1.jpg",
            "https://img.gazeta.ru/files3/169/7786169/upload-02-pic905-895x505-98668.jpg"
        )

        val goalsAndPictures = mapOf(
            ProfileFragment.CAR to listOf(
                "https://upload.wikimedia.org/wikipedia/commons/4/4e/Red_2019_Ferrari_SF90_Stradale_%2848264238897%29.jpg",
                "https://upload.wikimedia.org/wikipedia/commons/4/4f/Tesla_Model_S_02_2013.jpg",
                "https://kubnews.ru/upload/iblock/2e1/2e1d16651a099743a751d1e946cef046.jpg"
            ),
            ProfileFragment.FLAT to listOf(
                "https://www.ccnova.ru/images/blog/sovremennye-doma/sovremennyj-dom-4.jpg",
                "https://cdn.vdmsti.ru/image/2021/2c/1crdzc/original-1r70.jpg",
                "https://houses100.ru/projects/256a1.jpg"
            ),
            ProfileFragment.PENSION to listOf(
                "https://cdnimg.rg.ru/img/content/212/68/69/iStock-1173217004_d_850.jpg",
                "https://cdn.profile.ru/wp-content/uploads/2021/01/shutterstock_1638163480-1200x675.jpg",
                "https://regnum.ru/uploads/pictures/news/2017/05/25/regnum_picture_149570152688627_big.jpg"
            ),
            ProfileFragment.TRAVELLING to listOf(
                "https://img.championat.com/s/735x490/news/big/v/r/na-kraju-zemli-10-neobychnyh-idej-dlja-puteshestvija_1517331980822771222.jpg",
                "https://rubic.us/wp-content/uploads/2018/02/iw-US-Roatrip-e1518285949838-1024x642.jpg",
                "https://chance4traveller.com/wp-content/uploads/2020/05/photo-1508672019048-805c876b67e2.jpeg"
            )
        )
    }
}
package com.semenovdev.addendtrainer.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GameResult(
    val isSuccess: Boolean,
    val correctAnswersCount: Int,
    val totalAnswersCount: Int,
    val percentage: Int,
    val gameSettings: GameSettings
): Parcelable {

    val correctAnswersCountString: String
        get() = correctAnswersCount.toString()

    val percentageString: String
        get() = percentage.toString()

}
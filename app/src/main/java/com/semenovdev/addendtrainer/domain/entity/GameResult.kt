package com.semenovdev.addendtrainer.domain.entity

import java.io.Serializable

data class GameResult(
    val isSuccess: Boolean,
    val correctAnswersCount: Int,
    val totalAnswersCount: Int,
    val gameSettings: GameSettings
): Serializable
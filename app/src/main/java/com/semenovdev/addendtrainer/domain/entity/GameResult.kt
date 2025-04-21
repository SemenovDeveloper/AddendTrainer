package com.semenovdev.addendtrainer.domain.entity

data class GameResult(
    val isSuccess: Boolean,
    val correctAnswersCount: Int,
    val totalAnswersCount: Int,
    val gameSettings: GameSettings
)
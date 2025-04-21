package com.semenovdev.addendtrainer.domain.repository

import com.semenovdev.addendtrainer.domain.entity.GameSettings
import com.semenovdev.addendtrainer.domain.entity.Level
import com.semenovdev.addendtrainer.domain.entity.Question

interface GameRepository {

    fun generateQuestion(
        maxSumValue: Int,
        countOfOptions: Int
    ): Question

    fun getGameSettings(level: Level): GameSettings
}
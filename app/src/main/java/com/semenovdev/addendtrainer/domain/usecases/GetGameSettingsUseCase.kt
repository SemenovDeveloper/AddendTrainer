package com.semenovdev.addendtrainer.domain.usecases

import com.semenovdev.addendtrainer.domain.entity.GameSettings
import com.semenovdev.addendtrainer.domain.entity.Level
import com.semenovdev.addendtrainer.domain.entity.Question
import com.semenovdev.addendtrainer.domain.repository.GameRepository

class GetGameSettingsUseCase(
    private val repository: GameRepository
) {
    operator fun invoke (level: Level): GameSettings {
        return repository.getGameSettings(level)
    }
}
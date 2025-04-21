package com.semenovdev.addendtrainer.domain.usecases

import com.semenovdev.addendtrainer.domain.entity.GameSettings
import com.semenovdev.addendtrainer.domain.entity.Question
import com.semenovdev.addendtrainer.domain.repository.GameRepository

class GenerateQuestionUseCase(
    private val repository: GameRepository
) {
    operator fun invoke(maxSumValue: Int): Question {
        return repository.generateQuestion(maxSumValue, COUNT_OF_OPTIONS)
    }

    private companion object {
        private const val COUNT_OF_OPTIONS = 6
    }
}
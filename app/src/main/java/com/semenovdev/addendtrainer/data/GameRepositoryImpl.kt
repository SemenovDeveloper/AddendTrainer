package com.semenovdev.addendtrainer.data

import com.semenovdev.addendtrainer.domain.entity.GameSettings
import com.semenovdev.addendtrainer.domain.entity.Level
import com.semenovdev.addendtrainer.domain.entity.Question
import com.semenovdev.addendtrainer.domain.repository.GameRepository
import kotlin.math.max
import kotlin.math.min
import kotlin.random.Random

object GameRepositoryImpl: GameRepository {
    private val MIN_SUM = 2
    private val MIN_ADDEND = 1
    override fun generateQuestion(
        maxSumValue: Int,
        countOfOptions: Int
    ): Question {
        val sum = Random.nextInt(MIN_SUM, maxSumValue + 1)
        val visibleAddend = Random.nextInt(MIN_ADDEND, sum)
        val addend = sum - visibleAddend
        val options = HashSet<Int>()
        options.add(addend)
        val minAddendLimit = max(addend - countOfOptions, MIN_ADDEND)
        val maxAddendLimit = min(addend + countOfOptions, maxSumValue-MIN_ADDEND)

        while (options.size < countOfOptions) {
            options.add(Random.nextInt(minAddendLimit, maxAddendLimit))
        }

        return Question(sum, visibleAddend, options.toList())
    }

    override fun getGameSettings(level: Level): GameSettings {
        return when(level) {
            Level.TEST -> GameSettings(
                maxSumValue = 20,
                minCountRightAnswers = 3,
                minPercentRightAnswers = 50,
                gameTimeInSeconds = 15,
            )
            Level.EASY -> GameSettings(
                maxSumValue = 30,
                minCountRightAnswers = 15,
                minPercentRightAnswers = 50,
                gameTimeInSeconds = 60,
            )
            Level.NORMAL -> GameSettings(
                maxSumValue = 50,
                minCountRightAnswers = 20,
                minPercentRightAnswers = 70,
                gameTimeInSeconds = 45,
            )
            Level.HARD -> GameSettings(
                maxSumValue = 100,
                minCountRightAnswers = 20,
                minPercentRightAnswers = 90,
                gameTimeInSeconds = 45,
            )
        }
    }

}
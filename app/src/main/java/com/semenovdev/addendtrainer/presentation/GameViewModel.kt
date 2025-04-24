package com.semenovdev.addendtrainer.presentation

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.semenovdev.addendtrainer.data.GameRepositoryImpl
import com.semenovdev.addendtrainer.domain.entity.GameSettings
import com.semenovdev.addendtrainer.domain.entity.Level
import com.semenovdev.addendtrainer.domain.entity.Question
import android.os.CountDownTimer
import android.text.format.DateUtils
import androidx.lifecycle.ViewModel
import com.semenovdev.addendtrainer.R
import com.semenovdev.addendtrainer.domain.entity.GameResult
import com.semenovdev.addendtrainer.domain.usecases.GenerateQuestionUseCase
import com.semenovdev.addendtrainer.domain.usecases.GetGameSettingsUseCase


class GameViewModel(
    private val level: Level,
    private val application: Application
) : ViewModel() {
    private val repository = GameRepositoryImpl

    private val getGameSettingsUseCase = GetGameSettingsUseCase(repository)
    private val generateQuestionUseCase =  GenerateQuestionUseCase(repository)

    private var timer: CountDownTimer? = null
    private lateinit var settings: GameSettings
    private var correctAnswersCount = 0
    private var totalAnswersCount = 0

    private val _currentQuestion = MutableLiveData<Question>()
    val currentQuestion: LiveData<Question>
        get() = _currentQuestion

    private val _remainingTime = MutableLiveData<String>()
    val remainingTime: LiveData<String>
        get() = _remainingTime

    private val _correctAnswersPercent = MutableLiveData<Int>()
    val correctAnswersPercent: LiveData<Int>
        get() = _correctAnswersPercent

    private val _progress = MutableLiveData<String>()
    val progress: LiveData<String>
        get() = _progress

    private val _secondaryProgress = MutableLiveData<Int>()
    val secondaryProgress: LiveData<Int>
        get() = _secondaryProgress

    private val _isEnoughAnswers = MutableLiveData<Boolean>(false)
    val isEnoughAnswers: LiveData<Boolean>
        get() = _isEnoughAnswers

    private val _isEnoughPercent = MutableLiveData<Boolean>()
    val isEnoughPercent: LiveData<Boolean>
        get() = _isEnoughPercent

    private val _gameResult = MutableLiveData<GameResult>()
    val gameResult: LiveData<GameResult>
        get() = _gameResult

    init {
        startGame()
    }


    private fun startGame() {
        settings = getGameSettingsUseCase(level)
        _secondaryProgress.value = settings.minPercentRightAnswers
        startTimer()
        generateNextQuestion()
        updateProgress()
    }

    fun checkAnswer (answer: Int) {
        currentQuestion.value?.let {
            var isCorrect = answer ==  it.rightAnswer
            if (isCorrect) {
                correctAnswersCount++
            }
            totalAnswersCount++
        }
        updateProgress()
        generateNextQuestion()
    }

    private fun startTimer () {
        timer = object : CountDownTimer(settings.gameTimeInSeconds * MILLISECONDS_IN_SECOND, MILLISECONDS_IN_SECOND) {
            override fun onTick(millisUntilFinished: Long) {
                _remainingTime.value = formatTime(millisUntilFinished)
            }

            override fun onFinish() {
                finishGame()
            }
        }

        timer?.start()
    }

    private fun finishGame() {
        var isSuccess = isEnoughAnswers.value == true && isEnoughPercent.value == true
        _gameResult.value = GameResult(
            isSuccess,
            correctAnswersCount,
            totalAnswersCount,
            percentage = correctAnswersPercent.value ?: 0,
            gameSettings = settings,
        )
    }

    override fun onCleared() {
        super.onCleared()
        timer?.cancel()
    }

    private fun formatTime(millis: Long): String {
        return DateUtils.formatElapsedTime(millis/MILLISECONDS_IN_SECOND)
    }

    private fun generateNextQuestion() {
        _currentQuestion.value = generateQuestionUseCase(settings.maxSumValue)
    }

    private fun updateProgress() {
        val percent = calculatePercentOfRightAnswers()
        _correctAnswersPercent.value = percent

        _progress.value = String.format(
            application.resources.getString(R.string.progress_answers),
            correctAnswersCount.toString(),
            settings.minCountRightAnswers.toString(),
        )

        _isEnoughAnswers.value = correctAnswersCount >= settings.minCountRightAnswers
        _isEnoughPercent.value = percent >= settings.minPercentRightAnswers
    }

    private fun calculatePercentOfRightAnswers(): Int {
        return (correctAnswersCount/totalAnswersCount.toDouble() * 100).toInt()
    }

    companion object {
        private const val MILLISECONDS_IN_SECOND = 1000L
    }
}

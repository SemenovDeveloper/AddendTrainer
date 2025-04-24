package com.semenovdev.addendtrainer.presentation

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.semenovdev.addendtrainer.R
import com.semenovdev.addendtrainer.databinding.FragmentGameBinding
import com.semenovdev.addendtrainer.domain.entity.GameResult
import com.semenovdev.addendtrainer.domain.entity.Level

class GameFragment : Fragment() {
    private val viewModel: GameViewModel by lazy {
        ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
        )[GameViewModel::class.java]
    }
    private lateinit var level: Level
    private var _binding: FragmentGameBinding? = null
    private val binding: FragmentGameBinding
        get() = _binding ?: throw RuntimeException("FragmentGameBinding == null")

    private val tvOptions by lazy {
        mutableListOf<TextView>().apply {
            add(binding.tvOption1)
            add(binding.tvOption2)
            add(binding.tvOption3)
            add(binding.tvOption4)
            add(binding.tvOption5)
            add(binding.tvOption6)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseArgs()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.startGame(level)

        setTimerView()
        setQuestionFields()
        setProgressFields()
        subscribeToGameResult()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun parseArgs () {
        requireArguments().getParcelable<Level>(KEY_LEVEL)?.let{
            level = it
        }
    }

    private fun launchResultFragment(gameResult: GameResult) {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.main, ResultFragment.newInstance(gameResult))
            .addToBackStack(null)
            .commit()
    }

    private fun setTimerView() {
        viewModel.remainingTime.observe(viewLifecycleOwner) {
            binding.tvTimer.text = it
        }
    }

    private fun setQuestionFields() {
        viewModel.currentQuestion.observe(viewLifecycleOwner) { question ->
            with (binding) {
                tvSum.text = question .sum.toString()
                tvLeftAddend.text = question .visibleNumber.toString()

                for (i in tvOptions.indices){
                    tvOptions[i].text = question.options[i].toString()
                    tvOptions[i].setOnClickListener {
                        viewModel.checkAnswer(question.options[i])
                    }
                }
            }
        }
    }

    private fun setProgressFields() {
        with(binding) {
            viewModel.progress.observe(viewLifecycleOwner) {
                tvCorrectAnswersCount.text = it
            }
            viewModel.isEnoughAnswers.observe(viewLifecycleOwner) {
                tvCorrectAnswersCount.setTextColor(getSuccessColor(it))
            }
            viewModel.correctAnswersPercent.observe(viewLifecycleOwner) {
                progressBar.setProgress(it, true)
            }

            viewModel.isEnoughPercent.observe(viewLifecycleOwner) {
                val color = getSuccessColor(it)
                progressBar.progressTintList = ColorStateList.valueOf(color)
            }

            viewModel.secondaryProgress.observe(viewLifecycleOwner) {
                progressBar.secondaryProgress = it
            }
        }
    }

    private fun subscribeToGameResult() {
        viewModel.gameResult.observe(viewLifecycleOwner) {
            launchResultFragment(it)
        }
    }

    private fun getSuccessColor(isSuccess: Boolean): Int {
        val colorRes = if (isSuccess) {
            R.color.green_100
        } else {
            R.color.red
        }
        return ContextCompat.getColor(requireContext(), colorRes)
    }


    companion object {
        const val NAME = "GameFragment"
        private const val KEY_LEVEL = "level"

        fun newInstance(level: Level): GameFragment {
            return GameFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(KEY_LEVEL, level)
                }
            }
        }
    }

}
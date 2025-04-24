package com.semenovdev.addendtrainer.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.semenovdev.addendtrainer.R
import com.semenovdev.addendtrainer.databinding.FragmentGameBinding
import com.semenovdev.addendtrainer.domain.entity.GameResult
import com.semenovdev.addendtrainer.domain.entity.GameSettings
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

                tvOption1.text = question .options[0].toString()
                tvOption1.setOnClickListener {
                    viewModel.checkAnswer(question .options[0])
                }

                tvOption2.text = question .options[1].toString()
                tvOption2.setOnClickListener {
                    viewModel.checkAnswer(question .options[1])
                }

                tvOption3.text = question .options[2].toString()
                tvOption3.setOnClickListener {
                    viewModel.checkAnswer(question .options[2])
                }

                tvOption4.text = question .options[3].toString()
                tvOption4.setOnClickListener {
                    viewModel.checkAnswer(question .options[3])
                }

                tvOption5.text = question .options[4].toString()
                tvOption5.setOnClickListener {
                    viewModel.checkAnswer(question .options[4])
                }

                tvOption6.text = question .options[5].toString()
                tvOption6.setOnClickListener {
                    viewModel.checkAnswer(question .options[5])
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

            }
        }
    }

    private fun subscribeToGameResult() {
        viewModel.gameResult.observe(viewLifecycleOwner) {
            launchResultFragment(it)
        }
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
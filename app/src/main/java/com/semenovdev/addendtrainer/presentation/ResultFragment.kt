package com.semenovdev.addendtrainer.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.semenovdev.addendtrainer.R
import com.semenovdev.addendtrainer.databinding.FragmentResultBinding

class ResultFragment() : Fragment() {
    private val args by navArgs<ResultFragmentArgs>()

    private var _binding: FragmentResultBinding? = null
    private val binding: FragmentResultBinding
        get() = _binding ?: throw RuntimeException("FragmentResultBinding == null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentResultBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindViews()
        setListeners()
    }

    private fun bindViews() {
        binding.result = args.result
        with(binding) {
            var imageSource = if (args.result.isSuccess) {
                R.drawable.ic_glad
            } else {
                R.drawable.ic_sad
            }
            emojiResult.setImageResource(imageSource)

//            tvMinCount.text = String.format(
//                getString(R.string.min_quantity).toString(),
//                args.result.gameSettings.minCountRightAnswers.toString(),
//            )
//
//            tvScore.text = String.format(
//                getString(R.string.your_score).toString(),
//                args.result.correctAnswersCount.toString(),
//            )
//
//            tvMinPercentage.text = String.format(
//                getString(R.string.min_percentage).toString(),
//                args.result.gameSettings.minPercentRightAnswers.toString(),
//            )
//
//            tvPercentage.text = String.format(
//                getString(R.string.percentage).toString(),
//                args.result.percentage.toString(),
//            )
        }
    }

    private fun setListeners() {
        binding.buttonRetry.setOnClickListener {
            retryGame()
        }

        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    retryGame()
                }
            })
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun retryGame() {
        findNavController().popBackStack()
    }
}

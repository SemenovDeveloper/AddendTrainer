package com.semenovdev.addendtrainer.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.semenovdev.addendtrainer.R
import com.semenovdev.addendtrainer.databinding.FragmentLevelSelectionBinding
import com.semenovdev.addendtrainer.domain.entity.Level

class LevelSelectionFragment : Fragment() {
    private var _binding: FragmentLevelSelectionBinding? = null
    private val binding: FragmentLevelSelectionBinding
        get() = _binding ?: throw RuntimeException("FragmentLevelSelectionBinding == null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLevelSelectionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with (binding) {
            btnTest.setOnClickListener {
                launchGameFragment(Level.TEST)
            }

            btnEasy.setOnClickListener {
                launchGameFragment(Level.EASY)
            }

            btnNormal.setOnClickListener {
                launchGameFragment(Level.NORMAL)
            }

            btnHard.setOnClickListener {
                launchGameFragment(Level.HARD)
            }

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun launchGameFragment (level: Level) {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.main, GameFragment.newInstance(level))
            .addToBackStack(GameFragment.NAME)
            .commit()
    }

    companion object {
        const val NAME = "LevelSelectionFragment"

        fun newInstance (): LevelSelectionFragment {
           return LevelSelectionFragment()
        }
    }
}
package com.semenovdev.addendtrainer.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.semenovdev.addendtrainer.R
import com.semenovdev.addendtrainer.databinding.FragmentLevelSelectionBinding

class LevelSelectionFragment : Fragment() {
    private var _binding: FragmentLevelSelectionBinding? = null
    private val binding: FragmentLevelSelectionBinding
        get() = _binding ?: throw RuntimeException("FragmentLevelSelectionBinding == null")


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLevelSelectionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
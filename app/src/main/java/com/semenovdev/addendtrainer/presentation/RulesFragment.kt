package com.semenovdev.addendtrainer.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.semenovdev.addendtrainer.databinding.FragmentRulesBinding

class RulesFragment : Fragment() {
    private var _binding: FragmentRulesBinding? = null
    private val binding: FragmentRulesBinding
        get() = _binding ?: throw RuntimeException("FragmentRulesBinding == null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRulesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
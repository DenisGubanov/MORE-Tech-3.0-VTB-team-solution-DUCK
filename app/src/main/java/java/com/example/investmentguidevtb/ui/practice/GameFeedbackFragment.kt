package com.example.investmentguidevtb.ui.practice

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.example.investmentguidevtb.R
import com.example.investmentguidevtb.databinding.FragmentGameFeedbackBinding
import com.example.investmentguidevtb.ui.practice.models.GameEndFeedback
import com.example.investmentguidevtb.ui.theory.ArticleFragment


class GameFeedbackFragment : Fragment() {

    private lateinit var binding: FragmentGameFeedbackBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentGameFeedbackBinding.inflate(inflater)

        processArguments()

        binding.goToGameStart.setOnClickListener() {
            Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_container)
                .navigateUp()
        }

        return binding.root
    }

    private fun processArguments() {
        arguments?.getSerializable("gameEndFeedback")?.also {
            it as GameEndFeedback
            // вставка данных в binding
            Glide.with(this).load(it.goalResultPicture ?: "").into(binding.goalImage)
            binding.goalDescription.text = it.goalResultDescription

            Glide.with(this).load(it.riskResultDescription ?: "").into(binding.riskImage)
            binding.riskDescription.text = it.riskResultDescription

        }
    }

    companion object {
        fun createArguments(gameEndFeedback: GameEndFeedback) = Bundle().apply {
            putSerializable("gameEndFeedback", gameEndFeedback)
        }
    }
}
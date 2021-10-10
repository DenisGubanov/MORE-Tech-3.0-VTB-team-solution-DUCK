package com.example.investmentguidevtb.ui.practice

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.example.investmentguidevtb.R
import com.example.investmentguidevtb.databinding.FragmentStartGameBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlin.random.Random

@AndroidEntryPoint
class StartGameFragment : Fragment() {

    private lateinit var binding: FragmentStartGameBinding
    private val viewModel by viewModels<StartGameViewModel>()

    private var risk = -1f
    private var difficult = -1f
    private var inflation = 0.05f

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentStartGameBinding.inflate(inflater)

        binding.startGame.setOnClickListener(){
            Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_container)
                .navigate(R.id.action_startGameFragment_to_practiceFragment, PracticeFragment.createBundle(risk, difficult, inflation))
        }

        initObservers()

        viewModel.requestDataAndHandledStatus()

        return binding.root
    }

    private fun initObservers() {
        viewModel.requestToAddRiskData.observe(viewLifecycleOwner) {
            risk = if (it !in 0.0..1.0) Random.nextFloat() else it
        }

        viewModel.requestToAddDifficultData.observe(viewLifecycleOwner) {
            difficult = it

            if (difficult == -1f || difficult < 0) {
                val randIntDifficult = (0..3).random()
                difficult = randIntDifficult.toFloat()
            }

            inflation = (difficult + 5) / 100
        }

        viewModel.requestToNotifyAboutDataAvailable.observe(viewLifecycleOwner) {
            binding.startGame.isEnabled = true
            if (!it) binding.noSegmentationWarning.visibility = View.VISIBLE
        }
    }
}
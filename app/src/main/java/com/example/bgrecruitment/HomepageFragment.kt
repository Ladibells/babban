package com.example.bgrecruitment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.bgrecruitment.databinding.FragmentHomepageBinding


class HomepageFragment : Fragment(R.layout.fragment_homepage) {

    private lateinit var binding: FragmentHomepageBinding
    private lateinit var navController: NavController


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentHomepageBinding.bind(view)

        binding.btnInterview.setOnClickListener {
            findNavController().navigate(R.id.action_homepageFragment_to_interviewFragment)
        }

        binding.btnRecruitment.setOnClickListener {
            findNavController().navigate(R.id.action_homepageFragment_to_recruitmentFragment)
        }

        binding.btnReg.setOnClickListener {
            findNavController().navigate(R.id.action_homepageFragment_to_registrationFragment)
        }

        binding.btnTest.setOnClickListener {
            findNavController().navigate(R.id.action_homepageFragment_to_quizFragment)
        }

        binding.btnDetails.setOnClickListener {
            findNavController().navigate(R.id.action_homepageFragment_to_recruitedLeadersFragment)
        }

//        binding.btnRec.setOnClickListener {
//            findNavController().navigate(R.id.action_homepageFragment_to_rvRecruitment)
//        }
//        binding.btnFTest.setOnClickListener {
//            findNavController().navigate(R.id.action_homepageFragment_to_quizFragment)
//        }
//        binding.button2.setOnClickListener {
////            findNavController().navigate(R.id.action_homepageFragment_to_editLeaderFragment)
//        }

    }
}
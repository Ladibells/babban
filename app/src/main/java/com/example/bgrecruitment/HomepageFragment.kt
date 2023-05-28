package com.example.bgrecruitment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.bgrecruitment.databinding.FragmentHomepageBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomepageFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
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
            findNavController().navigate(R.id.action_homepageFragment_to_testFragment)
        }

        binding.btnDetails.setOnClickListener {
            findNavController().navigate(R.id.action_homepageFragment_to_recruitedLeadersFragment)
        }

        binding.btnRec.setOnClickListener {
            findNavController().navigate(R.id.action_homepageFragment_to_rvRecruitment)
        }
        binding.btnFTest.setOnClickListener {
            findNavController().navigate(R.id.action_homepageFragment_to_quizFragment)
        }
        binding.button2.setOnClickListener {
            findNavController().navigate(R.id.action_homepageFragment_to_editLeaderFragment)
        }

    }
}
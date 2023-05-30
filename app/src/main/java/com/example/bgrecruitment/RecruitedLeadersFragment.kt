package com.example.bgrecruitment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bgrecruitment.adapter.RecruitmentLeaderAdapter
import com.example.bgrecruitment.data.viewmodel.RecruitmentViewModel
import com.example.bgrecruitment.data.viewmodel.UserViewModelFactory
import com.example.bgrecruitment.databinding.FragmentRecruitedLeadersBinding
import com.example.bgrecruitment.db.UserDatabase
import com.example.bgrecruitment.data.Recruitment
import com.example.bgrecruitment.data.viewmodel.UserViewModel
import com.example.bgrecruitment.helpers.PreferenceHelper
import com.google.android.material.snackbar.Snackbar


class RecruitedLeadersFragment : Fragment(), RecruitmentLeaderAdapter.OnItemClickListener {

    private var _binding: FragmentRecruitedLeadersBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: RecruitmentViewModel
    private lateinit var userViewModel: UserViewModel
    private lateinit var adapter: RecruitmentLeaderAdapter
    private lateinit var preferenceHelper: PreferenceHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentRecruitedLeadersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView: RecyclerView = binding.recyclerViewLeaders
        val dao = UserDatabase.getInstance(requireContext()).userDao()
        val recDao = UserDatabase.getInstance(requireContext()).recDao()
        val qustionDao = UserDatabase.getInstance(requireContext()).questionDao()
        val userResponseDao = UserDatabase.getInstance(requireContext()).userResponseDao()
        val recruitmentDao = UserDatabase.getInstance(requireContext()).recruitmentDao()
        val factory = UserViewModelFactory(dao, recDao, qustionDao, userResponseDao, recruitmentDao)
        viewModel = ViewModelProvider(this, factory).get(RecruitmentViewModel::class.java)
        userViewModel = ViewModelProvider(this, factory).get(UserViewModel::class.java)

        // Initialize the PreferenceHelper
        preferenceHelper = PreferenceHelper(requireContext())

        adapter = RecruitmentLeaderAdapter(this, preferenceHelper, findNavController())
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())



        // Observe the recruitment list from the ViewModel
        viewModel.getAllRecruitments().observe(viewLifecycleOwner) { recruitmentList ->
            adapter.setData(recruitmentList)
        }

//        adapter = RecruitmentLeaderAdapter(object : RecruitmentLeaderAdapter.OnItemClickListener{
//            override fun onItemClick(recruitment: Recruitment) {
//                if (recruitment.scheduledForTest) {
//                    // Show a Snackbar indicating that the item is already scheduled for a test
//                    Snackbar.make(requireView(), "Already scheduled for test", Snackbar.LENGTH_SHORT).show()
//                } else {
//                    // Handle the item click event
////                    recruitment.scheduledForTest = !recruitment.scheduledForTest
//                    recruitment.scheduledForTest = true
//                    Toast.makeText(requireContext(), "Test Scheduled: ${recruitment.scheduledForTest}", Toast.LENGTH_SHORT).show()
//
//                    // Update the recruitment data in the Room database
//                    userViewModel.updateRec(recruitment)
//
//                    // Update the adapter to reflect the changes
//                    adapter.notifyDataSetChanged()
//                }
//
//            }
//
//
//        })

    }


    override fun onItemClick(recruitment: Recruitment) {
        if (recruitment.scheduledForTest) {
            // Show a Snackbar indicating that the item is already scheduled for a test
            Snackbar.make(requireView(), "Already scheduled for test", Snackbar.LENGTH_SHORT).show()
        } else {
            // Handle the item click event
//            recruitment.scheduledForTest = false
//            Toast.makeText(requireContext(), "Test Scheduled: ${recruitment.scheduledForTest}", Toast.LENGTH_SHORT).show()


            val updatedRecruitment = recruitment.copy(scheduledForTest = !recruitment.scheduledForTest)
//            Toast.makeText(requireContext(), "Test Scheduled: ${updatedRecruitment.scheduledForTest}", Toast.LENGTH_SHORT).show()

            // Update the recruitment data in the Room database
            userViewModel.updateRec(recruitment)

            // Update the adapter to reflect the changes
            adapter.notifyDataSetChanged()

            // Navigate to the QuizFragment

        }
        // Navigate to RecruitedLeaderDetailsFragment and pass the recruitment details
        val action = RecruitedLeadersFragmentDirections.actionRecruitedLeadersFragmentToRecruitedLeaderDetailsFragment(recruitment)
        findNavController().navigate(action)
//        val bundle = bundleOf("recruitment" to recruitment)
//        findNavController().navigate(R.id.action_recruitedLeaderDetailsFragment_to_editLeaderFragment, bundle)


//        findNavController().navigate(R.id.action_recruitedLeadersFragment_to_recruitedLeaderDetailsFragment)
    }

    override fun onTakeTestClick(recruitment: Recruitment) {
        // Navigate to the QuizFragment
        val action = RecruitedLeadersFragmentDirections.actionRecruitedLeadersFragmentToQuizFragment(recruitment.id.toString())
        findNavController().navigate(action)
    }

//    override fun onItemClick(recruitment: Recruitment) {
//        findNavController().navigate(R.id.action_recruitedLeadersFragment_to_recruitedLeaderDetailsFragment)
//    }

}



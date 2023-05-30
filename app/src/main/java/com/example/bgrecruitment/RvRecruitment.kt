package com.example.bgrecruitment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bgrecruitment.adapter.RecruitmentAdapter
import com.example.bgrecruitment.data.viewmodel.UserViewModel
import com.example.bgrecruitment.data.viewmodel.UserViewModelFactory
import com.example.bgrecruitment.databinding.FragmentRvRecruitmentBinding
import com.example.bgrecruitment.db.UserDatabase


class RvRecruitment : Fragment(R.layout.fragment_rv_recruitment) {

    private lateinit var binding: FragmentRvRecruitmentBinding
    private lateinit var adapter: RecruitmentAdapter
    private lateinit var viewModel: UserViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentRvRecruitmentBinding.bind(view)

        // Initialize your database, view model, and retrieve the list of recruitment items
        val recyclerView: RecyclerView = binding.rvRecruitment
        val dao = UserDatabase.getInstance(requireContext()).userDao()
        val recDao = UserDatabase.getInstance(requireContext()).recDao()
        val qustionDao = UserDatabase.getInstance(requireContext()).questionDao()
        val userResponseDao = UserDatabase.getInstance(requireContext()).userResponseDao()
        val recruitmentDao = UserDatabase.getInstance(requireContext()).recruitmentDao()
        val factory = UserViewModelFactory(dao, recDao, qustionDao, userResponseDao, recruitmentDao)
        viewModel = ViewModelProvider(this, factory).get(UserViewModel::class.java)


        // Set up the RecyclerView adapter
        adapter = RecruitmentAdapter(false)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Observe the recruitment list from the ViewModel
        viewModel.getAllRecruitments().observe(viewLifecycleOwner) { recruitmentList ->
            adapter.setData(recruitmentList)
        }

        // Set item click listener for editing
        adapter.setOnItemClickListener(object : RecruitmentAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                // Handle item click event
                val recruitment = adapter.getItem(position)
                val isEditable = false// Determine the editability based on your logic

                // Update the recruitment object with the isEditable flag
                recruitment.scheduledForTest = isEditable

                // Notify the adapter that the item has changed
                adapter.notifyItemChanged(position)
                // Handle item click event for editing
                findNavController().navigate(R.id.action_rvRecruitment_to_recruitmentFragment)
            }

            override fun onEditClick(position: Int) {
                // Handle edit click event
                val recruitment = adapter.getItem(position)
                viewModel.editRecruitment(recruitment)
            }

            override fun onScheduleToggle(position: Int, isScheduled: Boolean) {
                val recruitment = adapter.getItem(position)
                // Handle toggle schedule event
                if (recruitment.scheduledForTest) {
                    // Update the isScheduled flag in the model
                    recruitment.isScheduled = isScheduled
                    // Call the ViewModel method to update the recruitment in the database
                    viewModel.updateRec(recruitment)
                }
            }
        })
    }

}

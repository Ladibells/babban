package com.example.bgrecruitment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.bgrecruitment.adapter.RecruitmentAdapter
import com.example.bgrecruitment.data.viewmodel.UserViewModel
import com.example.bgrecruitment.data.viewmodel.UserViewModelFactory
import com.example.bgrecruitment.databinding.FragmentScheduleBinding
import com.example.bgrecruitment.db.UserDatabase


class ScheduleFragment : Fragment() {
    private lateinit var binding: FragmentScheduleBinding
    private lateinit var adapter: RecruitmentAdapter.ScheduleListAdapter
    private lateinit var viewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_schedule, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentScheduleBinding.bind(view)

        // Initialize your database, view model, and retrieve the list of scheduled recruits
        val recyclerView: RecyclerView = binding.rvRecruitment
        val dao = UserDatabase.getInstance(requireContext()).userDao()
        val recDao = UserDatabase.getInstance(requireContext()).recDao()
        val qustionDao = UserDatabase.getInstance(requireContext()).questionDao()
        val userResponseDao = UserDatabase.getInstance(requireContext()).userResponseDao()
        val recruitmentDao = UserDatabase.getInstance(requireContext()).recruitmentDao()
        val factory = UserViewModelFactory(dao, recDao, qustionDao, userResponseDao, recruitmentDao)
        viewModel = ViewModelProvider(this, factory).get(UserViewModel::class.java)

        // Set up the RecyclerView adapter
        adapter = RecruitmentAdapter.ScheduleListAdapter()
        recyclerView.adapter = adapter

        // Observe the scheduled recruits list from the ViewModel
        viewModel.getScheduledRecruitments().observe(viewLifecycleOwner) { scheduledRecruits ->
            adapter.setData(scheduledRecruits)
        }
    }

}
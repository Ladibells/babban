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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)




        // Create an instance of the adapter
//        val adapter = RecruitmentAdapter(recruitmentList, object : RecruitmentAdapter.OnItemClickListener {
//            override fun onItemClick(position: Int) {
//                // Handle item click event here
//                val clickedRecruitment = recruitmentList[position]
//                // Perform the desired action with the clicked item, such as editing or deleting
//            }
//        })


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentRvRecruitmentBinding.bind(view)

        // Initialize your database, view model, and retrieve the list of recruitment items
        val recyclerView: RecyclerView = binding.rvRecruitment
        val dao = UserDatabase.getInstance(requireContext()).userDao()
        val recDao = UserDatabase.getInstance(requireContext()).recDao()
        val qustionDao = UserDatabase.getInstance(requireContext()).questionDao()
        val userResponseDao = UserDatabase.getInstance(requireContext()).userResponseDao()
        val factory = UserViewModelFactory(dao, recDao, qustionDao, userResponseDao)
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
                    recruitment.isEditable = isEditable

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
                if (recruitment.isEditable) {
                    // Update the isScheduled flag in the model
                    recruitment.isScheduled = isScheduled
                    // Call the ViewModel method to update the recruitment in the database
                    viewModel.updateRec(recruitment)
                }
            }
        })

        // Retrieve the list of recruitment items from the view model and update the adapter
//        viewModel.getAllRecruitments().observe(viewLifecycleOwner) { recruitmentList ->
//            adapter.setData(recruitmentList)
//        }

//            recyclerView.adapter = adapter
//            recyclerView.layoutManager = LinearLayoutManager(requireContext())

//        val repository = UserRepository()

//        viewModel = ViewModelProvider(this, factory).get(UserViewModel::class.java)
//        viewModel.recruitments.observe(viewLifecycleOwner) {
//            adapter = RecruitmentAdapter(it, this)
//            recyclerView.adapter = adapter
//            recyclerView.layoutManager = LinearLayoutManager(requireContext())
//        }


//        fun getUserInput(): Recruitment {
//            // Retrieve user input and create a Recruitment object
//            // Return the created object
//        }

        // Example of adding a new recruitment item based on user input
//        fun addRecruitmentItem() {
//            val newRecruitment = getUserInput()
//            recruitmentList.add(newRecruitment)
//            adapter.notifyDataSetChanged() // Notify the adapter that the data has changed
//        }
//
//        val adapter = RecruitmentAdapter(recruitmentList, object : RecruitmentAdapter.OnItemClickListener {
//            override fun onItemClick(position: Int) {
//                // Handle item click event here
//                val clickedRecruitment = recruitmentList[position]
//                // Perform the desired action with the clicked item, such as editing or deleting
//            }
//        })

    }

//    override fun cardClick() {
//        findNavController().navigate(R.id.action_rvRecruitment_to_recruitmentFragment)
//    }


}
package com.example.bgrecruitment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bgrecruitment.adapter.RecruitmentAdapter
import com.example.bgrecruitment.data.viewmodel.UserViewModel
import com.example.bgrecruitment.data.viewmodel.UserViewModelFactory
import com.example.bgrecruitment.databinding.FragmentRecruitmentBinding
import com.example.bgrecruitment.databinding.FragmentRvRecruitmentBinding
import com.example.bgrecruitment.db.UserDatabase


class RvRecruitment : Fragment(R.layout.fragment_rv_recruitment), RecruitmentAdapter.OnCardClick {

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

        val recyclerView: RecyclerView = binding.rvRecruitment
        val dao = UserDatabase.getInstance(requireContext()).userDao()
        val recDao = UserDatabase.getInstance(requireContext()).recDao()
        val factory = UserViewModelFactory(dao, recDao)





        viewModel = ViewModelProvider(this, factory).get(UserViewModel::class.java)
        viewModel.recruitments.observe(viewLifecycleOwner) {
            adapter = RecruitmentAdapter(it, this)
            recyclerView.adapter = adapter
            recyclerView.layoutManager = LinearLayoutManager(requireContext())
        }


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

    override fun cardClick() {
        findNavController().navigate(R.id.action_rvRecruitment_to_recruitmentFragment)
    }


}
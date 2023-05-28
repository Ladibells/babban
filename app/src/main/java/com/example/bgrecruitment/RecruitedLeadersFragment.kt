package com.example.bgrecruitment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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


class RecruitedLeadersFragment : Fragment(), RecruitmentLeaderAdapter.OnItemClickListener {

    private var _binding: FragmentRecruitedLeadersBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: RecruitmentViewModel
    private lateinit var adapter: RecruitmentLeaderAdapter

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

        adapter = RecruitmentLeaderAdapter(this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Observe the recruitment list from the ViewModel
        viewModel.getAllRecruitments().observe(viewLifecycleOwner) { recruitmentList ->
            adapter.setData(recruitmentList)
        }

    }

    override fun onItemClick(recruitment: Recruitment) {
        // Navigate to RecruitedLeaderDetailsFragment and pass the recruitment details
        val action = RecruitedLeadersFragmentDirections.actionRecruitedLeadersFragmentToRecruitedLeaderDetailsFragment(recruitment)
        findNavController().navigate(action)
//        val bundle = bundleOf("recruitment" to recruitment)
//        findNavController().navigate(R.id.action_recruitedLeaderDetailsFragment_to_editLeaderFragment, bundle)


//        findNavController().navigate(R.id.action_recruitedLeadersFragment_to_recruitedLeaderDetailsFragment)
    }

//    override fun onItemClick(recruitment: Recruitment) {
//        findNavController().navigate(R.id.action_recruitedLeadersFragment_to_recruitedLeaderDetailsFragment)
//    }

}
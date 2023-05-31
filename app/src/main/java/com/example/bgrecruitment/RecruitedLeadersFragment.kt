package com.example.bgrecruitment

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
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
import com.example.bgrecruitment.utils.hidekeyboard
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.transition.MaterialElevationScale
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class RecruitedLeadersFragment : Fragment(), RecruitmentLeaderAdapter.OnItemClickListener {

    private var _binding: FragmentRecruitedLeadersBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: RecruitmentViewModel
    private lateinit var userViewModel: UserViewModel
    private lateinit var adapter: RecruitmentLeaderAdapter
    private lateinit var preferenceHelper: PreferenceHelper
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentRecruitedLeadersBinding.inflate(inflater, container, false)

        exitTransition = MaterialElevationScale(false).apply {
            duration = 350
        }

        enterTransition = MaterialElevationScale(true).apply {
            duration = 350
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val activity = activity as MainActivity
        navController = Navigation.findNavController(view)
        requireView().hidekeyboard()
        CoroutineScope(Dispatchers.Main).launch {
            delay(10)
            activity.window.statusBarColor = Color.WHITE
            activity.window.clearFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            activity.window.statusBarColor = Color.parseColor("#9E9D9D")
        }

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


    }


    override fun onItemClick(recruitment: Recruitment) {
        if (recruitment.scheduledForTest) {
            // Show a Snackbar indicating that the item is already scheduled for a test
            Snackbar.make(requireView(), "Already scheduled for test", Snackbar.LENGTH_SHORT).show()
        } else {


            val updatedRecruitment = recruitment.copy(scheduledForTest = !recruitment.scheduledForTest)

            // Update the recruitment data in the Room database
            userViewModel.updateRec(recruitment)

            // Save the updated switch state in the preference
            preferenceHelper.saveSwitchState(updatedRecruitment.id.toString(), updatedRecruitment.scheduledForTest)


            // Update the adapter to reflect the changes
            adapter.notifyDataSetChanged()

            // Navigate to the QuizFragment

        }
        // Navigate to RecruitedLeaderDetailsFragment and pass the recruitment details
        val action = RecruitedLeadersFragmentDirections.actionRecruitedLeadersFragmentToRecruitedLeaderDetailsFragment(recruitment)
        findNavController().navigate(action)


   }

    override fun onTakeTestClick(recruitment: Recruitment) {
        // Navigate to the QuizFragment
        val action = RecruitedLeadersFragmentDirections.actionRecruitedLeadersFragmentToQuizFragment(recruitment.id.toString())
        findNavController().navigate(action)
    }

}



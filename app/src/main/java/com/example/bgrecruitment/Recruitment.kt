package com.example.bgrecruitment

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.bgrecruitment.data.Recruitment
import com.example.bgrecruitment.data.viewmodel.UserViewModel
import com.example.bgrecruitment.databinding.FragmentRecruitmentBinding
import java.io.File
import java.text.SimpleDateFormat
import java.util.*


class Recruitment : Fragment() {

    private lateinit var binding: FragmentRecruitmentBinding
    private var datePickerDialog: DatePickerDialog? = null
    private lateinit var viewModel: UserViewModel
    private lateinit var navController: NavController
    private var selectedState = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentRecruitmentBinding.bind(view)

        val sex = resources.getStringArray(R.array.Sex)
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.list_item, sex)
        binding.dropDownSex.setAdapter(arrayAdapter)

//        val sex = listOf("Male", "female")
//        val adapterSex = ArrayAdapter(requireContext(), R.layout.list_item, sex)
//        binding.dropDownSex.setAdapter(adapterSex)

        val state = resources.getStringArray(R.array.State)
        val adapterState = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, state)
        binding.dropDownState.setAdapter(adapterState)

//        binding.dropDownState.setOnItemClickListener { parent, view, position, id ->
//            selectedState = state[position]
//            //selectedState = parent?.selectedItem as String
//            Toast.makeText(
//                requireContext(),
//                "Selected state is: $selectedState",
//                Toast.LENGTH_LONG
//            ).show()
//        }

        val idType = resources.getStringArray(R.array.idType)
        val adapterIDType = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, idType)
        binding.dropDownIDType.setAdapter(adapterIDType)

        // take year and month and day from calendar
        val calendar = Calendar.getInstance()
        val dateSetListener = object : DatePickerDialog.OnDateSetListener {
            override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, month)
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateDateInView(calendar)
            }
        }

        binding.etDOB.setOnClickListener {
            openDatePicker()
        }

//        binding.etImage.setOnClickListener { showFileChooser() }
    }

//    private fun showFileChooser() {
//        val intent = Intent(Intent.ACTION_GET_CONTENT)
//        intent.type = "*Image/*"
//        intent.addCategory(Intent.CATEGORY_OPENABLE)
//        try {
//            startActivityForResult(Intent.createChooser(intent, "Select a file"), 100)
//        } catch (exception: Exception) {
//            Toast.makeText(requireContext(), "Please install a file manager", Toast.LENGTH_SHORT)
//                .show()
//        }
//    }
//
//    @Deprecated("Deprecated in Java")
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        if (requestCode == 100 && resultCode == Activity.RESULT_OK && data != null) {
//            val uri: Uri? = data?.data
//            val path: String = uri?.path.toString()
//            val file = File(path)
//
//            //val text = "Path: $path File name: ${file.name}".trimIndent()
//            val text = "${file.name}".trimIndent()
//            val editableText = Editable.Factory.getInstance().newEditable(text)
//            binding.etImage.setText(editableText) // no error
//
//        }

//        super.onActivityResult(requestCode, resultCode, data)
//    }

    private fun updateDateInView(calendar: Calendar) {
        val format = "dd-mm-yyyy"
        val sdf = SimpleDateFormat(format, Locale.getDefault())
        binding.etDOB.setText(sdf.format(calendar.time))
    }

    private fun openDatePicker() {
        val calendar = Calendar.getInstance()
        val day = calendar[Calendar.DAY_OF_MONTH]
        val month = calendar[Calendar.MONTH]
        val year = calendar[Calendar.YEAR]

        datePickerDialog = DatePickerDialog(
            requireContext(),
            { View, year, monthOfYear, dayOfMonth ->
                val month = monthOfYear + 1
                var strMonth = "" + month
                var strDayOfMonth = "" + dayOfMonth
                if (month < 10) {
                    strMonth = "0$strMonth"
                }
                if (dayOfMonth < 10) {
                    strDayOfMonth = "0$strDayOfMonth"
                }
                val date = "$strDayOfMonth-$strMonth-$year"
                binding.etDOB!!.setText(date)
            }, year, month, day
        )
        datePickerDialog!!.setTitle("Select Date")
        datePickerDialog!!.show()
    }

//    private fun insertDataToDatabase() {
//
//        val name = binding.etName.text.toString()
//        val phoneNumber = binding.etNo.text.toString()
//        val sex = binding.dropDownSex.text
//        val dob = binding.etDOB.text
//        val bvn = binding.etBVN.text.toString()
//        val nin = binding.etNIN.text.toString()
//        val state = binding.dropDownState.text
//        val lga = binding.etLGA.text.toString()
//        val hub = binding.etHub.text.toString()
//        val id = binding.etID.text.toString()
//        val idType = binding.dropDownIDType.text
//        //val image = binding.etImage.text
//
//        if (name.isNullOrEmpty() && phoneNumber.isNullOrEmpty() && sex.isNullOrEmpty() && dob.isNullOrEmpty()
//            && bvn.isNullOrEmpty() && nin.isNullOrEmpty() && state.isNullOrEmpty() && lga.isNullOrEmpty()
//            && hub.isNullOrEmpty() && id.isNullOrEmpty() && idType.isNullOrEmpty()
//        ) {
//            Toast.makeText(getActivity(), "please fill out all fields", Toast.LENGTH_SHORT).show()
//        } else {
//
//            // create user object
//            val recruitment = Recruitment(
//                0,
//                name,
//                phoneNumber,
//                sex,
//                dob,
//                bvn,
//                nin,
//                state,
//                lga,
//                hub,
//                id,
//                idType
//            )
//            // add data to database
//            viewModel.insertRec(recruitment)
//            Toast.makeText(activity, "Successfully added user $name", Toast.LENGTH_SHORT).show()
//            findNavController().navigate((R.id.action_signUp_to_login))
//        }
//    }

//    private fun inputCheck(name: String, phoneNumber: Int, sex: Editable, dob: Editable, bvn: Int, nin: String, state: Editable, lga: Int
//    hub: String, id: String, idType: Editable, image: Editable
//    ): Boolean {
//        return !(TextUtils.isEmpty(name) && phoneNumber )
//        //return !(TextUtils.isEmpty(name) && binding.etNo.isEmpty() && TextUtils.isEmpty(sex) && TextUtils.isEmpty(confirmPassword))
//    }

    private fun clearInput() {
        binding.apply {
            etName.setText("")
            etNo.setText("")
            dropDownSex.setText("")
            etDOB.setText("")
            etBVN.setText("")
            etNIN.setText("")
            dropDownState.setText("")
            etLGA.setText("")
            etHub.setText("")
            etID.setText("")
            dropDownIDType.setText("")
            etImage.setImageURI(null)
        }
    }


}
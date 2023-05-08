package com.example.bgrecruitment




import android.app.Activity.RESULT_OK
import android.app.DatePickerDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast.*
import com.example.bgrecruitment.databinding.FragmentRegistrationBinding
import java.io.File
import java.text.SimpleDateFormat
import java.util.*


class RegistrationFragment : Fragment() {

    private lateinit var binding: FragmentRegistrationBinding
    private var datePickerDialog: DatePickerDialog ?= null




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_registration, container, false)

    }

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        binding = FragmentRegistrationBinding.bind(view)
//
//        val sex = resources.getStringArray(R.array.Sex)
//        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.list_item, sex)
//        binding.dropDownSex.setAdapter(arrayAdapter)
//
////        val sex = listOf("Male", "female")
////        val adapterSex = ArrayAdapter(requireContext(), R.layout.list_item, sex)
////        binding.dropDownSex.setAdapter(adapterSex)
//
//        val state = resources.getStringArray(R.array.State)
//        val adapterState = ArrayAdapter(requireContext(), R.layout.state_list, state)
//        binding.dropDownState.setAdapter(adapterState)
//
//        val idType = resources.getStringArray(R.array.idType)
//        val adapterIDType = ArrayAdapter(requireContext(), R.layout.id_list, idType)
//        binding.dropDownIDType.setAdapter(adapterIDType)
//
//        // take year and month and day from calendar
//        val calendar = Calendar.getInstance()
//        val dateSetListener = object: DatePickerDialog.OnDateSetListener {
//            override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
//                calendar.set(Calendar.YEAR, year)
//                calendar.set(Calendar.MONTH, month)
//                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
//                updateDateInView(calendar)
//            }
//        }
//
////        binding.etDOB.setOnClickListener(object : View.OnClickListener{
////            override fun onClick(v: View?) {
////                DatePickerDialog(requireContext(), dateSetListener,
////                calendar.get(Calendar.YEAR),
////                    calendar.get(Calendar.MONTH),
////                    calendar.get(Calendar.DAY_OF_MONTH)
////                    ).show()
////            }
////        })
//
//        binding.etDOB.setOnClickListener {
//            openDatePicker()
//        }
//
//        binding.etImage.setOnClickListener { showFileChooser() }
//
////        val year = calendar.get(Calendar.YEAR)
////        val month = calendar.get(Calendar.MONTH)
////        val day = calendar.get(Calendar.DAY_OF_MONTH)
//
//        //on click, open date picker
////        binding.etDOB.setOnClickListener {
////            DatePickerDialog(this,
////                { view, year, month, dayOfMonth ->
////                //binding.etDOB.text = "$day/$(month+1)/$year"
////            }, day, month, year).show()
////        }
//
////        val datePicker = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
////            val year = calendar.get(Calendar.YEAR)
////            val month = calendar.get(Calendar.MONTH)
////            val day = calendar.get(Calendar.DAY_OF_MONTH)
////            updateLable(calendar)
////        }
////
////        binding.etDOB.setOnClickListener {
////            DatePickerDialog(this, datePicker, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show()
////        }
//
//    }

    private fun showFileChooser() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "*/*"
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        try {
            startActivityForResult(Intent.createChooser(intent, "Select a file"), 100)
        } catch (exception: Exception) {
            makeText( requireContext(), "Please install a file manager", LENGTH_SHORT).show()
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 100 && resultCode == RESULT_OK && data != null) {
            val uri: Uri? = data?.data
            val path: String = uri?.path.toString()
            val file = File(path)

            //val text = "Path: $path File name: ${file.name}".trimIndent()
            val text = "${file.name}".trimIndent()
            val editableText = Editable.Factory.getInstance().newEditable(text)
            binding.etImage.setText(editableText) // no error

        }

        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun updateDateInView(calendar: Calendar) {
        val format = "dd-mm-yyyy"
        val sdf = SimpleDateFormat(format, Locale.getDefault())
        binding.etDOB.setText(sdf.format(calendar.time))
    }

//    private fun launchPicker() {
//        val mimeTypes = mutableListOf("application/pdf", "image/*", "video/*")
//        val config = Config(BuildConfig.API_KEY)
//    }


    private fun openDatePicker() {
        val calendar = Calendar.getInstance()
        val day = calendar[Calendar.DAY_OF_MONTH]
        val month = calendar[Calendar.MONTH]
        val year = calendar[Calendar.YEAR]

        datePickerDialog = DatePickerDialog(requireContext(),
            {View, year, monthOfYear, dayOfMonth ->
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

}
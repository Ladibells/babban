package com.example.bgrecruitment.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioButton
import android.widget.Switch
import androidx.navigation.NavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bgrecruitment.data.Question
import com.example.bgrecruitment.data.Recruitment
import com.example.bgrecruitment.data.User
import com.example.bgrecruitment.databinding.ItemQuestionBinding
import com.example.bgrecruitment.databinding.ListItemLayoutBinding
import com.example.bgrecruitment.databinding.RvRecruitmentLayoutBinding
import com.example.bgrecruitment.databinding.ScheduledRecruitItemBinding
import com.example.bgrecruitment.databinding.UserDetailsListBinding
import com.example.bgrecruitment.helpers.PreferenceHelper
import com.google.android.material.card.MaterialCardView
import java.lang.String
import java.util.*
import kotlin.Boolean
import kotlin.Int
import kotlin.Unit
import kotlin.apply


class UserAdapter : RecyclerView.Adapter<UserAdapter.MyViewHolder>() {

    private var userList = emptyList<User>()

//    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
//
//    }

    class MyViewHolder(val binding: UserDetailsListBinding) : RecyclerView.ViewHolder(binding.root)

//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserAdapter.MyViewHolder {
//        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.user_details_list, parent, false))
//    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserAdapter.MyViewHolder {
        return MyViewHolder(
            UserDetailsListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: UserAdapter.MyViewHolder, position: Int) {
        val currentItem = userList[position]
        holder.binding.idNo.text = currentItem.id.toString()
        holder.binding.name.text = currentItem.name.toString()
        holder.binding.mail.text = currentItem.email.toString()
        holder.binding.password.text = currentItem.password.toString()

    }

    override fun getItemCount(): Int {
        return userList.size
    }


    fun setData(newUserList: List<User>) {
        val diffUtil = UserDiffUtil(userList, newUserList)
        val diffResults = DiffUtil.calculateDiff(diffUtil)
        userList = newUserList
        diffResults.dispatchUpdatesTo(this)
    }
}

//class RecruitmentAdapter(): RecyclerView.Adapter<RecruitmentAdapter.RecruitmentViewHolder>() {
//
//     private var recList = emptyList<Recruitment>()
//
//    class RecruitmentViewHolder(val binding: RvRecruitmentLayoutBinding): RecyclerView.ViewHolder(binding.root)
//
////    private val differCallback = object : DiffUtil.ItemCallback<User>() {
////        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
////            return oldItem.id == newItem.id
////        }
////
////        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
////            return oldItem == newItem
////        }
////
////    }
//
////    val differ = AsyncListDiffer(this, differCallback)
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecruitmentAdapter.RecruitmentViewHolder {
//        return RecruitmentAdapter.RecruitmentViewHolder(
//            RvRecruitmentLayoutBinding.inflate(
//                LayoutInflater.from(parent.context),
//                parent,
//                false
//            )
//
//        )
//    }
//
//    override fun getItemCount(): Int {
//        return recList.size
//    }
//
//    override fun onBindViewHolder(holder: RecruitmentViewHolder, position: Int) {
//        holder.binding.root.setOnClickListener {
//
//        }
//        val currentItem = recList[position]
//        holder.binding.idNo.text = currentItem.id.toString()
//        holder.binding.fname.text = currentItem.Name.toString()
//        holder.binding.idNo.text = currentItem.PhoneNumber.toString()
//        holder.binding.DOB.text = currentItem.DOB.toString()
//        holder.binding.sex.text = currentItem.Sex.toString()
//        holder.binding.bvn.text = currentItem.BVN.toString()
//        holder.binding.nin.text = currentItem.NIN.toString()
//        holder.binding.state.text = currentItem.State.toString()
//        holder.binding.LGA.text = currentItem.LGA.toString()
//        holder.binding.hub.text = currentItem.Hub.toString()
//        holder.binding.govID.text = currentItem.GovID.toString()
//        holder.binding.idType.text = currentItem.IdType.toString()
//    }
//
//    fun setData(recruitment: List<Recruitment>) {
//        this.recList = recruitment
//        notifyDataSetChanged()
//    }
////    interface OnCardClick{
////        fun cardClick()
////    }
//}


//class RecruitmentAdapter(
//    private val recruitmentList: List<Recruitment>,
//    private val listener: OnItemClickListener
//) : RecyclerView.Adapter<RecruitmentAdapter.RecruitmentViewHolder>() {
//
//    interface OnItemClickListener {
//        fun onItemClick(position: Int)
//    }
//
//        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecruitmentViewHolder {
//        val binding = RvRecruitmentLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
//        return RecruitmentViewHolder(binding)
//    }
//
//    override fun onBindViewHolder(holder: RecruitmentViewHolder, position: Int) {
//        val currentRecruitment = recruitmentList[position]
//        holder.bind(currentRecruitment)
//    }
//
//    override fun getItemCount(): Int {
//        return recruitmentList.size
//    }
//
//    inner class RecruitmentViewHolder(private val binding: RvRecruitmentLayoutBinding) :
//        RecyclerView.ViewHolder(binding.root), View.OnClickListener {
//
//        init {
//            itemView.setOnClickListener(this)
//        }
//
//        fun bind(recruitment: Recruitment) {
//            binding.fname.text = "Full Name: ${recruitment.Name}"
//            binding.idNo.text = "Phone Number: ${recruitment.PhoneNumber}"
//            binding.sex.text = "Sex: ${recruitment.Sex}"
//            binding.DOB.text = "Date of Birth: ${recruitment.DOB}"
//            binding.bvn.text = "BVN: ${recruitment.BVN}"
//            binding.nin.text = "NIN: ${recruitment.NIN}"
//            binding.state.text = "State: ${recruitment.State}"
//            binding.LGA.text = "LGA: ${recruitment.LGA}"
//            binding.hub.text = "Hub: ${recruitment.Hub}"
//            binding.govID.text = "ID: ${recruitment.GovID}"
//            binding.idType.text = "ID Type: ${recruitment.IdType}"
//        }
//
//        override fun onClick(v: View?) {
//            val position = adapterPosition
//            if (position != RecyclerView.NO_POSITION) {
//                listener.onItemClick(position)
//            }
//        }
//    }
//}

//class RecruitmentAdapter(private var recList: List<Recruitment>, private val onItemClickListener: OnItemClickListener) :
//    RecyclerView.Adapter<RecruitmentAdapter.RecruitmentViewHolder>() {
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecruitmentViewHolder {
//        val binding = RvRecruitmentLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
//        return RecruitmentViewHolder(binding)
//    }
//
//    override fun getItemCount(): Int {
//        return recList.size
//    }
//
//    override fun onBindViewHolder(holder: RecruitmentViewHolder, position: Int) {
//        val currentItem = recList[position]
//        holder.bind(currentItem)
//        holder.itemView.setOnClickListener {
//            onItemClickListener.onItemClick(position)
//        }
//    }
//
//    fun setData(recruitment: List<Recruitment>) {
//        recList = recruitment
//        notifyDataSetChanged()
//    }
//
//    interface OnItemClickListener {
//        fun onItemClick(position: Int)
//    }
//
//    inner class RecruitmentViewHolder(private val binding: RvRecruitmentLayoutBinding) :
//        RecyclerView.ViewHolder(binding.root) {
//
//        fun bind(recruitment: Recruitment) {
//            binding.idNo.text = recruitment.id.toString()
//            binding.fname.text = recruitment.Name.toString()
//            binding.idNo.text = recruitment.PhoneNumber.toString()
//            binding.DOB.text = recruitment.DOB.toString()
//            binding.sex.text = recruitment.Sex.toString()
//            binding.bvn.text = recruitment.BVN.toString()
//            binding.nin.text = recruitment.NIN.toString()
//            binding.state.text = recruitment.State.toString()
//            binding.LGA.text = recruitment.LGA.toString()
//            binding.hub.text = recruitment.Hub.toString()
//            binding.govID.text = recruitment.GovID.toString()
//            binding.idType.text = recruitment.IdType.toString()
//        }
//    }
//}


//class RecruitmentAdapter(
//    private var recList: List<Recruitment> = emptyList(),
//    private var onItemClickListener: OnItemClickListener? = null
//) : RecyclerView.Adapter<RecruitmentAdapter.RecruitmentViewHolder>() {
//
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecruitmentViewHolder {
//        val binding = RvRecruitmentLayoutBinding.inflate(
//            LayoutInflater.from(parent.context),
//            parent,
//            false
//        )
//        return RecruitmentViewHolder(binding)
//    }
//
//    override fun getItemCount(): Int {
//        return recList.size
//    }
//
//    override fun onBindViewHolder(holder: RecruitmentViewHolder, position: Int) {
//        val currentItem = recList[position]
//        holder.bind(currentItem)
//    }
//
//    fun setData(recruitment: List<Recruitment>) {
//        recList = recruitment
//        notifyDataSetChanged()
//    }
//
//    fun getItem(position: Int): Recruitment {
//        return recList[position]
//    }
//
//    fun setOnItemClickListener(listener: OnItemClickListener) {
//        onItemClickListener = listener
//    }
//
//    interface OnItemClickListener {
//        fun onItemClick(position: Int)
//    }
//
//    inner class RecruitmentViewHolder(val binding: RvRecruitmentLayoutBinding) :
//        RecyclerView.ViewHolder(binding.root) {
//
//        init {
//            binding.root.setOnClickListener {
//                val position = bindingAdapterPosition
//                if (position != RecyclerView.NO_POSITION && onItemClickListener != null) {
//                    onItemClickListener?.onItemClick(position)
//                }
//            }
//        }
//
//        fun bind(recruitment: Recruitment) {
//            binding.idNo.text = recruitment.id.toString()
//            binding.fname.text = recruitment.Name.toString()
//            binding.idNo.text = recruitment.PhoneNumber.toString()
//
//        }
//    }
//}


class RecruitmentAdapter(
    private val isEditable: Boolean,
    private var recList: List<Recruitment> = emptyList(),
    private var onItemClickListener: OnItemClickListener? = null
) : RecyclerView.Adapter<RecruitmentAdapter.RecruitmentViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecruitmentViewHolder {
        val binding = RvRecruitmentLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return RecruitmentViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return recList.size
    }

    override fun onBindViewHolder(holder: RecruitmentViewHolder, position: Int) {
        val currentItem = recList[position]
        holder.bind(currentItem, true)
    }

    fun setData(recruitment: List<Recruitment>) {
        recList = recruitment
        notifyDataSetChanged()
    }

    fun getItem(position: Int): Recruitment {
        return recList[position]
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        onItemClickListener = listener
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
        fun onEditClick(position: Int)

        fun onScheduleToggle(position: Int, isScheduled: Boolean)
    }

    inner class RecruitmentViewHolder(private val binding: RvRecruitmentLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION && onItemClickListener != null) {
                    onItemClickListener?.onItemClick(position)
                }
            }
            binding.edit.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION && onItemClickListener != null) {
                    onItemClickListener?.onEditClick(position)
                }
            }
            binding.switchSchedule.setOnCheckedChangeListener { _, isChecked ->
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION && onItemClickListener != null) {
                    onItemClickListener?.onScheduleToggle(position, isChecked)
                }
            }
        }

        fun bind(recruitment: Recruitment, isEditable: Boolean) {
            binding.apply {
                idNo.text = recruitment.id.toString()
                fname.text = recruitment.Name.toString()
                phoneNumber.text = recruitment.PhoneNumber.toString()

                switchSchedule.isChecked = recruitment.isScheduled
//                switchSchedule.isEnabled = isEditable && recruitment.isEditable
            }
        }
    }


    class ScheduleListAdapter : RecyclerView.Adapter<ScheduleListAdapter.ScheduleViewHolder>() {

        private val scheduleList = mutableListOf<Recruitment>()

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScheduleViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ScheduledRecruitItemBinding.inflate(inflater, parent, false)
            return ScheduleViewHolder(binding)
        }

        override fun onBindViewHolder(holder: ScheduleViewHolder, position: Int) {
            val recruitment = scheduleList[position]
            holder.bind(recruitment)
        }

        override fun getItemCount(): Int = scheduleList.size

        fun setData(recruitment: List<Recruitment>) {
            scheduleList.clear()
            scheduleList.addAll(recruitment)
            notifyDataSetChanged()
        }

        inner class ScheduleViewHolder(private val binding: ScheduledRecruitItemBinding) :
            RecyclerView.ViewHolder(binding.root) {

            fun bind(recruitment: Recruitment) {
                binding.textName.text = recruitment.Name
                binding.textPhoneNumber.text = recruitment.PhoneNumber
            }
        }
    }


    class TestAdapter(private val onItemClick: (Question) -> Unit) :
        RecyclerView.Adapter<TestAdapter.TestViewHolder>() {

        private var questions: List<Question> = emptyList()

        fun submitList(newQuestions: List<Question>) {
            questions = newQuestions
            notifyDataSetChanged()
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TestViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ItemQuestionBinding.inflate(inflater, parent, false)
            return TestViewHolder(binding)
        }

        override fun onBindViewHolder(holder: TestViewHolder, position: Int) {
            val question = questions[position]
            holder.bind(question)
        }

        override fun getItemCount(): Int = questions.size

        inner class TestViewHolder(private val binding: ItemQuestionBinding) :
            RecyclerView.ViewHolder(binding.root) {

            init {
                binding.root.setOnClickListener {
                    val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        val question = questions[position]
                        onItemClick(question)
                    }
                }
            }

            fun bind(question: Question) {
                binding.questionTextView.text = question.question

                binding.optionsRadioGroup.setOnCheckedChangeListener(null)
                binding.optionsRadioGroup.removeAllViews()

                for (option in question.options) {
                    val radioButton = RadioButton(binding.root.context).apply {
                        id = View.generateViewId()
                        text = option
                    }
                    binding.optionsRadioGroup.addView(radioButton)
                }

                binding.optionsRadioGroup.setOnCheckedChangeListener { group, checkedId ->
                    val radioButton = group.findViewById<RadioButton>(checkedId)
                    val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        val question = questions[position]
                        onItemClick(question)
                    }
                }
            }
        }
    }
}



//
//class RecruitmentLeaderAdapter(
//    private val itemClickListener: OnItemClickListener,
//    private val preferenceHelper: PreferenceHelper,
//    private val navController: NavController
//) : RecyclerView.Adapter<RecruitmentLeaderAdapter.ViewHolder>() {
//    private val recruitmentList = mutableListOf<Recruitment>()
//    private var dataset: List<Recruitment> = emptyList()
//
//    fun setData(recruitment: List<Recruitment>) {
//        recruitmentList.clear()
//        recruitmentList.addAll(recruitment)
//        notifyDataSetChanged()
//    }
//
//    fun setDataset(recruitment: List<Recruitment>) {
//        dataset = recruitment
//        notifyDataSetChanged()
//    }
//
//    interface OnItemClickListener {
//        fun onItemClick(recruitment: Recruitment)
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        val binding =
//            ListItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
//        return ViewHolder(binding).apply {
//            binding.takeTestButton.setOnClickListener {
//                val position = adapterPosition
//                if (position != RecyclerView.NO_POSITION) {
//                    val recruitment = recruitmentList[position]
//                    // Handle "Take Test" button click here
//                    itemClickListener.onItemClick(recruitment)
//                    // ...
//                }
//            }
//        }
//    }
//
//    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        val recruitment = recruitmentList[position]
//        holder.bind(recruitment)
//
//        holder.itemView.setOnClickListener {
//            itemClickListener.onItemClick(recruitment)
//        }
//        // Set the switch state based on the saved preference
//        val switchState = preferenceHelper.getSwitchState(recruitment.id.toString())
//        holder.toggleSwitch.isChecked = switchState
//
//        holder.takeTestButton.visibility = if (recruitment.scheduledForTest) View.VISIBLE else View.GONE // Uncomment this line
//
//    }
//
//    override fun getItemCount(): Int {
//        return recruitmentList.size
//    }
//
//    inner class ViewHolder(private val binding: ListItemLayoutBinding) :
//        RecyclerView.ViewHolder(binding.root) {
//        private val cardView: MaterialCardView = binding.detailsItemLayoutParent
//        val takeTestButton: Button = binding.takeTestButton
//        //        private val toggleSwitch: Switch = binding.toggle
//        val toggleSwitch: Switch = binding.toggle // Make toggleSwitch accessible within ViewHolder
//        init {
//            toggleSwitch.setOnCheckedChangeListener { _, isChecked ->
//                val position = adapterPosition
//                if (position != RecyclerView.NO_POSITION) {
//                    val recruitment = recruitmentList[position]
//                    recruitment.scheduledForTest = isChecked
//                    toggleItemEnabled(isChecked)
//
//                    // Save the switch state in the preference
//                    preferenceHelper.saveSwitchState(recruitment.id.toString(), isChecked)
//                }
//            }
//
//            binding.root.setOnClickListener {
//                val position = adapterPosition
//                if (position != RecyclerView.NO_POSITION) {
//                    val recruitment = recruitmentList[position]
//                    itemClickListener.onItemClick(recruitment)
//                }
//            }
//
//            itemView.setOnClickListener {
//                val position = adapterPosition
//                if (position != RecyclerView.NO_POSITION) {
//                    val recruitment = recruitmentList[position]
//                    itemClickListener.onItemClick(recruitment)
//                }
//            }
//        }
//
//        fun bind(recruitment: Recruitment) {
//            binding.fname.text = recruitment.Name
//            binding.phoneNumber.text = recruitment.PhoneNumber
//            binding.idNo.text = recruitment.id.toString()
////            binding.DOB.text = recruitment.DOB.toString()
////            binding.sex.text = recruitment.Sex.toString()
////            binding.bvn.text = recruitment.BVN.toString()
////            binding.nin.text = recruitment.NIN.toString()
////            binding.state.text = recruitment.State.toString()
////            binding.lga.text = recruitment.LGA.toString()
////            binding.hub.text = recruitment.Hub.toString()
////            binding.govId.text = recruitment.GovID.toString()
////            binding.govIdType.text = recruitment.IdType.toString()
//
//            // Load and set the image using Glide or Picasso
////            Glide.with(binding.root)
////                .load(recruitment.IdImage) // Assuming IdImage is the image URL or path
////                .into(binding.idImage)
//
//            toggleSwitch.isChecked = recruitment.scheduledForTest
//            toggleItemEnabled(recruitment.scheduledForTest)
//
//            takeTestButton.visibility = if (recruitment.scheduledForTest) View.VISIBLE else View.GONE
//
//            // Save the entire Recruitment object as a tag on the root view
//            binding.root.tag = recruitment
//        }
//        private fun toggleItemEnabled(enabled: Boolean) {
//            cardView.isEnabled = !enabled
//            cardView.isClickable = !enabled
//            takeTestButton.isEnabled = enabled
//            takeTestButton.isClickable = enabled
////            if (enabled) {
////                cardView.alpha = 0.5f // Apply grayed-out effect
////            } else {
////                cardView.alpha = 1.0f // Restore original alpha
////            }
//            cardView.alpha = if (enabled) 0.5f else 1.0f
//        }
//    }
//}




//
//
//class RecruitmentLeaderAdapter(
//    private val itemClickListener: OnItemClickListener,
//    private val preferenceHelper: PreferenceHelper,
//    private val navController: NavController
//) : RecyclerView.Adapter<RecruitmentLeaderAdapter.ViewHolder>() {
//    private val recruitmentList = mutableListOf<Recruitment>()
//    private var dataset: List<Recruitment> = emptyList()
//
//    fun setData(recruitment: List<Recruitment>) {
//        recruitmentList.clear()
//        recruitmentList.addAll(recruitment)
//        notifyDataSetChanged()
//    }
//
//    fun setDataset(recruitment: List<Recruitment>) {
//        dataset = recruitment
//        notifyDataSetChanged()
//    }
//
//    interface OnItemClickListener {
//        fun onItemClick(recruitment: Recruitment)
//        fun onTakeTestClick(recruitment: Recruitment)
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        val binding = ListItemLayoutBinding.inflate(
//            LayoutInflater.from(parent.context),
//            parent,
//            false
//        )
//        return ViewHolder(binding)
//    }
//
//    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        val recruitment = recruitmentList[position]
//        holder.bind(recruitment)
//
//        // Set the switch state based on the saved preference
//        val switchState = preferenceHelper.getSwitchState(recruitment.id.toString())
//        holder.toggleSwitch.isChecked = switchState
//
//        holder.takeTestButton.visibility = if (recruitment.scheduledForTest) View.VISIBLE else View.GONE
//    }
//
//    override fun getItemCount(): Int {
//        return recruitmentList.size
//    }
//
//    inner class ViewHolder(private val binding: ListItemLayoutBinding) :
//        RecyclerView.ViewHolder(binding.root) {
//
//        private val cardView: MaterialCardView = binding.detailsItemLayoutParent
//        val takeTestButton: Button = binding.takeTestButton
//        val toggleSwitch: Switch = binding.toggle
//
//
//        init {
//            binding.root.setOnClickListener {
//                val position = adapterPosition
//                if (position != RecyclerView.NO_POSITION) {
//                    val recruitment = recruitmentList[position]
//                    if (!recruitment.scheduledForTest) {
//                        itemClickListener.onItemClick(recruitment)
//                    }
//                }
//            }
//
////            toggleSwitch.setOnCheckedChangeListener(null)
//            toggleSwitch.setOnCheckedChangeListener { _, isChecked ->
//                val position = adapterPosition
//                if (position != RecyclerView.NO_POSITION) {
//                    val recruitment = recruitmentList[position]
//                    recruitment.scheduledForTest = isChecked
//                    toggleItemEnabled(isChecked)
//
//                    // Save the switch state in the preference
//                    preferenceHelper.saveSwitchState(recruitment.id.toString(), isChecked)
//                }
//            }
//
//            takeTestButton.setOnClickListener {
//                val position = adapterPosition
//                if (position != RecyclerView.NO_POSITION) {
//                    val recruitment = recruitmentList[position]
//                    if (recruitment.scheduledForTest) {
//                        itemClickListener.onItemClick(recruitment)
//                    }
//                    // ...
//                }
//            }
//        }
//
//        fun bind(recruitment: Recruitment) {
//            binding.fname.text = recruitment.Name
//            binding.phoneNumber.text = recruitment.PhoneNumber
//            binding.idNo.text = recruitment.id.toString()
//
//            toggleSwitch.isChecked = recruitment.scheduledForTest
//            toggleItemEnabled(recruitment.scheduledForTest)
//
//            takeTestButton.visibility =
//                if (recruitment.scheduledForTest) View.VISIBLE else View.GONE
//
//            binding.root.tag = recruitment
//
//            // Set the visibility of the 'take test' button based on the recruitment's scheduledForTest value
//            takeTestButton.visibility =
//                if (recruitment.scheduledForTest) View.VISIBLE else View.INVISIBLE
//
//            // Set the item view state based on the recruitment's scheduledForTest value
//            if (recruitment.scheduledForTest) {
//                // Item should be grayed out and not clickable
//                cardView.isEnabled = false
//                cardView.isClickable = false
//                cardView.alpha = 0.5f
//            } else {
//                // Item should not be grayed out and clickable
//                cardView.isEnabled = true
//                cardView.isClickable = true
//                cardView.alpha = 1.0f
//            }
//        }
//
//        private fun toggleItemEnabled(enabled: Boolean) {
//            cardView.isEnabled = !enabled
//            cardView.isClickable = !enabled
//            takeTestButton.isEnabled = enabled
//            takeTestButton.isClickable = enabled
//            cardView.alpha = if (enabled) 1.0f else 0.5f
//        }
//    }
//}




class RecruitmentLeaderAdapter(
    private val itemClickListener: OnItemClickListener,
    private val preferenceHelper: PreferenceHelper,
    private val navController: NavController
) : RecyclerView.Adapter<RecruitmentLeaderAdapter.ViewHolder>() {
    private val recruitmentList = mutableListOf<Recruitment>()

    fun setData(recruitment: List<Recruitment>) {
        recruitmentList.clear()
        recruitmentList.addAll(recruitment)
        notifyDataSetChanged()
    }

    interface OnItemClickListener {
        fun onItemClick(recruitment: Recruitment)
        fun onTakeTestClick(recruitment: Recruitment)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ListItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val recruitment = recruitmentList[position]
        holder.bind(recruitment)

        holder.itemView.setOnClickListener {
            if (!recruitment.scheduledForTest) {
                itemClickListener.onItemClick(recruitment)
            }
        }

        holder.toggleSwitch.setOnCheckedChangeListener(null)
        holder.toggleSwitch.isChecked = preferenceHelper.getSwitchState(recruitment.id.toString())
        holder.toggleSwitch.setOnCheckedChangeListener { _, isChecked ->
            recruitment.scheduledForTest = isChecked
            holder.toggleItemEnabled(isChecked)

            // Save the switch state in the preference
            preferenceHelper.saveSwitchState(recruitment.id.toString(), isChecked)

            // Update the visibility of the "Take Test" button
            holder.updateTakeTestButtonVisibility() // Change 1: Update button visibility
        }

        holder.takeTestButton.setOnClickListener {
            if (recruitment.scheduledForTest) {
                itemClickListener.onTakeTestClick(recruitment)
            }
        }

        // Update the visibility of the "Take Test" button
//        holder.updateTakeTestButtonVisibility()
    }

    override fun getItemCount(): Int {
        return recruitmentList.size
    }

    inner class ViewHolder(private val binding: ListItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val cardView: MaterialCardView = binding.detailsItemLayoutParent
        val takeTestButton: Button = binding.takeTestButton
        val toggleSwitch: Switch = binding.toggle

        fun bind(recruitment: Recruitment) {
            binding.fname.text = recruitment.Name
            binding.phoneNumber.text = recruitment.PhoneNumber
            binding.idNo.text = recruitment.id.toString()

            toggleItemEnabled(recruitment.scheduledForTest)

            toggleSwitch.isChecked = recruitment.scheduledForTest

            binding.root.tag = recruitment

            // Set the item view state based on the recruitment's scheduledForTest value
            if (recruitment.scheduledForTest) {
                // Item should be grayed out and not clickable
                cardView.isEnabled = false
                cardView.isClickable = false
                cardView.alpha = 0.5f
            } else {
                // Item should not be grayed out and clickable
                cardView.isEnabled = true
                cardView.isClickable = true
                cardView.alpha = 1.0f
            }

        }

        fun toggleItemEnabled(enabled: Boolean) {
            cardView.isEnabled = !enabled
            cardView.isClickable = !enabled
            takeTestButton.isEnabled = enabled
            takeTestButton.isClickable = enabled
            cardView.alpha = if (enabled) 1.0f else 0.5f
        }

        fun updateTakeTestButtonVisibility() {
            val recruitment = recruitmentList[adapterPosition]
            takeTestButton.visibility = if (recruitment.scheduledForTest) View.VISIBLE else View.INVISIBLE
        }
    }
}

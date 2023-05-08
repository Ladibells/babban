package com.example.bgrecruitment.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bgrecruitment.R
import com.example.bgrecruitment.data.Recruitment
import com.example.bgrecruitment.data.User
import com.example.bgrecruitment.databinding.RvRecruitmentLayoutBinding
import com.example.bgrecruitment.databinding.UserDetailsListBinding

class UserAdapter: RecyclerView.Adapter<UserAdapter.MyViewHolder>() {

    private var userList = emptyList<User>()

//    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
//
//    }

    class MyViewHolder(val binding: UserDetailsListBinding): RecyclerView.ViewHolder(binding.root)

//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserAdapter.MyViewHolder {
//        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.user_details_list, parent, false))
//    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserAdapter.MyViewHolder {
        return MyViewHolder(UserDetailsListBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: UserAdapter.MyViewHolder, position: Int) {
        val currentItem = userList[position]
//        holder.binding.idNo.text = currentItem.id.toString()
//        holder.binding.detailsName.text = currentItem.name.toString()
//        holder.binding.detailsEmail.text = currentItem.email.toString()
//        holder.binding.detailsPassword.text = currentItem.password.toString()

    }

    override fun getItemCount(): Int {
        return userList.size
    }

    fun setData(user: List<User>) {
        this.userList = user
        notifyDataSetChanged()
    }
}

class RecruitmentAdapter: RecyclerView.Adapter<RecruitmentAdapter.MyViewHolder>() {

    private var recList = emptyList<Recruitment>()

    class MyViewHolder(val binding: RvRecruitmentLayoutBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecruitmentAdapter.MyViewHolder {
        return MyViewHolder(
            RvRecruitmentLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return recList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = recList[position]
    }

    fun setData(recruitment: List<Recruitment>) {
        this.recList = recruitment
        notifyDataSetChanged()
    }
}
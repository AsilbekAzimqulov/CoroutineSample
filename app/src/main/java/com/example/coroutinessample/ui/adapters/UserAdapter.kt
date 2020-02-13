package com.example.coroutinessample.ui.adapters

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.coroutinessample.R
import com.example.coroutinessample.room.entity.UserEntity
import kotlinx.android.synthetic.main.item_user.view.*
import java.io.File

class UserAdapter :
    ListAdapter<UserEntity, UserAdapter.VH>(DIffUtil()) {

    var removeListener: ((UserEntity) -> Unit)? = null
    var clickListener: ((UserEntity) -> Unit)? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false))
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val mData = getItem(position)
        holder.bindData(mData)
    }

    inner class VH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.delete.setOnClickListener {
                removeListener?.invoke(getItem(adapterPosition))
            }
            itemView.setOnClickListener {
                clickListener?.invoke(getItem(adapterPosition))
            }
        }

        fun bindData(data: UserEntity) {
            itemView.name.text = data.name
            Glide.with(itemView.image).load(File(data.imgUrl))
                .apply(RequestOptions.circleCropTransform()).error(R.drawable.ic_person_black_24dp)
                .into(itemView.image)
        }
    }
}

class DIffUtil : DiffUtil.ItemCallback<UserEntity>() {
    override fun areItemsTheSame(oldItem: UserEntity, newItem: UserEntity): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: UserEntity, newItem: UserEntity): Boolean {
        return oldItem == newItem
    }

}
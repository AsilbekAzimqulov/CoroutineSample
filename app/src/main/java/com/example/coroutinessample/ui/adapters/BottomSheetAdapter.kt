package com.example.coroutinessample.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.coroutinessample.R
import com.example.coroutinessample.room.entity.UserEntity
import kotlinx.android.synthetic.main.item_user.view.*
import java.io.File

class BottomSheetAdapter : ListAdapter<UserEntity, BottomSheetAdapter.BottomSheetVH>(DIffUtil()) {
    var clickListener: ((UserEntity) -> Unit)? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BottomSheetVH {
        return BottomSheetVH(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_user_add_to_group,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: BottomSheetVH, position: Int) {
        val mData = getItem(position)
        holder.bindData(mData)
    }

    inner class BottomSheetVH(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindData(data: UserEntity) {
            itemView.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION)
                    clickListener?.invoke(getItem(adapterPosition))
            }
            itemView.name.text = data.name
            Glide.with(itemView.image).load(File(data.imgUrl))
                .apply(RequestOptions.circleCropTransform()).error(R.drawable.ic_person_black_24dp)
                .into(itemView.image)
        }
    }
}
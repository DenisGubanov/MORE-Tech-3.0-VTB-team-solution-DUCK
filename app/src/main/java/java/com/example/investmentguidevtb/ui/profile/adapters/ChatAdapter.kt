package com.example.investmentguidevtb.ui.profile.adapters

import android.R.id.message
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.investmentguidevtb.databinding.ItemChatMeBinding
import com.example.investmentguidevtb.databinding.ItemChatOtherBinding
import com.example.investmentguidevtb.ui.profile.models.UserMessage


class ChatAdapter() : ListAdapter<UserMessage, RecyclerView.ViewHolder>(differCallback) {

    inner class SentMessageHolder(private val binding: ItemChatMeBinding) : RecyclerView.ViewHolder(
        binding.root
    ) {
        fun bind(message: UserMessage) {
            binding.apply{
                tvMyMessage.text = message.text
            }
        }
    }

    inner class ReceivedMessageHolder(private val binding: ItemChatOtherBinding) : RecyclerView.ViewHolder(
        binding.root
    ) {
        fun bind(message: UserMessage) {
            binding.apply{
                tvOtherMessage.text = message.text
            }
        }
    }

    object differCallback : DiffUtil.ItemCallback<UserMessage>(){
        override fun areItemsTheSame(oldItem: UserMessage, newItem: UserMessage): Boolean = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: UserMessage, newItem: UserMessage): Boolean = oldItem.id == newItem.id
    }
    val differ = AsyncListDiffer(this, differCallback)

    override fun getItemViewType(position: Int): Int {
        val message = getItem(position)
        return message.userId
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType){
            VIEW_TYPE_MESSAGE_SENT -> SentMessageHolder(
                ItemChatMeBinding.inflate(
                    LayoutInflater.from(
                        parent.context
                    ), parent, false
                )
            )
            else -> ReceivedMessageHolder(
                ItemChatOtherBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentItem = getItem(position)
        currentItem?.let{
            when (holder.itemViewType) {
                VIEW_TYPE_MESSAGE_SENT -> (holder as SentMessageHolder).bind(it)
                VIEW_TYPE_MESSAGE_RECEIVED -> (holder as ReceivedMessageHolder).bind(it)
            }
        }
    }

    companion object {
        private const val VIEW_TYPE_MESSAGE_SENT = 1
        private const val VIEW_TYPE_MESSAGE_RECEIVED = 2
    }

}

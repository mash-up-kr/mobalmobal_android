package com.mashup.mobalmobal.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.funin.base.funinbase.extension.inflate
import com.mashup.base.image.GlideRequests
import com.mashup.mobalmobal.R
import com.mashup.mobalmobal.custom.view.DonationView
import com.mashup.mobalmobal.databinding.ItemMainDonationBinding
import com.mashup.mobalmobal.databinding.ItemMainHeaderBinding
import com.mashup.mobalmobal.databinding.ItemMainMyDonationsBinding

class MainAdapter(
    private val glideRequests: GlideRequests,
    private val myDonationAdapter: MyDonationAdapter,
    private val listener: OnClickListener? = null
) : PagingDataAdapter<MainAdapterItem, RecyclerView.ViewHolder>(DIFF_CALLBACK) {

    companion object {
        private const val VIEW_TYPE_MY_DONATION = R.layout.item_main_my_donations
        private const val VIEW_TYPE_HEADER = R.layout.item_main_header
        private const val VIEW_TYPE_PROGRESS_DONATION = R.layout.item_main_donation

        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<MainAdapterItem>() {
            override fun areItemsTheSame(
                oldItem: MainAdapterItem,
                newItem: MainAdapterItem
            ): Boolean = oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: MainAdapterItem,
                newItem: MainAdapterItem
            ): Boolean = oldItem == newItem
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is MainAdapterItem.MyDonation -> VIEW_TYPE_MY_DONATION
            is MainAdapterItem.Header -> VIEW_TYPE_HEADER
            else -> VIEW_TYPE_PROGRESS_DONATION
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return parent.inflate(viewType, false).let {
            when (viewType) {
                VIEW_TYPE_MY_DONATION -> MyDonationViewHolder(
                    binding = ItemMainMyDonationsBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    ),
                    adapter = myDonationAdapter
                )
                VIEW_TYPE_PROGRESS_DONATION -> ProgressDonationViewHolder(
                    binding = ItemMainDonationBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
                else -> HeaderViewHolder(
                    ItemMainHeaderBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        when {
            item is MainAdapterItem.MyDonation && holder is MyDonationViewHolder -> holder.bind(item)
            item is MainAdapterItem.Header && holder is HeaderViewHolder -> holder.bind(item)
            item is MainAdapterItem.ProgressDonation && holder is ProgressDonationViewHolder ->
                holder.bind(item)
        }
    }

    private fun MyDonationViewHolder.bind(item: MainAdapterItem.MyDonation) {
        myDonationAdapter.submitList(item.donations)
    }

    private fun HeaderViewHolder.bind(item: MainAdapterItem.Header) {
        textView.text = item.title
    }

    private fun ProgressDonationViewHolder.bind(item: MainAdapterItem.ProgressDonation) {
        donationView.apply {
            title = item.title
            dueDate = item.dueDateText.toString()
            goalPrice = item.goalPrice
            currentPrice = item.currentPrice
            setDonationImage(glideRequests, item.donationImageUrl)
            setOnClickListener { listener?.onDonationClick(item.postId) }
        }
    }

    private class MyDonationViewHolder(
        binding: ItemMainMyDonationsBinding,
        adapter: MyDonationAdapter
    ) : RecyclerView.ViewHolder(binding.root) {
        val recyclerView: RecyclerView = binding.mainMyDonationRecycler
        val myDonationAdapter: MyDonationAdapter = adapter

        init {
            recyclerView.adapter = myDonationAdapter
        }
    }

    private class HeaderViewHolder(
        binding: ItemMainHeaderBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        val textView: TextView = binding.root
    }

    private class ProgressDonationViewHolder(
        binding: ItemMainDonationBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        val donationView: DonationView = binding.root
    }

    interface OnClickListener {
        fun onDonationClick(donationId: Int)
        fun onAddDonationClick()
    }
}
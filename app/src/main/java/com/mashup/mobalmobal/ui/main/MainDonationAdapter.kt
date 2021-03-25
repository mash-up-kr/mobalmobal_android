package com.mashup.mobalmobal.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mashup.base.image.GlideRequests
import com.mashup.mobalmobal.databinding.ItemMainDonationBinding

class MainDonationAdapter(
    private val glideRequests: GlideRequests,
    private val listener: MainAdapter.OnClickListener ?= null
) : ListAdapter<MainDonationAdapterItem, RecyclerView.ViewHolder>(DIFF_CALLBACK) {
    companion object {
        private val DIFF_CALLBACK =
            AsyncDifferConfig.Builder(object : DiffUtil.ItemCallback<MainDonationAdapterItem>() {
                override fun areItemsTheSame(
                    oldItem: MainDonationAdapterItem,
                    newItem: MainDonationAdapterItem
                ): Boolean = oldItem.donationId == newItem.donationId

                override fun areContentsTheSame(
                    oldItem: MainDonationAdapterItem,
                    newItem: MainDonationAdapterItem
                ): Boolean = oldItem == newItem
            }).build()
    }

    init {
        setHasStableIds(true)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        DonationViewHolder(
            ItemMainDonationBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is DonationViewHolder) {
            holder.bind(getItem(position))
        }
    }

    private fun DonationViewHolder.bind(item: MainDonationAdapterItem) {
        donationView.apply {
            title = item.title
            dueDate = item.dueDateText.toString()
            goalPrice = item.goalPrice
            currentPrice = item.currentPrice
            currentPriceText = item.currentPriceText
            setDonationImage(glideRequests, item.donationImageUrl)
            setOnClickListener { listener?.onDonationClick(item.donationId) }
        }
    }

    private class DonationViewHolder(
        binding: ItemMainDonationBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        val donationView = binding.root
    }
}
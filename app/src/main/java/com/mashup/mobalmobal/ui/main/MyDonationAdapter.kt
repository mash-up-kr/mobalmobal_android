package com.mashup.mobalmobal.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.funin.base.funinbase.extension.inflate
import com.mashup.mobalmobal.R
import com.mashup.mobalmobal.databinding.ItemMainMyDonationBinding
import com.mashup.mobalmobal.databinding.ItemMyDonationAddBinding

class MyDonationAdapter(
    private val listener: MainAdapter.OnClickListener? = null
) : ListAdapter<MyDonationAdapterItem, RecyclerView.ViewHolder>(DIFF_CALLBACK) {
    companion object {
        private const val VIEW_TYPE_DONATION = R.layout.item_main_my_donation
        private const val VIEW_TYPE_ADDITIONAL = R.layout.item_my_donation_add

        private val DIFF_CALLBACK =
            AsyncDifferConfig.Builder(object : DiffUtil.ItemCallback<MyDonationAdapterItem>() {
                override fun areItemsTheSame(
                    oldItem: MyDonationAdapterItem,
                    newItem: MyDonationAdapterItem
                ): Boolean = oldItem.id == newItem.id

                override fun areContentsTheSame(
                    oldItem: MyDonationAdapterItem,
                    newItem: MyDonationAdapterItem
                ): Boolean = oldItem == newItem
            }).build()
    }

    init {
        setHasStableIds(true)
    }

    override fun getItemViewType(position: Int): Int = when (getItem(position)) {
        is MyDonationAdapterItem.Donation -> VIEW_TYPE_DONATION
        else -> VIEW_TYPE_ADDITIONAL
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return parent.inflate(viewType, false).let {
            when (viewType) {
                VIEW_TYPE_DONATION -> MyDonationViewHolder(
                    ItemMainMyDonationBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
                else -> MyAdditionViewHolder(
                    ItemMyDonationAddBinding.inflate(
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
            item is MyDonationAdapterItem.Donation && holder is MyDonationViewHolder ->
                holder.bind(item)
            item is MyDonationAdapterItem.Addition && holder is MyAdditionViewHolder ->
                holder.itemView.setOnClickListener { listener?.onAddDonationClick() }
        }
    }

    private fun MyDonationViewHolder.bind(item: MyDonationAdapterItem.Donation) {
        title.text = item.title
        currentPrice.text = item.currentPriceText
        itemView.setOnClickListener {
            listener?.onDonationClick(item.donationId)
        }
    }

    private class MyDonationViewHolder(
        binding: ItemMainMyDonationBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        val title: TextView = binding.mainMyDonationTitle
        val currentPrice: TextView = binding.mainMyDonationCurrentPrice
    }

    private class MyAdditionViewHolder(
        binding: ItemMyDonationAddBinding
    ) : RecyclerView.ViewHolder(binding.root)
}
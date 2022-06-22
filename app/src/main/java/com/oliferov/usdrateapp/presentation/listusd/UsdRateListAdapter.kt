package com.oliferov.usdrateapp.presentation.listusd

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.oliferov.usdrateapp.R
import com.oliferov.usdrateapp.databinding.ItemUsdRateBinding
import com.oliferov.usdrateapp.domain.UsdRate

class UsdRateListAdapter : ListAdapter<UsdRate, UsdRateListViewHolder>(UsdRateListDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsdRateListViewHolder {
        val binding = ItemUsdRateBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return UsdRateListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UsdRateListViewHolder, position: Int) {
        val usdRate = getItem(position)
        with(usdRate) {
            with(holder.binding) {
                tvDate.text = root.context.getString(R.string.date, date)
                tvValue.text =
                    root.context.getString(R.string.rate_usd, (value / nominal).toString())
            }
        }
    }
}

class UsdRateListViewHolder(val binding: ItemUsdRateBinding) : RecyclerView.ViewHolder(binding.root)

class UsdRateListDiffCallback() : DiffUtil.ItemCallback<UsdRate>() {
    override fun areItemsTheSame(
        oldItem: UsdRate,
        newItem: UsdRate
    ) = oldItem.date == newItem.date

    override fun areContentsTheSame(
        oldItem: UsdRate,
        newItem: UsdRate
    ) = oldItem == newItem

}



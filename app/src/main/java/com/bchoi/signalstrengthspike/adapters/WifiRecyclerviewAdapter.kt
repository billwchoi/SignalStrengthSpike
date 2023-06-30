package com.bchoi.signalstrengthspike.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bchoi.signalstrengthspike.databinding.RowWifiBinding
import com.bchoi.signalstrengthspike.entities.ConnectionStatus

class WifiRecyclerviewAdapter: ListAdapter<ConnectionStatus, WifiRecyclerviewAdapter.ViewHolder>(
    DiffCallback
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: RowWifiBinding = RowWifiBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    object DiffCallback: DiffUtil.ItemCallback<ConnectionStatus>() {
        override fun areItemsTheSame(
            oldItem: ConnectionStatus,
            newItem: ConnectionStatus
        ): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(
            oldItem: ConnectionStatus,
            newItem: ConnectionStatus
        ): Boolean {
            return oldItem.bssid == newItem.bssid && oldItem.dbm == newItem.dbm && oldItem.frequency == newItem.frequency && oldItem.signalLevel == newItem.signalLevel
        }

    }

    class ViewHolder(private val binding: RowWifiBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ConnectionStatus) {
            binding.connectionStatus = item
            binding.executePendingBindings()
        }
    }
}
package com.app.idtexam.views

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.app.idtexam.data.model.MockResponseDto
import com.app.idtexam.databinding.RowStateItemsBinding

class StatesAdapter(
    private val mList: List<MockResponseDto.MockData>,
    private val onItemClicked: (position: Int, MutableList<MockResponseDto.MockData>) -> Unit
) :
    Adapter<StatesAdapter.ViewHolder>(), Filterable {

    private var filteredUserList =
        mList.toMutableList()  // Filtered list for search in second RecyclerView


     class ViewHolder(val rowStateItemsBinding: RowStateItemsBinding) :
        RecyclerView.ViewHolder(rowStateItemsBinding.root){
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            RowStateItemsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        binding.tvStatesName.text = mList.get(position).state
        // Bind data from the filteredUserList for both filtered and unfiltered views
        val currentUser = filteredUserList[position]
        holder.rowStateItemsBinding.tvStatesName.text = currentUser.state
        holder.itemView.setOnClickListener {
            onItemClicked(position, filteredUserList)
        }
    }

    override fun getItemCount(): Int {
        return filteredUserList.size
    }

    // Filtering logic for the second RecyclerView
    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val query = constraint?.toString()?.lowercase()?.trim()
                val resultList = if (query.isNullOrEmpty()) {
                    mList.toMutableList()
                } else {
                    mList.filter { it.state!!.lowercase().contains(query) }
                }

                val filterResults = FilterResults()
                filterResults.values = resultList
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filteredUserList = results?.values as MutableList<MockResponseDto.MockData>
                notifyDataSetChanged()
            }
        }

    }
}
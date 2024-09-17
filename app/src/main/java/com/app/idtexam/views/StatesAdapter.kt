package com.app.idtexam.views

import android.content.res.Resources
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.app.idtexam.R
import com.app.idtexam.data.model.MockResponseDto
import com.app.idtexam.databinding.RowStateItemsBinding
import com.app.idtexam.utils.DualClickListener

class StatesAdapter(
    private val mList: List<MockResponseDto.MockData>,
    private val selectedItems: MutableList<MockResponseDto.MockData>,
    private val onItemClicked: (position: Int, MutableList<MockResponseDto.MockData>, showStateDetail: Boolean) -> Unit,
    private val onItemDoubleClicked: (Int, MutableList<MockResponseDto.MockData>) -> Unit
) :
    Adapter<StatesAdapter.ViewHolder>(), Filterable {

    private var filteredUserList =
        mList.toMutableList()

    class ViewHolder(val rowStateItemsBinding: RowStateItemsBinding) :
        RecyclerView.ViewHolder(rowStateItemsBinding.root) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            RowStateItemsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val currentState = filteredUserList[position]
        holder.rowStateItemsBinding.tvStatesName.text = currentState.state

        // Highlight the item if it is selected
        if (selectedItems.contains(currentState)) {
            holder.itemView.setBackgroundColor(Color.GRAY)
        } else {
            holder.itemView.setBackgroundColor(Color.WHITE)
        }

        holder.itemView.setOnClickListener(object : DualClickListener() {
            override fun onSingleClick(v: View) {
                onItemClicked(holder.adapterPosition, filteredUserList, true)
            }

            override fun onDoubleClick(v: View) {
                toggleHighlight(currentState, holder)
                onItemDoubleClicked(holder.adapterPosition, filteredUserList)
            }
        })
    }

    override fun getItemCount(): Int {
        return filteredUserList.size
    }

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

    private fun toggleHighlight(currentState: MockResponseDto.MockData, holder: ViewHolder) {
        if (selectedItems.contains(currentState)) {
            selectedItems.remove(currentState)
            holder.itemView.setBackgroundColor(Color.WHITE)
        } else {
            selectedItems.add(currentState)
            holder.itemView.setBackgroundColor(Color.GRAY)
        }
    }

    fun updateSelection() {
        notifyDataSetChanged()
    }
}
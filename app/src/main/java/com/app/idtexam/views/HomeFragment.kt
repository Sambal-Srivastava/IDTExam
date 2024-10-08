package com.app.idtexam.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.idtexam.R
import com.app.idtexam.data.model.MockResponseDto
import com.app.idtexam.databinding.FragmentHomeBinding
import com.app.idtexam.utils.Constants
import com.app.idtexam.viewmodel.StatesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val statesViewModel: StatesViewModel by viewModels()

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private var statesList: List<MockResponseDto.MockData> = ArrayList()
    private var itemPosition = 0
    private lateinit var firstAdapter: StatesAdapter
    private lateinit var secondAdapter: StatesAdapter
    private var showSateDetailsSection = false

    // Shared list for selected items between both adapters
    private val selectedItems = mutableListOf<MockResponseDto.MockData>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        binding.rvStates.layoutManager = LinearLayoutManager(requireContext())
        binding.rvStates.setHasFixedSize(true)

        binding.rvStatesCopy.layoutManager = LinearLayoutManager(requireContext())
        binding.rvStatesCopy.setHasFixedSize(true)
        statesViewModel.fetchStates(true)
        statesViewModel.states.observe(viewLifecycleOwner) {
            //statesList = it.data!!
            firstAdapter =
                StatesAdapter(it.data!!, selectedItems, ::onItemClicked, ::onItemDoubleClicked)
            secondAdapter =
                StatesAdapter(it.data!!, selectedItems, ::onItemClicked, ::onItemDoubleClicked)
            binding.rvStates.adapter = firstAdapter
            binding.rvStatesCopy.adapter = secondAdapter
        }

        if (!statesList.isEmpty()) {
            binding.tvState.text = "State Name: \n ${statesList?.get(itemPosition)?.state}"
            binding.tvPopulation.text =
                "State Overall Population: \n ${statesList?.get(itemPosition)?.population.toString()}"
            binding.tvCounties.text =
                "Number of counties: \n ${statesList?.get(itemPosition)?.counties.toString()}"
            binding.btnSeconScreen.isEnabled = showSateDetailsSection
        }

        statesViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading) {
                binding.progressBar?.visibility = View.VISIBLE
            } else {
                binding.progressBar?.visibility = View.GONE
            }
        }

        binding.mSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                secondAdapter.filter.filter(query)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                secondAdapter.filter.filter(newText)
                return false
            }
        })
        binding.btnSeconScreen.setOnClickListener {
            navigateToSecondScreen()
        }

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun onItemClicked(
        position: Int,
        filteredList: MutableList<MockResponseDto.MockData>,
        showStateDetail: Boolean
    ) {
        statesList = filteredList
        itemPosition = position
        binding.tvState.text = "State Name: \n ${filteredList.get(position).state}"
        binding.tvPopulation.text =
            "State Overall Population: \n ${filteredList.get(position).population.toString()}"
        binding.tvCounties.text =
            "Number of counties: \n ${filteredList.get(position).counties.toString()}"
        showSateDetailsSection = showStateDetail
        binding.btnSeconScreen.isEnabled = showSateDetailsSection
    }

    private fun onItemDoubleClicked(
        position: Int,
        filteredList: MutableList<MockResponseDto.MockData>
    ) {
        firstAdapter.updateSelection()
        secondAdapter.updateSelection()
    }

    private fun navigateToSecondScreen() {
        val bundle = Bundle().apply {
            putSerializable(Constants.STATES_LIST_KEY, ArrayList(statesList))
            putInt(Constants.ITEM_POSITION_KEY, itemPosition)
        }
        findNavController().navigate(R.id.action_homeFragment_to_detailsDuplicateFragment, bundle)
    }
}
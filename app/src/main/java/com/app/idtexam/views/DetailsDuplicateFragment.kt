package com.app.idtexam.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.idtexam.R
import com.app.idtexam.data.model.MockResponseDto
import com.app.idtexam.databinding.FragmentDetailsDuplicateBinding
import com.app.idtexam.utils.Constants

class DetailsDuplicateFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var statesList: ArrayList<MockResponseDto.MockData>? = null
    private var itemPosition = 0

    private var _binding: FragmentDetailsDuplicateBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            statesList =
                it.getSerializable(Constants.STATES_LIST_KEY) as? ArrayList<MockResponseDto.MockData>
            itemPosition = it.getInt(Constants.ITEM_POSITION_KEY)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDetailsDuplicateBinding.inflate(inflater, container, false)
        binding.tvState.text = "State Name: \n ${statesList?.get(itemPosition)?.state}"
        binding.tvPopulation.text =
            "State Overall Population: \n ${statesList?.get(itemPosition)?.population.toString()}"
        binding.tvCounties.text =
            "Number of counties: \n ${statesList?.get(itemPosition)?.counties.toString()}"
        return binding.root
    }
}
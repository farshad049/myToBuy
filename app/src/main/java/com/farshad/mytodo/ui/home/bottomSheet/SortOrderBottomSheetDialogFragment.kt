package com.farshad.mytodo.ui.home.bottomSheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.farshad.mytodo.arch.ToBuyViewModel
import com.farshad.mytodo.arch.viewStateEntity.HomeViewState
import com.farshad.mytodo.databinding.BottomSheetSortOrderBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class SortOrderBottomSheetDialogFragment:BottomSheetDialogFragment() {

    private var _binding:BottomSheetSortOrderBinding?=null
    private val binding get() = _binding!!
    //because this fragment doesn't inherit from BaseFragment, we have to do this to be sure this fragment is using the same viewModel
    private val viewModel:ToBuyViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding= BottomSheetSortOrderBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //we manage setOnClickListener by changed on HomeViewState.Sort.values
        //means every time that sort has been changed then update currentSort which also runs a function include in ToBuyViewModel
        val controller = BottomSheetEpoxyController(viewModel.currentSort,HomeViewState.Sort.values()){
            viewModel.currentSort=it
            //this close the fragment and also help up to update styling for selected option
            dismiss()
        }
        binding.epoxyRecyclerViewSort.setControllerAndBuildModels(controller)








    }//FUN














    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }
}
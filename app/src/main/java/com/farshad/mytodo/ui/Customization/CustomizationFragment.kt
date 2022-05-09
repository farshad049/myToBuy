package com.farshad.mytodo.ui.Customization

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.farshad.mytodo.R
import com.farshad.mytodo.database.entity.CategoryEntity
import com.farshad.mytodo.databinding.FragmentProfileBinding
import com.farshad.mytodo.ui.BaseFragment


class CustomizationFragment: BaseFragment(), CustomizationInterface {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private val profileEpoxyController = CustomizationEpoxyController(this)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        mainActivity.hideKeyboard(requireView())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.epoxyRecyclerView.setController(profileEpoxyController)

        sharedViewModel.categoryEntityLiveData.observe(viewLifecycleOwner) { categoryEntityList ->
            profileEpoxyController.categories = categoryEntityList
        }
    }

    override fun onCategoryEmptyStateClicked() {
        navigateViaNavGraph(R.id.action_profileFragment_to_addCategoryFragment)
    }

    override fun onDeleteCategory(categoryEntity: CategoryEntity) {
        sharedViewModel.deleteCategory(categoryEntity)
    }

    override fun onCategorySelected(categoryEntity: CategoryEntity) {
        Log.i("ProfileFragment", categoryEntity.toString())
    }

    override fun onPrioritySelected(priorityName: String) {
        navigateViaNavGraph(CustomizationFragmentDirections
            .actionCustomizationFragmentToColorPickerFragment(priorityName)
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

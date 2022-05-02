package com.farshad.mytodo.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.farshad.mytodo.R
import com.farshad.mytodo.database.entity.CategoryEntity
import com.farshad.mytodo.database.entity.ItemEntity
import com.farshad.mytodo.databinding.FragmentAddItemEntityBinding
import com.farshad.mytodo.databinding.FragmentProfileBinding
import com.farshad.mytodo.ui.BaseFragment
import com.farshad.mytodo.ui.add.AddItemEntityFragmentArgs

class ProfileFragment:BaseFragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private val controller=ProfileEpoxyController(onCategoryEmptyStateClick=::onCategoryEmptyStateClick)



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedViewModel.categoryEntityLiveData.observe(viewLifecycleOwner){ categoryEntity ->
            controller.categories=categoryEntity as ArrayList<CategoryEntity>
        }

        binding.epoxyRecyclerView.setController(controller)




    }//FUN

    private fun onCategoryEmptyStateClick() {
       navigateViaNavGraph(R.id.action_profileFragment_to_addCategoryFragment)
    }









    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

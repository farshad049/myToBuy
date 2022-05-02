package com.farshad.mytodo.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.farshad.mytodo.database.entity.CategoryEntity
import com.farshad.mytodo.databinding.FragmentAddCategoryBinding

import com.farshad.mytodo.ui.BaseFragment
import java.util.*

class AddCategoryFragment:BaseFragment() {
    private var _binding: FragmentAddCategoryBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentAddCategoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.etEditTitle.requestFocus()
        mainActivity.showKeyboard()
        binding.saveButton.setOnClickListener {
            saveCategory()
        }


    }//FUN

    private fun saveCategory(){
        val categoryTitle=binding.etEditTitle.text.toString().trim()
        if (categoryTitle.isEmpty()) {
            binding.etTitle.error = "* Require Field"
            return
        }
        sharedViewModel.insertCategory(
            CategoryEntity(
                id = UUID.randomUUID().toString(),
                name = categoryTitle
            )
        )
        navigateUp()
    }






    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
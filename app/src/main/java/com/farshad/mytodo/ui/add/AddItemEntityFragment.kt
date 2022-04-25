package com.farshad.mytodo.ui.add

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.farshad.mytodo.R
import com.farshad.mytodo.database.entity.ItemEntity
import com.farshad.mytodo.databinding.FragmentAddItemEntityBinding
import com.farshad.mytodo.ui.BaseFragment
import java.util.*

class AddItemEntityFragment:BaseFragment() {

    private var _binding: FragmentAddItemEntityBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentAddItemEntityBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.saveButton.setOnClickListener {
            saveEntityToDatabase()
        }

    }//FUN

    private fun saveEntityToDatabase(){
        val itemTitle=binding.etEditTitle.text.toString().trim()
        if (itemTitle.isEmpty()){
            binding.etTitle.error="* Required Field"
            return
        }else{
            binding.etTitle.error=null
        }
        val itemDescription=binding.etEditDescription.text.toString().trim()
        val itemPriority =when(binding.radioGroup.checkedRadioButtonId){
            R.id.radioButtonLow -> 1
            R.id.radioButtonMedium -> 2
            R.id.radioButtonHigh -> 3
            else -> 0
        }

        sharedViewModel.insertItem(
            ItemEntity(
                id = UUID.randomUUID().toString(),
                title = itemTitle,
                description = itemDescription,
                priority = itemPriority,
                createdAt = System.currentTimeMillis(),
                category = ""//TODO
            )
        )
    }







    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
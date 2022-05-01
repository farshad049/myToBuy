package com.farshad.mytodo.ui.add

import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.Toast
import androidx.navigation.fragment.navArgs
import com.farshad.mytodo.R
import com.farshad.mytodo.database.entity.ItemEntity
import com.farshad.mytodo.databinding.FragmentAddItemEntityBinding
import com.farshad.mytodo.ui.BaseFragment
import java.util.*

class AddItemEntityFragment:BaseFragment() {

    private var _binding: FragmentAddItemEntityBinding? = null
    private val binding get() = _binding!!

    private val safeArgs:AddItemEntityFragmentArgs by navArgs()
    private val selectedItemEntity :ItemEntity? by lazy {
        sharedViewModel.itemEntityLiveData.value?.find {
            it.id ==safeArgs.selectedItemEntityId
        }
    }
   // private var isInEditMode:Boolean=false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentAddItemEntityBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        //setup save button
        binding.saveButton.setOnClickListener {
            saveEntityToDatabase()
        }

        binding.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                val currentText = binding.etEditTitle.text.toString().trim()
                if (currentText.isEmpty()) {
                    return
                }
                //return -1 if doesn't found "[" means doesn't have quantity
                val startIndex = currentText.indexOf("[") - 1
                //if it has quantity then...
                val newText = if (startIndex > 0) {
                    "${currentText.substring(0, startIndex)} [$progress]"
                } else {
                    "$currentText [$progress]"
                }
                //if it has quantity 1 ,remove the quantity
                val sanitizedText = newText.replace(" [1]", "")
                binding.etEditTitle.setText(sanitizedText)
                binding.etEditTitle.setSelection(sanitizedText.length)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                //TODO("Not yet implemented")


            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                //TODO("Not yet implemented")
            }
        })


        //handle update mode or insert mode
        if (selectedItemEntity != null) {
         //   isInEditMode = true
            binding.etEditTitle.setText(selectedItemEntity!!.title)
            //set mouse pointer at the end of text just fill more than we are in edit mode
            binding.etEditTitle.setSelection(selectedItemEntity!!.title.length)
            binding.etEditDescription.setText(selectedItemEntity!!.description)
            when (selectedItemEntity!!.priority) {
                1 -> binding.radioGroup.check(R.id.radioButtonLow)
                2 -> binding.radioGroup.check(R.id.radioButtonMedium)
                else -> binding.radioGroup.check(R.id.radioButtonHigh)
            }
            binding.saveButton.text = "update"
            mainActivity.supportActionBar?.title = "update item"
            // if title contained quantity take it pas set the seekBar
            if (selectedItemEntity!!.title.contains("[")) {
                val startIndex = selectedItemEntity!!.title.indexOf("[")
                val endIndex = selectedItemEntity!!.title.indexOf("]")
                try {
                    val progress =
                        selectedItemEntity!!.title.substring(startIndex, endIndex).toInt()
                    binding.seekBar.progress = progress
                } catch (e: Exception) {
                    //Whoops
                }
            }
//            else {
//                sharedViewModel.transactionCompleteLiveData.observe(viewLifecycleOwner) { complete ->
//                    if (complete) {
//
//                        binding.etEditTitle.text?.clear()
//                        //set mouse pointer to this field
//                        binding.etEditDescription.requestFocus()
//                        mainActivity.showKeyboard()
//                        binding.etEditDescription.text?.clear()
//                        binding.radioGroup.check(R.id.radioButtonLow)
//                    }
//                    // Show keyboard and default select our Title EditText when we get into this page
//                    mainActivity.showKeyboard()
//                    binding.etEditTitle.requestFocus()
//                }
//            }

            //check if data has been successfully saved then reset the form
//        sharedViewModel.transactionCompleteLiveData.observe(viewLifecycleOwner){complete ->
//            if (complete){
//                //after click on save button ,run this so it exits this fragment
//                if (isInEditMode){
//                    navigateUp()
//                    return@observe
//                }
//                Toast.makeText(requireContext(),"item saved!",Toast.LENGTH_SHORT).show()
//                binding.etEditTitle.text?.clear()
//                //set mouse pointer to this field
//                binding.etEditDescription.requestFocus()
//                mainActivity.showKeyboard()
//                binding.etEditDescription.text?.clear()
//                binding.radioGroup.check(R.id.radioButtonLow)
//            }
//            // Show keyboard and default select our Title EditText when we get into this page
//            mainActivity.showKeyboard()
//            binding.etEditTitle.requestFocus()
//        }

            //in update mode check if selectedItemEntity in not empty the do this
            // .let means like if(selectedItemEntity != null)
//        selectedItemEntity?.let { itemEntity ->
//            isInEditMode=true
//            binding.etEditTitle.setText(itemEntity.title)
//            //set mouse pointer at the end of text just fill more than we are in edit mode
//            binding.etEditTitle.setSelection(itemEntity.title.length)
//            binding.etEditDescription.setText(itemEntity.description)
//            when(itemEntity.priority){
//                1 -> binding.radioGroup.check(R.id.radioButtonLow)
//                2 -> binding.radioGroup.check(R.id.radioButtonMedium)
//                else -> binding.radioGroup.check(R.id.radioButtonHigh)
//            }
//            binding.saveButton.text="update"
//            mainActivity.supportActionBar?.title="update item"
//        }

        }//FUN
    }


        private fun saveEntityToDatabase() {
            val itemTitle = binding.etEditTitle.text.toString().trim()
            if (itemTitle.isEmpty()) {
                binding.etTitle.error = "* Required Field"
                return
            } else {
                binding.etTitle.error = null
            }
            val itemDescription = binding.etEditDescription.text.toString().trim()
            val itemPriority = when (binding.radioGroup.checkedRadioButtonId) {
                R.id.radioButtonLow -> 1
                R.id.radioButtonMedium -> 2
                R.id.radioButtonHigh -> 3
                else -> 0
            }

            if (selectedItemEntity != null) {
                val itemEntity = selectedItemEntity!!.copy(
                    title = itemTitle,
                    description = itemDescription,
                    priority = itemPriority
                )
                sharedViewModel.updateItem(itemEntity)
                //came back to home page
                navigateUp()
            } else {
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
                Toast.makeText(requireContext(), "item saved!", Toast.LENGTH_SHORT).show()
                binding.etEditTitle.text?.clear()
                //set mouse pointer to this field
                binding.etEditDescription.requestFocus()
                mainActivity.showKeyboard()
                binding.etEditDescription.text?.clear()
                binding.radioGroup.check(R.id.radioButtonLow)
                // Show keyboard and default select our Title EditText when we get into this page
                mainActivity.showKeyboard()
                binding.etEditTitle.requestFocus()
            }
        }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
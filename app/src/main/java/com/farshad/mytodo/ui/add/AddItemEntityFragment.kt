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
import com.farshad.mytodo.database.entity.CategoryEntity
import com.farshad.mytodo.database.entity.ItemEntity
import com.farshad.mytodo.databinding.FragmentAddItemEntityBinding
import com.farshad.mytodo.ui.BaseFragment
import java.util.*

class AddItemEntityFragment:BaseFragment() {

    private var _binding: FragmentAddItemEntityBinding? = null
    private val binding get() = _binding!!

    private val safeArgs:AddItemEntityFragmentArgs by navArgs()
    private val selectedItemEntity :ItemEntity? by lazy {
        sharedViewModel.itemEntityWithCategoryLiveData.value?.find {
            it.itemEntity.id ==safeArgs.selectedItemEntityId
            //find is like itemEntityWithCategoryLiveData the we should add .itemEntity at the end to be match with private val selectedItemEntity :ItemEntity?
        }?.itemEntity
    }
    private var isInEditMode:Boolean=false

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


//        //handle update mode or insert mode
//        if (selectedItemEntity != null) {
//         //   isInEditMode = true
//            binding.etEditTitle.setText(selectedItemEntity!!.title)
//            //set mouse pointer at the end of text just fill more than we are in edit mode
//            binding.etEditTitle.setSelection(selectedItemEntity!!.title.length)
//            binding.etEditDescription.setText(selectedItemEntity!!.description)
//            when (selectedItemEntity!!.priority) {
//                1 -> binding.radioGroup.check(R.id.radioButtonLow)
//                2 -> binding.radioGroup.check(R.id.radioButtonMedium)
//                else -> binding.radioGroup.check(R.id.radioButtonHigh)
//            }
//            binding.saveButton.text = "update"
//            mainActivity.supportActionBar?.title = "update item"
//            // if title contained quantity take it pas set the seekBar
//            if (selectedItemEntity!!.title.contains("[")) {
//                val startIndex = selectedItemEntity!!.title.indexOf("[")
//                val endIndex = selectedItemEntity!!.title.indexOf("]")
//                try {
//                    val progress =
//                        selectedItemEntity!!.title.substring(startIndex, endIndex).toInt()
//                    binding.seekBar.progress = progress
//                } catch (e: Exception) {
//                    //Whoops
//                }
//            }

          //  check if data has been successfully saved ot updated then if we were updating, back to previous fragment, if we were inserting stay on this fragment
        sharedViewModel.transactionCompleteLiveData.observe(viewLifecycleOwner){event ->
            //it will be null only if it has content
            event.getContent()?.let {
                if (isInEditMode) {
                    navigateUp()
                    return@observe
                }
                Toast.makeText(requireContext(),"item saved!",Toast.LENGTH_SHORT).show()
                binding.etEditTitle.text?.clear()
                //set mouse pointer to this field
                binding.etEditDescription.requestFocus()
                mainActivity.showKeyboard()
                binding.etEditDescription.text?.clear()
                binding.radioGroup.check(R.id.radioButtonLow)
            }
            // Show keyboard and default select our Title EditText when we get into this page
            mainActivity.showKeyboard()
            binding.etEditTitle.requestFocus()
        }

          //  in update mode check if selectedItemEntity in not empty the do this
          //   .let means like if(selectedItemEntity != null)
        selectedItemEntity?.let { itemEntity ->
            isInEditMode = true
            binding.etEditTitle.setText(itemEntity.title)
            //set mouse pointer at the end of text just fill more than we are in edit mode
            binding.etEditTitle.setSelection(itemEntity.title.length)
            binding.etEditDescription.setText(itemEntity.description)
            when (itemEntity.priority) {
                1 -> binding.radioGroup.check(R.id.radioButtonLow)
                2 -> binding.radioGroup.check(R.id.radioButtonMedium)
                else -> binding.radioGroup.check(R.id.radioButtonHigh)
            }
            binding.saveButton.text = "update"
            mainActivity.supportActionBar?.title = "update item"
            //if title contained quantity take it pas set the seekBar
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
        }

        val controller=CategoryViewStateEpoxyModel(){categoryId->
            sharedViewModel.onCategorySelected(categoryId)
        }
        sharedViewModel.categoriesViewStateLiveData.observe(viewLifecycleOwner){
            controller.viewState=it
        }
        binding.categoryEpoxyRecyclerView.setController(controller)
        //if were in insert mode set "NONE" as selectedItemEntity id, otherwise will set selected item id to selectedItemEntity
        sharedViewModel.onCategorySelected(selectedItemEntity?.categoryId ?:CategoryEntity.DEFAULT_CATEGORY_ID,true)

        }//FUN

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

            val itemCategory=sharedViewModel.categoriesViewStateLiveData.value?.getSelectedCategoryId() ?:return

            if (isInEditMode) {
                val itemEntity = selectedItemEntity!!.copy(
                    title = itemTitle,
                    description = itemDescription,
                    priority = itemPriority,
                    categoryId = itemCategory
                )

                sharedViewModel.updateItem(itemEntity)
                return
            }

            val itemEntity = ItemEntity(
                id = UUID.randomUUID().toString(),
                title = itemTitle,
                description = itemDescription,
                priority = itemPriority,
                createdAt = System.currentTimeMillis(),
                categoryId= itemCategory
            )
            sharedViewModel.insertItem(itemEntity)
        }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
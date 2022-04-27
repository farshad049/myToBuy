package com.farshad.mytodo.ui.add

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

        //check if data has been successfully saved then reset the form
        sharedViewModel.transactionCompleteLiveData.observe(viewLifecycleOwner){complete ->
            if (complete){
                //after click on save button ,run this so it exits this fragment
                if (isInEditMode){
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

        //in update mode check if selectedItemEntity in not empty the do this
        // .let means like if(selectedItemEntity != null)
        selectedItemEntity?.let { itemEntity ->
            isInEditMode=true
            binding.etEditTitle.setText(itemEntity.title)
            //set mouse pointer at the end of text just fill more than we are in edit mode
            binding.etEditTitle.setSelection(itemEntity.title.length)
            binding.etEditDescription.setText(itemEntity.description)
            when(itemEntity.priority){
                1 -> binding.radioGroup.check(R.id.radioButtonLow)
                2 -> binding.radioGroup.check(R.id.radioButtonMedium)
                else -> binding.radioGroup.check(R.id.radioButtonHigh)
            }
            binding.saveButton.text="update"
            mainActivity.supportActionBar?.title="update item"
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

        if (isInEditMode){
            val itemEntity=selectedItemEntity!!.copy(
                title =itemTitle,
                description = itemDescription,
                priority = itemPriority
            )
            sharedViewModel.updateItem(itemEntity)
            //this line says it is not necessary to continue and run function below
            return
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





    override fun onPause() {
        //transactionCompleteLiveData is stored in activity so when we insert a data and set in to tru it will stands true,even when we leave the page
        //because of that we set it to false in onPause in order to cover thi issue
        sharedViewModel.transactionCompleteLiveData.postValue(false)
        super.onPause()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
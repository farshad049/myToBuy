package com.farshad.mytodo.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.ui.AppBarConfiguration
import com.farshad.mytodo.R
import com.farshad.mytodo.database.entity.ItemEntity
import com.farshad.mytodo.databinding.FragmentHomeBinding
import com.farshad.mytodo.ui.BaseFragment

class HomeFragment:BaseFragment(),ItemEntityInterface {


    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val controller=HomeEpoxyController(this)
        binding.epoxyRecyclerView.setController(controller)

        sharedViewModel.itemEntityLiveData.observe(viewLifecycleOwner){itemEntityList ->
            controller.itemEntity=itemEntityList as ArrayList<ItemEntity>
        }
        binding.fab.setOnClickListener {
            navigateViaNavGraph(R.id.action_homeFragment_to_addItemEntityFragment)
        }













    }//FUN

    override fun onDeleteItemEntity(itemEntity: ItemEntity) {
       sharedViewModel.deleteItem(itemEntity)
    }

    override fun onBumpPriority(itemEntity: ItemEntity) {
      //  TODO("Not yet implemented")
    }






















    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
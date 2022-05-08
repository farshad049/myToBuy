package com.farshad.mytodo.ui.home

import android.os.Bundle
import android.view.*
import androidx.navigation.NavController
import androidx.navigation.ui.AppBarConfiguration
import com.airbnb.epoxy.EpoxyTouchHelper
import com.farshad.mytodo.R
import com.farshad.mytodo.database.entity.ItemEntity
import com.farshad.mytodo.database.entity.ItemWithCategoryEntity
import com.farshad.mytodo.databinding.FragmentHomeBinding
import com.farshad.mytodo.ui.BaseFragment
import com.farshad.mytodo.ui.home.bottomSheet.SortOrderBottomSheetDialogFragment

class HomeFragment:BaseFragment(),ItemEntityInterface {


    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    //we want this fragment to update the action bar in order to set sort menu
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val controller=HomeEpoxyController(this)
        binding.epoxyRecyclerView.setController(controller)

        sharedViewModel.homeViewStateLiveData.observe(viewLifecycleOwner){it ->
            controller.viewState=it
        }
        binding.fab.setOnClickListener {
            navigateViaNavGraph(R.id.action_homeFragment_to_addItemEntityFragment)
        }

        //enable swipe to delete
        EpoxyTouchHelper.initSwiping(binding.epoxyRecyclerView)
            .right()
            .withTarget(HomeEpoxyController.ItemEntityEpoxyModel::class.java)
            .andCallbacks(object :EpoxyTouchHelper.SwipeCallbacks<HomeEpoxyController.ItemEntityEpoxyModel>(){
                override fun onSwipeCompleted(
                    model: HomeEpoxyController.ItemEntityEpoxyModel?,
                    itemView: View?,
                    position: Int,
                    direction: Int
                ) {
                    val deletedItem=model?.item ?: return
                    sharedViewModel.deleteItem(deletedItem.itemEntity)
                }
            })















    }//FUN
    override fun onBumpPriority(itemEntity: ItemEntity) {
      val currentPriority=itemEntity.priority
        //in order to change the value of priority
      var newPriority=currentPriority +1
      if (newPriority >3){
          newPriority = 1
      }
      val updatedItem=itemEntity.copy(priority = newPriority)
        sharedViewModel.updateItem(updatedItem)
    }

    override fun onItemClicked(itemEntity: ItemEntity) {
        val navDirections=HomeFragmentDirections.actionHomeFragmentToAddItemEntityFragment(itemEntity.id)
        navigateViaNavGraphSafeArg(navDirections)
    }
    //enable select menu item sort
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_home_fragment,menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == R.id.menuItemSort){
            SortOrderBottomSheetDialogFragment().show(childFragmentManager,null)
            true
        }else{
            super.onOptionsItemSelected(item)
        }
    }





    override fun onResume() {
        super.onResume()
        mainActivity.hideKeyboard(requireView())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
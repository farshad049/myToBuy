package com.farshad.mytodo.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavDirections
import com.farshad.mytodo.arch.ToBuyViewModel
import com.farshad.mytodo.database.AppDatabase

abstract class BaseFragment:Fragment() {
    protected val mainActivity:MainActivity
        get() = activity as MainActivity

    protected val appDatabase:AppDatabase
    get() = AppDatabase.getDatabase(requireActivity())

    //keep data in activity level, means even if fragment destroy data will last
    val sharedViewModel:ToBuyViewModel by activityViewModels()

    // region Navigation helper methods
    protected fun navigateUp() {
        mainActivity.navController.navigateUp()
    }

    // take the action id and sent us to destination
    protected fun navigateViaNavGraph(actionId: Int) {
        mainActivity.navController.navigate(actionId)
    }

    protected fun navigateViaNavGraphSafeArg(navDirections: NavDirections) {
        mainActivity.navController.navigate(navDirections)
    }

}
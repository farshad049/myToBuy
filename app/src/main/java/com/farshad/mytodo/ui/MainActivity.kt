package com.farshad.mytodo.ui

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.farshad.mytodo.R
import com.farshad.mytodo.arch.ToBuyViewModel
import com.farshad.mytodo.database.AppDatabase
import com.farshad.mytodo.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    private var binding: ActivityMainBinding?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        //mainActivity initialize the data for us as we enter the application
        val viewModel:ToBuyViewModel by viewModels()
        viewModel.init(AppDatabase.getDatabase(this))

        //enable the nav controller
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        navController = navHostFragment.navController

        //enable the action bar
        appBarConfiguration=
            //set up this fragments to be as top level fragments in order to don't show toolbar back button in this fragment
            AppBarConfiguration(setOf(R.id.homeFragment,R.id.profileFragment))
        //set up fragment title in toolbar
        setupActionBarWithNavController(navController,appBarConfiguration)

        // Setup bottom nav bar
        val navBar=findViewById<BottomNavigationView>(R.id.bottomNavigation)
        NavigationUI.setupWithNavController(navBar, navController)

        //set up our nav bar to be show/hide based on destination
        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            //if we were not in a top level fragment don't show the nav bar
            if (appBarConfiguration.topLevelDestinations.contains(destination.id)){
                navBar.isVisible=true
            }else{
                navBar.isGone=true
            }
        }



    }//FUN
















    fun hideKeyboard(view: View) {
        val imm: InputMethodManager =
            application.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    fun showKeyboard() {
        val imm: InputMethodManager =
            application.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
    }

    //enable back button on action bar
    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}
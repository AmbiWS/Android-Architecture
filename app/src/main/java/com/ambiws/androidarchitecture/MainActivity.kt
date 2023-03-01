package com.ambiws.androidarchitecture

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.ambiws.androidarchitecture.databinding.ActivityMainBinding
import com.ambiws.androidarchitecture.features.splash.SplashFragment
import com.ambiws.androidarchitecture.utils.extensions.subscribe
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val mainViewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewBinding()
        initNavigation()
    }

    private fun initViewBinding() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }

    private fun initNavigation() {
        if (getApplicationContainerFragment() != null) {
            startNavigationFlow()
        } else {
            initNavigationGraph()
        }
    }

    private fun startNavigationFlow() {
        getApplicationContainerFragment()?.let { navigationHostFragment ->
            initDashboardNavigation(navigationHostFragment)
            activateSplashScreen()
        } ?: throw IllegalStateException(getString(R.string.exception_null_app_container_fragment))
    }

    private fun initNavigationGraph() {
        mainViewModel.initStartDestination()
        subscribe(mainViewModel.startDestinationEvent) { destination ->
            val navigationHostFragment = NavHostFragment()
            supportFragmentManager.beginTransaction()
                .replace(R.id.applicationContainerLayout, navigationHostFragment)
                .setPrimaryNavigationFragment(navigationHostFragment)
                .commitNow()

            val navigationGraph =
                navigationHostFragment.navController.navInflater.inflate(R.navigation.navigation_main)
            navigationGraph.setStartDestination(destination)
            navigationHostFragment.navController.setGraph(navigationGraph, null)

            startNavigationFlow()
        }
    }

    private fun initDashboardNavigation(navigationHostFragment: Fragment) {
        navigationHostFragment.findNavController()
            .addOnDestinationChangedListener { _, _, _ ->
                // TODO implement navigation for dashboard
            }
    }

    private fun activateSplashScreen() {
        val splashFragment = supportFragmentManager.findFragmentById(R.id.splashContainerLayout) as SplashFragment
        splashFragment.runSplashAnimation {
            supportFragmentManager.beginTransaction().remove(splashFragment).commitNow()
            binding.mainLayout.removeView(binding.splashContainerLayout)
        }
    }

    private fun getApplicationContainerFragment() =
        supportFragmentManager.findFragmentById(R.id.applicationContainerLayout)
}

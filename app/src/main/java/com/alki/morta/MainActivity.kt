package com.alki.morta

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.alki.morta.databinding.ActivityMainBinding
import com.alki.morta.domain.AppRepository
import com.alki.morta.network.MortaAppService
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navController: NavController
    @Inject lateinit var repository: AppRepository;
    @Inject lateinit var service: MortaAppService;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val isConnected: Boolean
        val cm = application.applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (android.os.Build.VERSION.SDK_INT < 23) {

            val activeNetwork = cm.activeNetworkInfo
            isConnected = activeNetwork?.isConnectedOrConnecting == true
        } else {
            var capabilities = cm.getNetworkCapabilities(cm.activeNetwork)
            isConnected = capabilities != null
                       && capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                       && capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)

        }

        if (isConnected)
            CoroutineScope(Dispatchers.Default).launch { repository.refresh()}
        else {
            CoroutineScope(Dispatchers.Default).launch { repository.refreshOnlyLocal()}
            Toast.makeText(
                application.applicationContext,
                "Отсутствует подключение к интернету, приложение работает в оффлайн режиме",
                Toast.LENGTH_LONG
            ).show()
        }

        val binding =
            DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        drawerLayout = binding.drawerLayout
        NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout)
        NavigationUI.setupWithNavController(binding.navView, navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController, drawerLayout)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(item, navController)
                || super.onOptionsItemSelected(item)
    }

}
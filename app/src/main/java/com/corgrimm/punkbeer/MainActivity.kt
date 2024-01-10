package com.corgrimm.punkbeer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.MaterialTheme
import com.corgrimm.punkbeer.ui.MainScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: BeerViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                MainScreen(onNavigationIconClick = ::onNavigationIconClick, viewModel = viewModel)
            }
        }
    }

    /**
     * When the navigator cannot navigate; means it reaches the startDestination and it is
     * a good signal to finish this activity.
     */
    private fun onNavigationIconClick(navigated: Boolean) {
        if (!navigated) finish()
    }
}
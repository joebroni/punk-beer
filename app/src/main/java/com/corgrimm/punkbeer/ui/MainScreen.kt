package com.corgrimm.punkbeer.ui

import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.corgrimm.punkbeer.BeerViewModel
import com.corgrimm.punkbeer.R

const val BEER_LIST_ROUTE = "Punk Beers"
const val BEER_DETAILS_ROUTE = "Beer Details"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    onNavigationIconClick: (navigated: Boolean) -> Unit,
    viewModel: BeerViewModel,
) {
    val navHostController = rememberNavController()
    val navBackStackEntry by navHostController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val title =
        stringResource(id = if (currentRoute?.contains(BEER_DETAILS_ROUTE) == true) R.string.details else R.string.punk_beer)
    MaterialTheme() {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = title) },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        titleContentColor = MaterialTheme.colorScheme.onPrimary,
                        navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
                        actionIconContentColor = MaterialTheme.colorScheme.onSecondary
                    ),
                    navigationIcon = {
                        if (currentRoute != null) {
                            if (currentRoute.contains(BEER_DETAILS_ROUTE)) {
                                IconButton(onClick = { navHostController.navigateUp() }) {
                                    Icon(
                                        imageVector = Icons.Filled.ArrowBack,
                                        contentDescription = stringResource(R.string.back_button)
                                    )
                                }
                            }
                        }
                    }
                )
            },
            content = {
                NavHost(
                    navController = navHostController,
                    startDestination = BEER_LIST_ROUTE,
                    modifier = Modifier.padding(it),
                ) {
                    composable(route = BEER_LIST_ROUTE) {
                        BeerListScreen(
                            viewModel = viewModel,
                            navHostController
                        )
                    }
                    composable(route = "$BEER_DETAILS_ROUTE/{beerId}") {
                        val beerId = it.arguments?.getString("beerId")
                        val beer = viewModel.getBeerById(beerId?.toIntOrNull() ?: -1)
                        // Navigate to BeerDetailsScreen and pass the beer details
                        if (beer != null) {
                            BeerDetailsScreen(beer = beer)
                        }
                    }
                }
            }
        )
    }
}
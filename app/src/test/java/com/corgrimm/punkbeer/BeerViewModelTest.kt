package com.corgrimm.punkbeer

import com.corgrimm.punkbeer.Resource.Loading
import com.corgrimm.punkbeer.models.Amount
import com.corgrimm.punkbeer.models.Beer
import com.corgrimm.punkbeer.models.Fermentation
import com.corgrimm.punkbeer.models.Hop
import com.corgrimm.punkbeer.models.Ingredients
import com.corgrimm.punkbeer.models.Malt
import com.corgrimm.punkbeer.models.MashTemp
import com.corgrimm.punkbeer.models.Method
import com.corgrimm.punkbeer.models.Temp
import com.corgrimm.punkbeer.models.Volume
import com.corgrimm.punkbeer.repository.PunkBeerRepo
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestWatcher
import org.junit.runner.Description

@ExperimentalCoroutinesApi
class BeerViewModelTest {

    private lateinit var beerViewModel: BeerViewModel
    private lateinit var punkBeerRepo: PunkBeerRepo

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Before
    fun setUp() {
        punkBeerRepo = mockk(relaxed = true)
        beerViewModel = BeerViewModel(punkBeerRepo)
    }

    @Test
    fun `fetchBeers emits Loading statee`() = runTest() {
        // Given
        coEvery { punkBeerRepo.beerListFlow() } returns flowOf(
            Loading(),
        )

        // When
        beerViewModel.fetchBeers()

        // Then
        val state = beerViewModel.state.value
        assertTrue(state.isLoading)
        assertEquals(0, state.beers.size)
        assertEquals("", state.errorMessage)
    }

    @Test
    fun `fetchBeers emits Error state`() = runTest() {
        // Given
        val errorMessage = "Error fetching beers"
        coEvery { punkBeerRepo.beerListFlow() } returns flowOf(
            Resource.Error(errorMessage)
        )

        // When
        beerViewModel.fetchBeers()

        // Then
        val newState = beerViewModel.state.value
        assertFalse(newState.isLoading)
        assertEquals(0, newState.beers.size)
        assertEquals(errorMessage, newState.errorMessage)
    }

    @Test
    fun `getBeerById returns the correct beer`() = runTest() {
        // Given
        coEvery { punkBeerRepo.beerListFlow() } returns flowOf(Resource.Success(beerList))

        // When
        beerViewModel.fetchBeers()

        // Then
        val beerId = 1
        val beer = beerViewModel.getBeerById(beerId)
        assertEquals(beerList.firstOrNull { it.id == beerId }, beer)
    }

    val beerList = listOf(
        Beer(
            id = 1,
            name = "Punk IPA",
            tagline = "Post Modern Classic. Spiky. Tropical. Hoppy.",
            firstBrewed = "04/2007",
            description = "Our flagship beer that kick-started the craft beer revolution.",
            imageUrl = "https://images.punkapi.com/v2/keg.png",
            abv = 5.6,
            ibu = 41.5,
            targetFg = 1010.0,
            targetOg = 1056.0,
            ebc = 17.0,
            srm = 8.5,
            ph = 4.4,
            attenuationLevel = 81.82,
            volume = Volume(value = 20.0, unit = "liters"),
            boilVolume = Volume(value = 25.0, unit = "liters"),
            method = Method(
                mashTemp = listOf(
                    MashTemp(temp = Temp(value = 65.0, unit = "celsius"), duration = 75)
                ),
                fermentation = Fermentation(temp = Temp(value = 19.0, unit = "celsius")),
                twist = null
            ),
            ingredients = Ingredients(
                malt = listOf(
                    Malt(name = "Extra Pale", amount = Amount(value = 5.3, unit = "kg")),
                    Malt(name = "Caramalt", amount = Amount(value = 0.2, unit = "kg"))
                ),
                hops = listOf(
                    Hop(
                        name = "Ahtanum",
                        amount = Amount(value = 17.5, unit = "g"),
                        add = "start",
                        attribute = "bitter"
                    ),
                    Hop(
                        name = "Chinook",
                        amount = Amount(value = 15.0, unit = "g"),
                        add = "start",
                        attribute = "bitter"
                    ),
                    Hop(
                        name = "Ahtanum",
                        amount = Amount(value = 10.0, unit = "g"),
                        add = "middle",
                        attribute = "flavour"
                    ),
                    Hop(
                        name = "Chinook",
                        amount = Amount(value = 10.0, unit = "g"),
                        add = "middle",
                        attribute = "flavour"
                    ),
                    Hop(
                        name = "Ahtanum",
                        amount = Amount(value = 17.5, unit = "g"),
                        add = "end",
                        attribute = "flavour"
                    )
                ),
                yeast = "Wyeast 1056 - American Ale™"
            ),
            foodPairing = listOf(
                "Fresh crab with lemon",
                "Garlic butter dipping sauce",
                "Goats cheese salad",
                "Creamy lemon bar doused in powdered sugar."
            ),
            brewersTips = "Highly recommended to use the freshest ingredients.",
            contributedBy = "Sam Mason <samjbmason>"
        ),
        Beer(
            id = 2,
            name = "Sparkle Pony Club",
            tagline = "Session Pale Ale.",
            firstBrewed = "04/2012",
            description = "A low-alcohol, high-impact, hop-forward beer.",
            imageUrl = "https://images.punkapi.com/v2/dead_pony.png",
            abv = 3.8,
            ibu = 40.0,
            targetFg = 1010.0,
            targetOg = 1040.0,
            ebc = 15.0,
            srm = 7.5,
            ph = 4.2,
            attenuationLevel = 75.0,
            volume = Volume(value = 20.0, unit = "liters"),
            boilVolume = Volume(value = 25.0, unit = "liters"),
            method = Method(
                mashTemp = listOf(
                    MashTemp(temp = Temp(value = 67.0, unit = "celsius"), duration = 60)
                ),
                fermentation = Fermentation(temp = Temp(value = 18.0, unit = "celsius")),
                twist = "Dry Hopped with Simcoe for an aromatic kick."
            ),
            ingredients = Ingredients(
                malt = listOf(
                    Malt(name = "Extra Pale", amount = Amount(value = 3.5, unit = "kg")),
                    Malt(name = "Caramalt", amount = Amount(value = 0.2, unit = "kg"))
                ),
                hops = listOf(
                    Hop(
                        name = "Simcoe",
                        amount = Amount(value = 15.0, unit = "g"),
                        add = "start",
                        attribute = "bitter"
                    ),
                    Hop(
                        name = "Simcoe",
                        amount = Amount(value = 10.0, unit = "g"),
                        add = "middle",
                        attribute = "flavour"
                    ),
                    Hop(
                        name = "Simcoe",
                        amount = Amount(value = 17.5, unit = "g"),
                        add = "end",
                        attribute = "flavour"
                    )
                ),
                yeast = "Wyeast 1056 - American Ale™"
            ),
            foodPairing = listOf(
                "Grilled chicken wings",
                "Spicy goat cheese salad",
                "Citrus-marinated salmon",
                "Key lime pie"
            ),
            brewersTips = "Experiment with different dry hop varieties for unique flavors.",
            contributedBy = "John Doe <johndoe>"
        )
    )
}

@OptIn(ExperimentalCoroutinesApi::class)
class MainDispatcherRule(
    private val testDispatcher: TestDispatcher = UnconfinedTestDispatcher()
) : TestWatcher() {
    override fun starting(description: Description) {
        Dispatchers.setMain(testDispatcher)
    }

    override fun finished(description: Description) {
        Dispatchers.resetMain()
    }
}
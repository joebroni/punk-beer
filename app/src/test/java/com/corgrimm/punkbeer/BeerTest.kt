package com.corgrimm.punkbeer

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
import io.mockk.core.ValueClassSupport.boxedValue
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test


class BeerTest {

    @Test
    fun createBeer() {
        // Given
        val beer = Beer(
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
        )

        // Then
        assertEquals(1, beer.id)
        assertEquals("Punk IPA", beer.name)
        assertEquals("Post Modern Classic. Spiky. Tropical. Hoppy.", beer.tagline)
        assertEquals("04/2007", beer.firstBrewed)
        assertEquals("Our flagship beer that kick-started the craft beer revolution.", beer.description)
        assertEquals("https://images.punkapi.com/v2/keg.png", beer.imageUrl)
        assertEquals(5.6, beer.abv, 0.0)
        assertEquals(41.5, beer.ibu, 0.0)
        assertEquals(1010.0, beer.targetFg, 0.0)
        assertEquals(1056.0, beer.targetOg, 0.0)
        assertEquals(17.0, beer.ebc, 0.0)
        assertEquals(8.5, beer.srm, 0.0)
        assertEquals(4.4, beer.ph, 0.0)
        assertEquals(81.82, beer.attenuationLevel, 0.0)
        assertEquals(Volume(value = 20.0, unit = "liters"), beer.volume)
        assertEquals(Volume(value = 25.0, unit = "liters"), beer.boilVolume)

        assertEquals(
            Method(
                mashTemp = listOf(MashTemp(temp = Temp(value = 65.0, unit = "celsius"), duration = 75)),
                fermentation = Fermentation(temp = Temp(value = 19.0, unit = "celsius")),
                twist = null
            ),
            beer.method
        )

        assertEquals(
            Ingredients(
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
            beer.ingredients
        )

        assertEquals(
            listOf(
                "Fresh crab with lemon",
                "Garlic butter dipping sauce",
                "Goats cheese salad",
                "Creamy lemon bar doused in powdered sugar."
            ),
            beer.foodPairing
        )

        assertEquals("Highly recommended to use the freshest ingredients.", beer.brewersTips)
        assertEquals("Sam Mason <samjbmason>", beer.contributedBy)
    }
}
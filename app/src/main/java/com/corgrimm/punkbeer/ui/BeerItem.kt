package com.corgrimm.punkbeer.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
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

@Composable
fun BeerItem(beer: Beer, onItemClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable { onItemClick() },
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        )
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
        ) {
            // Display beer image using Coil
            AsyncImage(
                model = beer.imageUrl,
                contentDescription = null,
                modifier = Modifier
                    .wrapContentWidth()
                    .height(200.dp)
                    .clip(shape = RoundedCornerShape(8.dp))
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column {
                // Display beer details
                Text(
                    text = beer.name,
                    style = MaterialTheme.typography.headlineSmall
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = beer.tagline,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.secondary
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = beer.description,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewBeerItem() {
    val dummyBeer = Beer(
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
                Hop(name = "Ahtanum", amount = Amount(value = 17.5, unit = "g"), add = "start", attribute = "bitter"),
                Hop(name = "Chinook", amount = Amount(value = 15.0, unit = "g"), add = "start", attribute = "bitter"),
                Hop(name = "Ahtanum", amount = Amount(value = 10.0, unit = "g"), add = "middle", attribute = "flavour"),
                Hop(name = "Chinook", amount = Amount(value = 10.0, unit = "g"), add = "middle", attribute = "flavour"),
                Hop(name = "Ahtanum", amount = Amount(value = 17.5, unit = "g"), add = "end", attribute = "flavour")
            ),
            yeast = "Wyeast 1056 - American Ale™"
        ),
        foodPairing = listOf("Fresh crab with lemon", "Garlic butter dipping sauce", "Goats cheese salad", "Creamy lemon bar doused in powdered sugar."),
        brewersTips = "Highly recommended to use the freshest ingredients.",
        contributedBy = "Sam Mason <samjbmason>"
    )

//    BeerItem(beer = dummyBeer)
}
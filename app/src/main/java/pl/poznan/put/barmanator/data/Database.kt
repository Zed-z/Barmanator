package pl.poznan.put.barmanator.data

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.graphics.BitmapFactory
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.client.plugins.contentnegotiation.*
import kotlinx.serialization.json.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.runBlocking
import io.ktor.client.call.body
import io.ktor.client.plugins.HttpTimeout

import kotlinx.serialization.Serializable
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter


@SuppressLint("UnsafeOptInUsageError")
@Serializable
data class CocktailResponse(
    val drinks: List<Cocktail>
)

@SuppressLint("UnsafeOptInUsageError")
@Serializable
data class Cocktail (
    val idDrink: Long,
    val strDrink: String?,
    val strCategory: String?,
    val strAlcoholic: String?,
    val strGlass: String?,
    val strInstructions: String?,
    val strDrinkThumb: String?,
    val strIngredient1: String?,
    val strIngredient2: String?,
    val strIngredient3: String?,
    val strIngredient4: String?,
    val strIngredient5: String?,
    val strIngredient6: String?,
    val strIngredient7: String?,
    val strIngredient8: String?,
    val strIngredient9: String?,
    val strIngredient10: String?,
    val strIngredient11: String?,
    val strIngredient12: String?,
    val strIngredient13: String?,
    val strIngredient14: String?,
    val strIngredient15:String?,
    val strMeasure1: String?,
    val strMeasure2: String?,
    val strMeasure3: String?,
    val strMeasure4: String?,
    val strMeasure5: String?,
    val strMeasure6: String?,
    val strMeasure7: String?,
    val strMeasure8: String?,
    val strMeasure9: String?,
    val strMeasure10: String?,
    val strMeasure11: String?,
    val strMeasure12: String?,
    val strMeasure13: String?,
    val strMeasure14: String?,
    val strMeasure15: String?,


    val strCreativeCommonsConfirmed: String?,

    )

data class Drink(
    val id: Long,
    val name: String,
    val category: String,
    val instructions: String,

    val ingredients: List<String> = ArrayList<String>(),
    val measures: List<String> =ArrayList<String>(),
    val ingredientsMeasutes: List<Pair<String, String>>,

    val image : String?


)

class CocktailDatabase


class Database(context: Context): SQLiteOpenHelper(context, "Database", null, 1) {
    companion object {
        private const val DATABASE_NAME = "database.db"
        private const val DATABASE_VERSION = 1

        const val TABLE_NAME = "Drinks"

        const val IDDRINK = "idDrink"
        const val STRDRINK = "strDrink"
        const val STRCATEGORY = "strCategory"
        const val STRALCOHOLIC = "strAlcoholic"
        const val STRGLASS = "strGlass"
        const val STRINSTRUCTIONS = "strInstructions"
        const val STRDRINKTHUMB = "strDrinkThumb"
        const val STRINGREDIENT1 = "strIngredient1"
        const val STRINGREDIENT2 = "strIngredient2"
        const val STRINGREDIENT3 = "strIngredient3"
        const val STRINGREDIENT4 = "strIngredient4"
        const val STRINGREDIENT5 = "strIngredient5"
        const val STRINGREDIENT6 = "strIngredient6"
        const val STRINGREDIENT7 = "strIngredient7"
        const val STRINGREDIENT8 = "strIngredient8"
        const val STRINGREDIENT9 = "strIngredient9"
        const val STRINGREDIENT10 = "strIngredient10"
        const val STRINGREDIENT11 = "strIngredient11"
        const val STRINGREDIENT12 = "strIngredient12"
        const val STRINGREDIENT13 = "strIngredient13"
        const val STRINGREDIENT14 = "strIngredient14"
        const val STRINGREDIENT15 = "strIngredient15"
        const val STRMEASURE1 = "strMeasure1"
        const val STRMEASURE2 = "strMeasure2"
        const val STRMEASURE3 = "strMeasure3"
        const val STRMEASURE4 = "strMeasure4"
        const val STRMEASURE5 = "strMeasure5"
        const val STRMEASURE6 = "strMeasure6"
        const val STRMEASURE7 = "strMeasure7"
        const val STRMEASURE8 = "strMeasure8"
        const val STRMEASURE9 = "strMeasure9"
        const val STRMEASURE10 = "strMeasure10"
        const val STRMEASURE11 = "strMeasure11"
        const val STRMEASURE12 = "strMeasure12"
        const val STRMEASURE13 = "strMeasure13"
        const val STRMEASURE14 = "strMeasure14"
        const val STRMEASURE15 = "strMeasure15"
        const val STRCREATIVECOMMONSCONFIRMED = "strCreativeCommonsConfirmed"
    }

    override fun onCreate(db: SQLiteDatabase)  {
        val query = """
            CREATE TABLE $TABLE_NAME (
                $IDDRINK INTEGER PRIMARY KEY,
                $STRDRINK TEXT ,
                $STRCATEGORY TEXT ,
                $STRALCOHOLIC TEXT ,
                $STRGLASS TEXT ,
                $STRINSTRUCTIONS TEXT ,
                $STRDRINKTHUMB TEXT ,
                $STRINGREDIENT1 TEXT ,
                $STRINGREDIENT2 TEXT ,
                $STRINGREDIENT3 TEXT ,
                $STRINGREDIENT4 TEXT ,
                $STRINGREDIENT5 TEXT ,
                $STRINGREDIENT6 TEXT ,
                $STRINGREDIENT7 TEXT ,
                $STRINGREDIENT8 TEXT ,
                $STRINGREDIENT9 TEXT ,
                $STRINGREDIENT10 TEXT ,
                $STRINGREDIENT11 TEXT ,
                $STRINGREDIENT12 TEXT ,
                $STRINGREDIENT13 TEXT ,
                $STRINGREDIENT14 TEXT ,
                $STRINGREDIENT15 TEXT ,
                $STRMEASURE1 TEXT ,
                $STRMEASURE2 TEXT ,
                $STRMEASURE3 TEXT ,
                $STRMEASURE4 TEXT ,
                $STRMEASURE5 TEXT ,
                $STRMEASURE6 TEXT ,
                $STRMEASURE7 TEXT ,
                $STRMEASURE8 TEXT ,
                $STRMEASURE9 TEXT ,
                $STRMEASURE10 TEXT ,
                $STRMEASURE11 TEXT ,
                $STRMEASURE12 TEXT ,
                $STRMEASURE13 TEXT ,
                $STRMEASURE14 TEXT ,
                $STRMEASURE15 TEXT ,
                $STRCREATIVECOMMONSCONFIRMED TEXT 

            )
        """.trimIndent()
        db.execSQL(query)

        runBlocking {
            queryCocktailAPI(db)
        }

    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {}

    fun getDrinks(): List<Drink> {
        val data = mutableListOf<Drink>()

        val cursor: Cursor = readableDatabase.query(TABLE_NAME, null, null, null, null, null, "$STRDRINK ASC")
        with(cursor) {
            while (moveToNext()) {
                val id = getLong(getColumnIndexOrThrow(IDDRINK))
                val name = getString(getColumnIndexOrThrow(STRDRINK))
                val category = getString(getColumnIndexOrThrow(STRCATEGORY))
                val instructions = getString(getColumnIndexOrThrow(STRINSTRUCTIONS))
                var url = getString(getColumnIndexOrThrow(STRDRINKTHUMB))


                val ingredients = mutableListOf<String>()
                val measures = mutableListOf<String>()

                for (i in 1..15) {
                    val ingredientCol = "strIngredient$i"
                    val measureCol = "strMeasure$i"

                    val ingredient = getString(getColumnIndexOrThrow(ingredientCol))
                    val measure = getString(getColumnIndexOrThrow(measureCol))


                    if (!ingredient.isNullOrBlank()) {
                        ingredients.add(ingredient)
                        measures.add(measure ?: "")
                    }
                }

                val ingredientsMeasures = ingredients.zip(measures)
                data.add(Drink(id, name, category, instructions,ingredients,measures,ingredientsMeasures,url))
            }
        }
        cursor.close()
        return data
    }

    fun drinkExists(idDrink: Long, database : SQLiteDatabase= readableDatabase): Boolean {
        val data = mutableListOf<Cocktail>()

        val selection = "$IDDRINK = ?"
        val selectionArgs = arrayOf(idDrink.toString())
        val cursor: Cursor = database.query(TABLE_NAME, arrayOf(IDDRINK), selection, selectionArgs, null, null, null)

        val exists = cursor.moveToFirst()
        cursor.close()
        return exists
    }

    private val jsonSettings = Json { ignoreUnknownKeys = true; prettyPrint = true; isLenient = true }

    suspend fun queryCocktailAPI(database: SQLiteDatabase) {
        // Create a Ktor client
        val client = HttpClient(CIO) {
            install(ContentNegotiation) {
                json(jsonSettings)
            }
            engine {
                requestTimeout = 15_000 // 15 sekund na całe żądanie
                endpoint {
                    connectTimeout = 10_000 // 10 sek. na nawiązanie połączenia
                    connectAttempts = 3     // próby połączenia
                }
            }

            install(HttpTimeout) {
                requestTimeoutMillis = 15_000
                connectTimeoutMillis = 10_000
                socketTimeoutMillis = 15_000
            }
        }

        for ( i in 0..30) {
            try {
                println("BBB")
                val response: HttpResponse =
                    client.get("https://www.thecocktaildb.com/api/json/v1/1/random.php") {
                        contentType(ContentType.Application.Json)
                    }
                // Parse response
                if (response.status == HttpStatusCode.OK) {
                    val responseBody = response.bodyAsText()
                    println("Response: $responseBody")
                    val result = jsonSettings.decodeFromString<CocktailResponse>(responseBody)
                    println("Parsed Result: ${result.drinks}")

                    for (cocktail in result.drinks) {



                            if (response.status == HttpStatusCode.OK) {

                                val values = ContentValues().apply {
                                    put("idDrink", cocktail.idDrink)
                                    put("strDrink", cocktail.strDrink)
                                    put("strCategory", cocktail.strCategory)
                                    put("strAlcoholic", cocktail.strAlcoholic)
                                    put("strGlass", cocktail.strGlass)
                                    put("strInstructions", cocktail.strInstructions)
                                    put("strDrinkThumb", cocktail.strDrinkThumb)
                                    put("strIngredient1", cocktail.strIngredient1)
                                    put("strIngredient2", cocktail.strIngredient2)
                                    put("strIngredient3", cocktail.strIngredient3)
                                    put("strIngredient4", cocktail.strIngredient4)
                                    put("strIngredient5", cocktail.strIngredient5)
                                    put("strIngredient6", cocktail.strIngredient6)
                                    put("strIngredient7", cocktail.strIngredient7)
                                    put("strIngredient8", cocktail.strIngredient8)
                                    put("strIngredient9", cocktail.strIngredient9)
                                    put("strIngredient10", cocktail.strIngredient10)
                                    put("strIngredient11", cocktail.strIngredient11)
                                    put("strIngredient12", cocktail.strIngredient12)
                                    put("strIngredient13", cocktail.strIngredient13)
                                    put("strIngredient14", cocktail.strIngredient14)
                                    put("strIngredient15", cocktail.strIngredient15)
                                    put("strMeasure1", cocktail.strMeasure1)
                                    put("strMeasure2", cocktail.strMeasure2)
                                    put("strMeasure3", cocktail.strMeasure3)
                                    put("strMeasure4", cocktail.strMeasure4)
                                    put("strMeasure5", cocktail.strMeasure5)
                                    put("strMeasure6", cocktail.strMeasure6)
                                    put("strMeasure7", cocktail.strMeasure7)
                                    put("strMeasure8", cocktail.strMeasure8)
                                    put("strMeasure9", cocktail.strMeasure9)
                                    put("strMeasure10", cocktail.strMeasure10)
                                    put("strMeasure11", cocktail.strMeasure11)
                                    put("strMeasure12", cocktail.strMeasure12)
                                    put("strMeasure13", cocktail.strMeasure13)
                                    put("strMeasure14", cocktail.strMeasure14)
                                    put("strMeasure15", cocktail.strMeasure15)
                                    put(
                                        "strCreativeCommonsConfirmed",
                                        cocktail.strCreativeCommonsConfirmed
                                    )


                                }

                                if (!drinkExists(cocktail.idDrink, database)) {
                                    database.insert(TABLE_NAME, null, values);
                                }
                            } else {

                                println(cocktail.strDrinkThumb.toString())
                            }

                    }
                } else {
                    println("Request failed with status: ${response.status}")
                }
            } catch (e: Exception) {
                println("Error occurred: ${e.localizedMessage}")
            }
        }

        client.close()
    }
}

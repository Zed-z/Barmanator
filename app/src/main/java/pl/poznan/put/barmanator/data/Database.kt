package pl.poznan.put.barmanator.data

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.client.plugins.contentnegotiation.*
import kotlinx.serialization.*
import kotlinx.serialization.*
import kotlinx.serialization.json.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.*
import io.ktor.util.logging.Logger
import io.ktor.client.*
import io.ktor.client.engine.cio.*

import io.ktor.http.*
import io.ktor.client.statement.*
import kotlinx.serialization.*
import kotlinx.serialization.json.*
import kotlinx.coroutines.*
import kotlinx.serialization.Serializable
import java.sql.PreparedStatement


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

data class Drink (
    val id: Long,
    val name: String,
    val category: String,
    val instructions: String,
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
        
//        db.insert(TABLE_NAME, null,   ContentValues().apply { put(COLUMN_NAME,"Zombie"); put(COLUMN_TAGLINE," Tropikalny potwór"); put(COLUMN_DESCRIPTION," Zombie to jeden z najbardziej intensywnych koktajli, który łączy kilka rodzajów rumu z owocowymi sokami i likierami. Jest mocny, pełen smaku i bardzo orzeźwiający."); put(COLUMN_PREPARATION," Przygotowanie: Wymieszaj rum, sok ananasowy, sok z limonki, grenadynę, maraschino i angosturę w shakerze z lodem, przelej do szklanki."); put(COLUMN_INGREDIENTS," Składniki: 30 ml białego rumu, 30 ml ciemnego rumu, 15 ml rumu overproof, 50 ml soku ananasowego, 25 ml soku z limonki, 10 ml grenadyny, 10 ml likieru maraschino, 2-3 krople angostury, kostki lodu")})
//        db.insert(TABLE_NAME, null,   ContentValues().apply { put(COLUMN_NAME,"White Russian"); put(COLUMN_TAGLINE," Pyszna klasyka"); put(COLUMN_DESCRIPTION," White Russian to koktajl, który łączy wódkę, likier kawowy i śmietankę, tworząc gładki, deserowy napój o bogatym smaku. Jest to idealny drink na wieczór."); put(COLUMN_PREPARATION," Przygotowanie: Wlej wódkę i likier kawowy do szklanki z lodem, dopełnij śmietanką."); put(COLUMN_INGREDIENTS," Składniki: 50 ml wódki, 30 ml likieru kawowego, 30 ml śmietanki, kostki lodu")})
//        db.insert(TABLE_NAME, null,   ContentValues().apply { put(COLUMN_NAME,"Whiskey Sour"); put(COLUMN_TAGLINE," Kwasowo-słodka przyjemność"); put(COLUMN_DESCRIPTION," Whiskey Sour to klasyczny koktajl, który łączy whisky z sokiem cytrynowym i syropem cukrowym, tworząc balans pomiędzy kwaskowatością a słodyczą."); put(COLUMN_PREPARATION," Przygotowanie: Wymieszaj składniki w shakerze z lodem, przelej do kieliszka koktajlowego."); put(COLUMN_INGREDIENTS," Składniki: 50 ml whisky, 25 ml soku z cytryny, 10 ml syropu cukrowego, kostki lodu")})
//        db.insert(TABLE_NAME, null,   ContentValues().apply { put(COLUMN_NAME,"Tuxedo"); put(COLUMN_TAGLINE," Klasyczna elegancja"); put(COLUMN_DESCRIPTION," Tuxedo to koktajl, który łączy w sobie gin, maraschino i absinthe. Jego złożony smak sprawia, że jest to napój wyrafinowany i idealny na specjalne okazje."); put(COLUMN_PREPARATION," Przygotowanie: Wymieszaj składniki w szklance z lodem, przelej do kieliszka koktajlowego."); put(COLUMN_INGREDIENTS," Składniki: 45 ml ginu, 15 ml likieru maraschino, 1 kropla absinthu, kostki lodu")})
//        db.insert(TABLE_NAME, null,   ContentValues().apply { put(COLUMN_NAME,"Tom Collins"); put(COLUMN_TAGLINE," Cytrusowa klasyka"); put(COLUMN_DESCRIPTION," Tom Collins to koktajl o wyważonym smaku, który łączy gin, sok cytrynowy i wodę gazowaną, tworząc napój orzeźwiający i lekki. Idealny na lato."); put(COLUMN_PREPARATION," Przygotowanie: Wymieszaj gin, sok z cytryny i cukier w shakerze z lodem, przelej do szklanki, dopełnij wodą gazowaną."); put(COLUMN_INGREDIENTS," Składniki: 50 ml ginu, 25 ml soku z cytryny, 10 ml syropu cukrowego, woda gazowana, kostki lodu")})
//        db.insert(TABLE_NAME, null,   ContentValues().apply { put(COLUMN_NAME,"Tequila Sunrise"); put(COLUMN_TAGLINE," Tropikalny wschód słońca"); put(COLUMN_DESCRIPTION," Tequila Sunrise to jeden z najbardziej kolorowych koktajli, który nie tylko smakuje wyśmienicie, ale również cieszy oko. Jego słodki smak z tequilą w roli głównej sprawia, że jest to drink, który świetnie pasuje na imprezy."); put(COLUMN_PREPARATION," Przygotowanie: Wlej tequilę i sok pomarańczowy do szklanki z lodem, delikatnie wlej grenadynę, aby uzyskać efekt wschodu słońca."); put(COLUMN_INGREDIENTS," Składniki: 50 ml tequili, 100 ml soku pomarańczowego, 10 ml grenadyny, kostki lodu")})
//        db.insert(TABLE_NAME, null,   ContentValues().apply { put(COLUMN_NAME,"Tequila Sour"); put(COLUMN_TAGLINE," Zrównoważony smak"); put(COLUMN_DESCRIPTION," Tequila Sour to koktajl łączący w sobie tequilę z sokiem cytrynowym i syropem cukrowym, tworząc napój o idealnej równowadze smaku – lekko kwaśny, ale z wyraźną nutą tequili."); put(COLUMN_PREPARATION," Przygotowanie: Wymieszaj wszystkie składniki w shakerze z lodem, przelej do kieliszka."); put(COLUMN_INGREDIENTS," Składniki: 50 ml tequili, 25 ml soku z cytryny, 10 ml syropu cukrowego, kostki lodu")})
//        db.insert(TABLE_NAME, null,   ContentValues().apply { put(COLUMN_NAME,"Strawberry Daiquiri"); put(COLUMN_TAGLINE," Owocowy raj"); put(COLUMN_DESCRIPTION," Strawberry Daiquiri to owocowa wersja klasycznego Daiquiri, która łączy rum, truskawki, sok z limonki i cukier. Jest to koktajl pełen słodyczy i świeżości."); put(COLUMN_PREPARATION," Przygotowanie: Zmiksuj wszystkie składniki w blenderze, przelej do szklanki."); put(COLUMN_INGREDIENTS," Składniki: 50 ml białego rumu, 10 truskawek, 25 ml soku z limonki, 10 ml syropu cukrowego, kostki lodu")})
//        db.insert(TABLE_NAME, null,   ContentValues().apply { put(COLUMN_NAME,"Shandy"); put(COLUMN_TAGLINE," Orzeźwiające połączenie"); put(COLUMN_DESCRIPTION," Shandy to napój, który łączy piwo z lemoniadą lub limonką, tworząc orzeźwiający i lekko alkoholowy drink. Idealny na letnie dni."); put(COLUMN_PREPARATION," Przygotowanie: Wlej piwo do szklanki, dopełnij lemoniadą lub sokiem limonkowym."); put(COLUMN_INGREDIENTS," Składniki: 150 ml piwa, 100 ml lemoniady lub soku limonkowego")})
//        db.insert(TABLE_NAME, null,   ContentValues().apply { put(COLUMN_NAME,"Sex on the Beach"); put(COLUMN_TAGLINE," Zmysłowa rozkosz"); put(COLUMN_DESCRIPTION," Sex on the Beach to koktajl, który łączy w sobie słodycz soku pomarańczowego, kwaśność żurawiny i delikatność wódki. Idealny na imprezy i letnie wieczory."); put(COLUMN_PREPARATION," Przygotowanie: Wymieszaj wszystkie składniki w shakerze z lodem, przelej do szklanki z lodem."); put(COLUMN_INGREDIENTS," Składniki: 40 ml wódki, 20 ml likieru brzoskwiniowego, 40 ml soku pomarańczowego, 40 ml soku żurawinowego, kostki lodu")})
//        db.insert(TABLE_NAME, null,   ContentValues().apply { put(COLUMN_NAME,"Sazerac"); put(COLUMN_TAGLINE," Nowoorleańska klasyka"); put(COLUMN_DESCRIPTION," Sazerac to klasyczny koktajl z Nowego Orleanu, który łączy żytną whisky z absinthem i angosturą. Jest to drink o wyjątkowym smaku i historii."); put(COLUMN_PREPARATION," Przygotowanie: Schłodź szklankę absinthem, wymieszaj whisky z angosturą i cukrem w szklance z lodem, przelej do schłodzonej szklanki."); put(COLUMN_INGREDIENTS," Składniki: 50 ml żytniej whisky, 2-3 krople angostury, 1 łyżeczka cukru, 1 kropla absinthu, kostki lodu")})
//        db.insert(TABLE_NAME, null,   ContentValues().apply { put(COLUMN_NAME,"Rum Runner"); put(COLUMN_TAGLINE," Tropikalna moc"); put(COLUMN_DESCRIPTION," Rum Runner to koktajl pełen owocowych smaków z dodatkiem rumu, banana, malin i soku pomarańczowego. Jest pełen energii i tropikalnego smaku."); put(COLUMN_PREPARATION," Przygotowanie: Wymieszaj składniki w shakerze z lodem, przelej do szklanki."); put(COLUMN_INGREDIENTS," Składniki: 30 ml białego rumu, 30 ml ciemnego rumu, 30 ml likieru bananowego, 30 ml likieru malinowego, 60 ml soku pomarańczowego, kostki lodu")})
//        db.insert(TABLE_NAME, null,   ContentValues().apply { put(COLUMN_NAME,"Rum Punch"); put(COLUMN_TAGLINE," Egzotyczna przygoda"); put(COLUMN_DESCRIPTION," Rum Punch to koktajl, który jest mieszanką kilku rodzajów rumu, soków owocowych i grenadyny. Jest bardzo słodki i orzeźwiający."); put(COLUMN_PREPARATION," Przygotowanie: Zmiksuj wszystkie składniki w shakerze z lodem, przelej do szklanki."); put(COLUMN_INGREDIENTS," Składniki: 40 ml rumu, 60 ml soku ananasowego, 60 ml soku pomarańczowego, 10 ml grenadyny, kostki lodu")})
//        db.insert(TABLE_NAME, null,   ContentValues().apply { put(COLUMN_NAME,"Raspberry Mojito"); put(COLUMN_TAGLINE," Soczysta alternatywa"); put(COLUMN_DESCRIPTION," Raspberry Mojito to wersja klasycznego Mojito z dodatkiem malin, które nadają mu delikatnej słodyczy i wyrazistości. To owocowa odmiana na każdą okazję."); put(COLUMN_PREPARATION," Przygotowanie: Ugnieć maliny, miętę, cukier i sok z limonki, dodaj rum, lód i wymieszaj."); put(COLUMN_INGREDIENTS," Składniki: 50 ml białego rumu, 8-10 liści mięty, 2 łyżeczki cukru, 25 ml soku z limonki, 10 malin, woda gazowana, kostki lodu")})
//        db.insert(TABLE_NAME, null,   ContentValues().apply { put(COLUMN_NAME,"Pisco Sour"); put(COLUMN_TAGLINE," Peruwiańska delicja"); put(COLUMN_DESCRIPTION," Pisco Sour to koktajl, który łączy peruwiańskie pisco z cytryną, cukrem i białkiem jajka, tworząc napój o gładkiej konsystencji i wyrazistym smaku."); put(COLUMN_PREPARATION," Przygotowanie: Wymieszaj wszystkie składniki w shakerze z lodem, przelej do kieliszka koktajlowego."); put(COLUMN_INGREDIENTS," Składniki: 50 ml pisco, 25 ml soku z limonki, 20 ml syropu cukrowego, 1 białko jajka, kostki lodu")})
//        db.insert(TABLE_NAME, null,   ContentValues().apply { put(COLUMN_NAME,"Pink Lady"); put(COLUMN_TAGLINE," Delikatna przyjemność"); put(COLUMN_DESCRIPTION," Pink Lady to koktajl, który łączy wódkę, sok cytrynowy i grenadynę, tworząc napój o subtelnym, owocowym smaku. Jest to koktajl idealny na eleganckie przyjęcia."); put(COLUMN_PREPARATION," Przygotowanie: Wymieszaj składniki w shakerze, przelej do kieliszka koktajlowego."); put(COLUMN_INGREDIENTS," Składniki: 30 ml wódki, 15 ml soku z cytryny, 10 ml grenadyny, kostki lodu")})
//        db.insert(TABLE_NAME, null,   ContentValues().apply { put(COLUMN_NAME,"Pineapple Mojito"); put(COLUMN_TAGLINE," Orzeźwiająca wariacja"); put(COLUMN_DESCRIPTION," Pineapple Mojito to wersja klasycznego mojito, w którym dodatek ananasa nadaje koktajlowi owocowego, tropikalnego smaku. Doskonały na lato."); put(COLUMN_PREPARATION," Przygotowanie: Ugnieć miętę, ananasa, cukier i sok z limonki w szklance, dodaj rum, lód i wodę gazowaną."); put(COLUMN_INGREDIENTS," Składniki: 50 ml białego rumu, 8-10 liści mięty, 2-3 kawałki ananasa, 2 łyżeczki cukru, 25 ml soku z limonki, woda gazowana, kostki lodu")})
//        db.insert(TABLE_NAME, null,   ContentValues().apply { put(COLUMN_NAME,"Piña Colada"); put(COLUMN_TAGLINE," Raj w szklance"); put(COLUMN_DESCRIPTION," Piña Colada to koktajl, który przenosi nas na tropikalne wyspy. Z połączenia rumu, mleka kokosowego i soku ananasowego powstaje drink pełen słodyczy i egzotycznych smaków."); put(COLUMN_PREPARATION," Przygotowanie: Zmiksuj składniki w blenderze z lodem, przelej do szklanki."); put(COLUMN_INGREDIENTS," Składniki: 50 ml białego rumu, 50 ml soku ananasowego, 25 ml mleka kokosowego, kostki lodu")})
//        db.insert(TABLE_NAME, null,   ContentValues().apply { put(COLUMN_NAME,"Paloma"); put(COLUMN_TAGLINE," Orzeźwiająca meksykańska przyjemność"); put(COLUMN_DESCRIPTION," Paloma to koktajl na bazie tequili, który łączy sok grejpfrutowy z limonką, tworząc napój o wyrazistym, ale orzeźwiającym smaku."); put(COLUMN_PREPARATION," Przygotowanie: Wymieszaj tequilę, sok z grejpfruta i sok z limonki w szklance z lodem, dopełnij wodą gazowaną."); put(COLUMN_INGREDIENTS," Składniki: 50 ml tequili, 100 ml soku grejpfrutowego, 25 ml soku z limonki, woda gazowana, kostki lodu")})
//        db.insert(TABLE_NAME, null,   ContentValues().apply { put(COLUMN_NAME,"Old Fashioned"); put(COLUMN_TAGLINE," Koktajl z historią"); put(COLUMN_DESCRIPTION," Old Fashioned to klasyczny koktajl, który kusi swoim prostym, ale wyrafinowanym smakiem. Połączenie bourbona, cukru i angostury sprawia, że jest to napój idealny dla miłośników whisky."); put(COLUMN_PREPARATION," Przygotowanie: Umieść cukier, angosturę i wodę w szklance, mieszaj, aż cukier się rozpuści. Dodaj whiskey, lód i wymieszaj."); put(COLUMN_INGREDIENTS," Składniki: 50 ml bourbona lub żytniej whisky, 1 kostka cukru, 2-3 krople angostury, kostki lodu")})
//        db.insert(TABLE_NAME, null,   ContentValues().apply { put(COLUMN_NAME,"Negroni"); put(COLUMN_TAGLINE," Włoska klasyka"); put(COLUMN_DESCRIPTION," Negroni to koktajl, który jest połączeniem ginu, czerwonego wermutu i Campari. Jest intensywny, goryczkowy, ale idealnie zbalansowany. Znany w barach na całym świecie."); put(COLUMN_PREPARATION," Przygotowanie: Wymieszaj składniki w szklance z lodem, przelej do szklanki niskiej."); put(COLUMN_INGREDIENTS," Składniki: 30 ml ginu, 30 ml Campari, 30 ml słodkiego wermutu, kostki lodu")})
//        db.insert(TABLE_NAME, null,   ContentValues().apply { put(COLUMN_NAME,"Moscow Mule"); put(COLUMN_TAGLINE," Orzeźwiająca siła imbiru"); put(COLUMN_DESCRIPTION," Moscow Mule to koktajl na bazie wódki, imbiru i limonki, który łączy ostry smak imbirowego piwa z cytrusową świeżością. Często serwowany w miedzianych kubkach."); put(COLUMN_PREPARATION," Przygotowanie: Wlej wódkę, sok z limonki do szklanki z lodem, dopełnij imbirowym piwem."); put(COLUMN_INGREDIENTS," Składniki: 50 ml wódki, 25 ml soku z limonki, 150 ml piwa imbirowego, kostki lodu")})
//        db.insert(TABLE_NAME, null,   ContentValues().apply { put(COLUMN_NAME,"Mojito"); put(COLUMN_TAGLINE," Orzeźwiająca świeżość"); put(COLUMN_DESCRIPTION," Mojito to koktajl, który doskonale łączy smak rumu, mięty i cytrusów. Jest niezwykle orzeźwiający i idealny na gorące dni. Jego słodko-kwaśna równowaga sprawia, że jest niezastąpiony w letnie wieczory."); put(COLUMN_PREPARATION," Przygotowanie: Ugnieć liście mięty, cukier i sok z limonki w szklance, dodaj rum i lód, a następnie dopełnij wodą gazowaną."); put(COLUMN_INGREDIENTS," Składniki: 50 ml białego rumu, 8-10 liści mięty, 2 łyżeczki cukru, 25 ml soku z limonki, woda gazowana, kostki lodu")})
//        db.insert(TABLE_NAME, null,   ContentValues().apply { put(COLUMN_NAME,"Mint Julep"); put(COLUMN_TAGLINE," Elegancka orzeźwiająca przyjemność"); put(COLUMN_DESCRIPTION," Mint Julep to koktajl klasyczny, pełen świeżości dzięki mięcie i bourbonowi. Popularny w Stanach Zjednoczonych, szczególnie w czasie wyścigów konnych Kentucky Derby."); put(COLUMN_PREPARATION," Przygotowanie: Ugnieć miętę z cukrem i wodą w szklance, dodaj bourbon i lód, wymieszaj."); put(COLUMN_INGREDIENTS," Składniki: 50 ml bourbona, 8-10 liści mięty, 2 łyżeczki cukru, 15 ml wody, kostki lodu")})
//        db.insert(TABLE_NAME, null,   ContentValues().apply { put(COLUMN_NAME,"Mimosa"); put(COLUMN_TAGLINE," Bąbelkowa przyjemność"); put(COLUMN_DESCRIPTION," Mimosa to koktajl łączący świeży sok pomarańczowy z szampanem. Jest to delikatny i elegancki napój, idealny na brunch."); put(COLUMN_PREPARATION," Przygotowanie: Wlej sok pomarańczowy do kieliszka szampana, dopełnij szampanem."); put(COLUMN_INGREDIENTS," Składniki: 100 ml soku pomarańczowego, 100 ml szampana")})
//        db.insert(TABLE_NAME, null,   ContentValues().apply { put(COLUMN_NAME,"Margarita"); put(COLUMN_TAGLINE," Klasyka w każdym łyku"); put(COLUMN_DESCRIPTION," Margarita to jeden z najbardziej popularnych koktajli na świecie. Łączy w sobie intensywny smak tequili z orzeźwiającym sokiem z limonki i słodyczą likieru triple sec. Idealny na każdą imprezę."); put(COLUMN_PREPARATION," Przygotowanie: Wymieszaj wszystkie składniki w shakerze z lodem. Przecedź do kieliszka z brzegiem oprószonym solą."); put(COLUMN_INGREDIENTS," Składniki: 50 ml tequili, 30 ml likieru triple sec, 25 ml soku z limonki, kostki lodu")})
//        db.insert(TABLE_NAME, null,   ContentValues().apply { put(COLUMN_NAME,"Mai Tai"); put(COLUMN_TAGLINE," Wyjątkowy koktajl tropikalny"); put(COLUMN_DESCRIPTION," Mai Tai to złożony koktajl, który łączy rum z sokiem limonkowym, syropem orgeat i grenadyną. Jest pełen owocowej świeżości i egzotycznych smaków."); put(COLUMN_PREPARATION," Przygotowanie: Zmiksuj składniki w shakerze, przelej do szklanki z lodem."); put(COLUMN_INGREDIENTS," Składniki: 40 ml białego rumu, 20 ml ciemnego rumu, 10 ml syropu orgeat, 10 ml grenadyny, 25 ml soku z limonki, kostki lodu")})
//        db.insert(TABLE_NAME, null,   ContentValues().apply { put(COLUMN_NAME,"Long Island Iced Tea"); put(COLUMN_TAGLINE," Mocna mieszanka"); put(COLUMN_DESCRIPTION," Long Island Iced Tea to koktajl, który łączy w sobie wiele rodzajów alkoholi i soków, tworząc wyjątkowo mocny napój. Pomimo swojej mocy, smakuje orzeźwiająco i owocowo."); put(COLUMN_PREPARATION," Przygotowanie: Wymieszaj składniki w shakerze z lodem, przelej do szklanki, dopełnij colą."); put(COLUMN_INGREDIENTS," Składniki: 20 ml wódki, 20 ml tequili, 20 ml rumu, 20 ml ginu, 20 ml triple sec, 25 ml soku cytrynowego, cola, kostki lodu")})
//        db.insert(TABLE_NAME, null,   ContentValues().apply { put(COLUMN_NAME,"Hibiscus Margarita"); put(COLUMN_TAGLINE," Kwiatowa podróż"); put(COLUMN_DESCRIPTION," Hibiscus Margarita to wyjątkowa wersja klasycznej margarity, z dodatkiem syropu hibiskusowego, który nadaje jej kwiatowego, lekko kwaskowatego smaku. Idealna na wyjątkowe okazje."); put(COLUMN_PREPARATION," Przygotowanie: Wymieszaj składniki w shakerze z lodem, przelej do kieliszka z solą na brzegu."); put(COLUMN_INGREDIENTS," Składniki: 50 ml tequili, 25 ml likieru triple sec, 25 ml soku z limonki, 10 ml syropu hibiskusowego, kostki lodu")})
//        db.insert(TABLE_NAME, null,   ContentValues().apply { put(COLUMN_NAME,"Golden Dream"); put(COLUMN_TAGLINE," Delikatna i złocista przyjemność"); put(COLUMN_DESCRIPTION," Golden Dream to koktajl na bazie likieru Cointreau, soku pomarańczowego i śmietany, który ma subtelną, kremową konsystencję."); put(COLUMN_PREPARATION," Przygotowanie: Wymieszaj składniki w shakerze z lodem, przelej do kieliszka."); put(COLUMN_INGREDIENTS," Składniki: 30 ml likieru Cointreau, 20 ml soku pomarańczowego, 30 ml śmietany, kostki lodu")})
//        db.insert(TABLE_NAME, null,   ContentValues().apply { put(COLUMN_NAME,"Gin Tonic"); put(COLUMN_TAGLINE," Prosta przyjemność"); put(COLUMN_DESCRIPTION," Gin Tonic to jeden z najprostszych, ale najbardziej orzeźwiających koktajli. Połączenie ginu i toniku tworzy klasyczny drink, który nigdy nie wychodzi z mody."); put(COLUMN_PREPARATION," Przygotowanie: Wlej gin do szklanki z lodem, dopełnij tonikiem, delikatnie wymieszaj."); put(COLUMN_INGREDIENTS," Składniki: 50 ml ginu, 150 ml toniku, kostki lodu")})
//        db.insert(TABLE_NAME, null,   ContentValues().apply { put(COLUMN_NAME,"Gin Fizz"); put(COLUMN_TAGLINE," Prosta orzeźwiająca przyjemność"); put(COLUMN_DESCRIPTION," Gin Fizz to koktajl łączący gin, sok z cytryny i wodę gazowaną. Jest lekki, orzeźwiający i idealny na gorące dni."); put(COLUMN_PREPARATION," Przygotowanie: Wymieszaj gin, sok z cytryny i syrop cukrowy w shakerze z lodem, przelej do szklanki i dopełnij wodą gazowaną."); put(COLUMN_INGREDIENTS," Składniki: 50 ml ginu, 25 ml soku z cytryny, 10 ml syropu cukrowego, woda gazowana, kostki lodu")})
//        db.insert(TABLE_NAME, null,   ContentValues().apply { put(COLUMN_NAME,"Gin Basil Smash"); put(COLUMN_TAGLINE," Zielona świeżość"); put(COLUMN_DESCRIPTION," Gin Basil Smash to koktajl, który łączy gin z świeżą bazylią i sokiem cytrynowym, tworząc napój pełen aromatu ziołowego i cytrusowego."); put(COLUMN_PREPARATION," Przygotowanie: Ugnieć bazylię z sokiem z cytryny i cukrem, dodaj gin i lód, wymieszaj."); put(COLUMN_INGREDIENTS," Składniki: 50 ml ginu, 8-10 liści bazylii, 25 ml soku z cytryny, 10 ml syropu cukrowego, kostki lodu")})
//        db.insert(TABLE_NAME, null,   ContentValues().apply { put(COLUMN_NAME,"Frozen Margarita"); put(COLUMN_TAGLINE," Zamrożona przyjemność"); put(COLUMN_DESCRIPTION," Frozen Margarita to mrożona wersja klasycznej margarity, która jest orzeźwiająca, zimna i idealna na letnie przyjęcia. Dzięki zmrożonym składnikom koktajl ma świetną konsystencję."); put(COLUMN_PREPARATION," Przygotowanie: Zmiksuj składniki z lodem w blenderze, przelej do kieliszka."); put(COLUMN_INGREDIENTS," Składniki: 50 ml tequili, 30 ml likieru triple sec, 25 ml soku z limonki, 1 szklanka lodu")})
//        db.insert(TABLE_NAME, null,   ContentValues().apply { put(COLUMN_NAME,"French 75"); put(COLUMN_TAGLINE," Elegancja w bąbelkach"); put(COLUMN_DESCRIPTION," French 75 to koktajl z bąbelkami szampana, który łączy w sobie gin, sok z cytryny i cukier. Jest to elegancki i orzeźwiający drink, idealny na specjalne okazje."); put(COLUMN_PREPARATION," Przygotowanie: Wymieszaj gin, sok z cytryny i syrop cukrowy w shakerze z lodem, przelej do kieliszka szampana, dopełnij szampanem."); put(COLUMN_INGREDIENTS," Składniki: 30 ml ginu, 15 ml soku z cytryny, 10 ml syropu cukrowego, szampan, kostki lodu")})
//        db.insert(TABLE_NAME, null,   ContentValues().apply { put(COLUMN_NAME,"Fallen Leaves"); put(COLUMN_TAGLINE," W jesiennej aurze"); put(COLUMN_DESCRIPTION," Fallen Leaves to koktajl na bazie whisky, likieru orzechowego i soku z cytryny. Ma wyraźny orzechowy posmak z nutą cytrusową, doskonały na chłodniejsze dni."); put(COLUMN_PREPARATION," Przygotowanie: Wymieszaj składniki w shakerze z lodem, przelej do szklanki."); put(COLUMN_INGREDIENTS," Składniki: 50 ml whisky, 20 ml likieru orzechowego, 15 ml soku z cytryny, kostki lodu")})
//        db.insert(TABLE_NAME, null,   ContentValues().apply { put(COLUMN_NAME,"Dirty Martini"); put(COLUMN_TAGLINE," Wersja z charakterem"); put(COLUMN_DESCRIPTION," Dirty Martini to wariacja na temat klasycznego Martini, w której dodaje się sok z oliwek, nadając mu bardziej intensywny smak."); put(COLUMN_PREPARATION," Przygotowanie: Wymieszaj gin, sok z oliwek i vermut w shakerze z lodem, przelej do kieliszka."); put(COLUMN_INGREDIENTS," Składniki: 50 ml ginu, 10 ml vermutu, 10 ml soku z oliwek, kostki lodu")})
//        db.insert(TABLE_NAME, null,   ContentValues().apply { put(COLUMN_NAME,"Daiquiri"); put(COLUMN_TAGLINE," Prosta elegancja"); put(COLUMN_DESCRIPTION," Daiquiri to koktajl, który w swojej prostocie łączy rum, sok z limonki i syrop cukrowy. Jest to napój o idealnej równowadze smaku, zarówno słodki, jak i kwaśny."); put(COLUMN_PREPARATION," Przygotowanie: Wymieszaj składniki w shakerze z lodem, przelej do kieliszka koktajlowego."); put(COLUMN_INGREDIENTS," Składniki: 50 ml białego rumu, 25 ml soku z limonki, 15 ml syropu cukrowego, kostki lodu")})
//        db.insert(TABLE_NAME, null,   ContentValues().apply { put(COLUMN_NAME,"Cuba Libre"); put(COLUMN_TAGLINE," Kubańska klasyka"); put(COLUMN_DESCRIPTION," Cuba Libre to prosty, ale pyszny koktajl, który łączy rum z colą i sokiem z limonki. Jest to napój orzeźwiający, który idealnie pasuje na każdą okazję."); put(COLUMN_PREPARATION," Przygotowanie: Wlej rum do szklanki z lodem, dopełnij colą i sokiem z limonki."); put(COLUMN_INGREDIENTS," Składniki: 50 ml rumu, 150 ml coli, sok z 1/2 limonki, kostki lodu")})
//        db.insert(TABLE_NAME, null,   ContentValues().apply { put(COLUMN_NAME,"Cosmopolitan"); put(COLUMN_TAGLINE," Elegancja w szkle"); put(COLUMN_DESCRIPTION," Cosmopolitan to koktajl pełen elegancji, który stał się symbolem klasyki koktajlowej. Dzięki wyważonemu połączeniu wódki, likieru triple sec i soku żurawinowego, stanowi idealny napój na wyrafinowane przyjęcia."); put(COLUMN_PREPARATION," Przygotowanie: Wymieszaj wszystkie składniki w shakerze z lodem. Przecedź do kieliszka koktajlowego."); put(COLUMN_INGREDIENTS," Składniki: 40 ml wódki, 15 ml likieru triple sec, 10 ml soku z limonki, 15 ml soku żurawinowego, kostki lodu")})
//        db.insert(TABLE_NAME, null,   ContentValues().apply { put(COLUMN_NAME,"Clover Club"); put(COLUMN_TAGLINE," Owocowa elegancja"); put(COLUMN_DESCRIPTION," Clover Club to koktajl, który łączy gin, malinowy syrop i sok z cytryny, tworząc napój pełen owocowego smaku i subtelnej kwasowości."); put(COLUMN_PREPARATION," Przygotowanie: Wymieszaj składniki w shakerze z lodem, przelej do kieliszka koktajlowego."); put(COLUMN_INGREDIENTS," Składniki: 50 ml ginu, 15 ml malinowego syropu, 25 ml soku z cytryny, 1 białko jajka, kostki lodu")})
//        db.insert(TABLE_NAME, null,   ContentValues().apply { put(COLUMN_NAME,"Cherry Brandy Sour"); put(COLUMN_TAGLINE," Owocowa klasyka"); put(COLUMN_DESCRIPTION," Cherry Brandy Sour to koktajl, który łączy w sobie likier wiśniowy z cytryną, tworząc napój o lekko kwaśnym i owocowym smaku. Idealny dla miłośników owocowych drinków."); put(COLUMN_PREPARATION," Przygotowanie: Wymieszaj likier wiśniowy, sok z cytryny i cukier w shakerze z lodem, przelej do kieliszka koktajlowego."); put(COLUMN_INGREDIENTS," Składniki: 50 ml cherry brandy, 25 ml soku z cytryny, 10 ml syropu cukrowego, kostki lodu")})
//        db.insert(TABLE_NAME, null,   ContentValues().apply { put(COLUMN_NAME,"Caipirinha"); put(COLUMN_TAGLINE," Brazylijska świeżość"); put(COLUMN_DESCRIPTION," Caipirinha to brazylijski koktajl, który bazuje na cachaça, limonce i cukrze. Jest to orzeźwiający drink, który idealnie oddaje tropikalny klimat Brazylii."); put(COLUMN_PREPARATION," Przygotowanie: Ugnieć limonkę z cukrem w szklance, dodaj cachaça i lód, delikatnie wymieszaj."); put(COLUMN_INGREDIENTS," Składniki: 50 ml cachaça, 1 limonka, 2 łyżeczki cukru, kostki lodu")})
//        db.insert(TABLE_NAME, null,   ContentValues().apply { put(COLUMN_NAME,"Bramble"); put(COLUMN_TAGLINE," Owocowy klasyk"); put(COLUMN_DESCRIPTION," Bramble to koktajl, który łączy w sobie gin z sokiem z cytryny i świeżymi owocami, co czyni go wyjątkowym i orzeźwiającym. Idealny na letnie wieczory."); put(COLUMN_PREPARATION," Przygotowanie: Wymieszaj gin, sok z cytryny i syrop cukrowy w shakerze z lodem, przelej do szklanki z lodem, a następnie wlej likier jeżynowy."); put(COLUMN_INGREDIENTS," Składniki: 40 ml ginu, 20 ml soku z cytryny, 10 ml syropu cukrowego, 20 ml likieru jeżynowego, kostki lodu")})
//        db.insert(TABLE_NAME, null,   ContentValues().apply { put(COLUMN_NAME,"Bourbon Lemonade"); put(COLUMN_TAGLINE," Słodka orzeźwiająca moc"); put(COLUMN_DESCRIPTION," Bourbon Lemonade to koktajl łączący bourbon z lemoniadą, który jest lekki, orzeźwiający i pełen smaku."); put(COLUMN_PREPARATION," Przygotowanie: Wymieszaj bourbon z lemoniadą w szklance z lodem."); put(COLUMN_INGREDIENTS," Składniki: 50 ml bourbona, 150 ml lemoniady, kostki lodu")})
//        db.insert(TABLE_NAME, null,   ContentValues().apply { put(COLUMN_NAME,"Blue Lagoon"); put(COLUMN_TAGLINE," Niebieska przygoda"); put(COLUMN_DESCRIPTION," Blue Lagoon to koktajl pełen owocowych smaków i niesamowitego, intensywnego koloru. Zaskakuje smakiem cytrusów i alkoholowym akcentem wódki."); put(COLUMN_PREPARATION," Przygotowanie: Wymieszaj składniki w shakerze z lodem, przelej do szklanki z lodem."); put(COLUMN_INGREDIENTS," Składniki: 30 ml wódki, 20 ml likieru blue curacao, 100 ml soku cytrynowego, kostki lodu")})
//        db.insert(TABLE_NAME, null,   ContentValues().apply { put(COLUMN_NAME,"Bloody Mary"); put(COLUMN_TAGLINE," Pikantna i wyrazista"); put(COLUMN_DESCRIPTION," Bloody Mary to koktajl, który łączy w sobie wódkę i sok pomidorowy z przyprawami, tworząc drink o intensywnym smaku. Często serwowany na brunchach jako sposób na poranną regenerację."); put(COLUMN_PREPARATION," Przygotowanie: Wymieszaj składniki w shakerze z lodem, przelej do wysokiej szklanki."); put(COLUMN_INGREDIENTS," Składniki: 50 ml wódki, 100 ml soku pomidorowego, 2-3 krople tabasco, 1 łyżeczka sosu Worcestershire, sok z cytryny, kostki lodu")})
//        db.insert(TABLE_NAME, null,   ContentValues().apply { put(COLUMN_NAME,"Blackberry Bramble"); put(COLUMN_TAGLINE," Owoce leśne w szklance"); put(COLUMN_DESCRIPTION," Blackberry Bramble to wersja klasycznego Bramble z dodatkiem świeżych jeżyn, które nadają mu pełni smaku i wyjątkowego koloru."); put(COLUMN_PREPARATION," Przygotowanie: Wymieszaj gin, sok z cytryny i syrop cukrowy w shakerze z lodem, przelej do szklanki z lodem, a następnie wlej likier jeżynowy."); put(COLUMN_INGREDIENTS," Składniki: 40 ml ginu, 20 ml soku z cytryny, 10 ml syropu cukrowego, 20 ml likieru jeżynowego, kostki lodu")})
//        db.insert(TABLE_NAME, null,   ContentValues().apply { put(COLUMN_NAME,"Batida de Coco"); put(COLUMN_TAGLINE," Tropikalna kokosowa przyjemność"); put(COLUMN_DESCRIPTION," Batida de Coco to brazylijski koktajl na bazie cachaça, mleka kokosowego i soku ananasowego. Jest słodki, egzotyczny i idealny na gorące dni."); put(COLUMN_PREPARATION," Przygotowanie: Wymieszaj składniki w shakerze z lodem, przelej do szklanki."); put(COLUMN_INGREDIENTS," Składniki: 50 ml cachaça, 50 ml mleka kokosowego, 50 ml soku ananasowego, kostki lodu")})
//        db.insert(TABLE_NAME, null,   ContentValues().apply { put(COLUMN_NAME,"Apple Martini"); put(COLUMN_TAGLINE," Owocowa elegancja"); put(COLUMN_DESCRIPTION," Apple Martini to koktajl, który łączy w sobie smak zielonego jabłka z wódką i likierem. Jest to napój o owocowej, lekko kwaśnej nucie, który jest jednym z ulubionych koktajli na imprezach."); put(COLUMN_PREPARATION," Przygotowanie: Wymieszaj składniki w shakerze z lodem, przelej do kieliszka koktajlowego."); put(COLUMN_INGREDIENTS," Składniki: 50 ml wódki, 20 ml likieru jabłkowego, 10 ml likieru triple sec, kostki lodu")})
//        db.insert(TABLE_NAME, null,   ContentValues().apply { put(COLUMN_NAME,"Amaretto Sour"); put(COLUMN_TAGLINE," Słodko-kwaśna przyjemność"); put(COLUMN_DESCRIPTION," Amaretto Sour to koktajl o wyrazistym smaku, który łączy orzechowy smak amaretto z cytrynową kwaskowatością. Jest doskonały na każdą okazję."); put(COLUMN_PREPARATION," Przygotowanie: Wymieszaj wszystkie składniki w shakerze z lodem, przelej do kieliszka koktajlowego."); put(COLUMN_INGREDIENTS," Składniki: 50 ml amaretto, 25 ml soku z cytryny, 10 ml syropu cukrowego, kostki lodu")})
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

                data.add(Drink(id, name, category, instructions))
            }
        }
        cursor.close()
        return data
    }



    private val jsonSettings = Json { ignoreUnknownKeys = true; prettyPrint = true; isLenient = true }

    suspend fun queryCocktailAPI(database: SQLiteDatabase) {
        // Create a Ktor client
        val client = HttpClient(CIO) {
            install(ContentNegotiation) {
                json(jsonSettings)
            }
        }

        for (letter in 'a'..'z') {
            try {
                println("BBB")
                val response: HttpResponse =
                    client.get("http://www.thecocktaildb.com/api/json/v1/1/search.php?f=$letter") {
                        contentType(ContentType.Application.Json)
                    }
                // Parse response
                if (response.status == HttpStatusCode.OK) {
                    val responseBody = response.bodyAsText()
                    println("Response: $responseBody")
                    val result = jsonSettings.decodeFromString<CocktailResponse>(responseBody)
                    println("Parsed Result: ${result.drinks}")

                    for (cocktail in result.drinks) {


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
                            put("strCreativeCommonsConfirmed", cocktail.strCreativeCommonsConfirmed)
                        }

                        database.insert(TABLE_NAME,null,values)
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




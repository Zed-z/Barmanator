package pl.poznan.put.barmanator.data

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

data class Drink (
    val id: Long,
    val name: String,
    val tagline: String,
    val description: String,
)

class Database(context: Context): SQLiteOpenHelper(context, "Database", null, 1) {
    companion object {
        private const val DATABASE_NAME = "database.db"
        private const val DATABASE_VERSION = 1

        const val TABLE_NAME = "Drinks"
        const val COLUMN_ID = "id"
        const val COLUMN_NAME = "name"
        const val COLUMN_TAGLINE = "tagline"
        const val COLUMN_DESCRIPTION = "description"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val query = """
            CREATE TABLE $TABLE_NAME (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_NAME TEXT NOT NULL,
                $COLUMN_TAGLINE TEXT NOT NULL,
                $COLUMN_DESCRIPTION TEXT NOT NULL
            )
        """.trimIndent()

        writableDatabase.insert(TABLE_NAME, null,
            ContentValues().apply {
                put(COLUMN_NAME, "Margarita")
                put(COLUMN_TAGLINE, "...")
                put(COLUMN_DESCRIPTION, "...")
            })

        writableDatabase.insert(TABLE_NAME, null,
            ContentValues().apply {
                put(COLUMN_NAME, "Margarita")
                put(COLUMN_TAGLINE, "...")
                put(COLUMN_DESCRIPTION, "...")
            })

        writableDatabase.insert(TABLE_NAME, null,
            ContentValues().apply {
                put(COLUMN_NAME, "Margarita")
                put(COLUMN_TAGLINE, "...")
                put(COLUMN_DESCRIPTION, "...")
            })
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {}

    fun getDrinks(): List<Drink> {
        val data = mutableListOf<Drink>()
        val cursor: Cursor = readableDatabase.query(TABLE_NAME, null, null, null, null, null, "$COLUMN_ID ASC")
        with(cursor) {
            while (moveToNext()) {
                val id = getLong(getColumnIndexOrThrow(COLUMN_ID))
                val name = getString(getColumnIndexOrThrow(COLUMN_NAME))
                val tagline = getString(getColumnIndexOrThrow(COLUMN_TAGLINE))
                val description = getString(getColumnIndexOrThrow(COLUMN_DESCRIPTION))

                data.add(Drink(id, name, tagline, description))
            }
        }
        cursor.close()
        return data
    }
}


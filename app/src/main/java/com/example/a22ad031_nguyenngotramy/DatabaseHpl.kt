package com.example.a22ad031_nguyenngotramy
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.a22ad031_nguyenngotramy.Sach
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.sql.SQLException

class DatabaseHpl(context: Context) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {

    companion object {
        private const val DB_NAME = "sach.db"
        private const val DB_VERSION = 1
        private const val TABLE_NAME = "Sach"
        private const val COLUMN_ID = "id"
        private const val COLUMN_TITLE = "TieuDe"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableSQL = """
        CREATE TABLE IF NOT EXISTS $TABLE_NAME (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            TieuDe TEXT,
            TacGia TEXT,
            TheLoai TEXT,
            NamXB TEXT,
            MoTa TEXT,
            HinhAnh BOLB
        )
    """
        try {
            db?.execSQL(createTableSQL)
            Log.d("DatabaseHpl", "Table $TABLE_NAME created successfully.")
        } catch (e: SQLException) {
            Log.e("DatabaseHpl", "Error creating table $TABLE_NAME: ${e.message}")
        }
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        // Handle database upgrade if needed
    }

    fun addSach(sach: Sach1) {
        val db = writableDatabase
        val contentValues = ContentValues().apply {
            put("TieuDe", sach.tieuDe)
            put("TacGia", sach.tacGia)
            put("TheLoai", sach.theLoai)
            put("NamXB", sach.namXB)
            put("MoTa", sach.moTa)
            put("HinhAnh", sach.hinhAnh)
        }

        db.insert(TABLE_NAME, null, contentValues)

        Log.d("MyDatabase", "Inserted ${sach.tieuDe} into $TABLE_NAME table.")
    }
    fun getAllBook():List<Sach>{
        val listBook = mutableListOf<Sach>()
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME"
        val cursor = db.rawQuery(query,null)
        while(cursor.moveToNext()){
            val maSach = cursor.getInt(cursor.getColumnIndexOrThrow("id"))
            val tieuDe = cursor.getString(cursor.getColumnIndexOrThrow("TieuDe"))
            val tacGia = cursor.getString(cursor.getColumnIndexOrThrow("TacGia"))
            val theLoai = cursor.getString(cursor.getColumnIndexOrThrow("TheLoai"))
            val namXB = cursor.getString(cursor.getColumnIndexOrThrow("NamXB"))
            val moTa = cursor.getString(cursor.getColumnIndexOrThrow("MoTa"))
            val hinhAnh = cursor.getBlob(cursor.getColumnIndexOrThrow("HinhAnh"))

            val sach = Sach(maSach,tieuDe,tacGia,theLoai,namXB,moTa,hinhAnh)
            listBook.add(sach)
        }
        cursor.close()
        db.close()
        return listBook
    }
    fun deleteBook(bookId:Int){
        val db = writableDatabase
        val whereClause = "$COLUMN_ID=?"
        val whereArgs = arrayOf(bookId.toString())
        db.delete(TABLE_NAME,whereClause,whereArgs)
        db.close()
    }
    fun getAllFindBook(title: String): List<Sach> {
        val listBook = mutableListOf<Sach>()
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME WHERE $COLUMN_TITLE LIKE ?"
        val selectionArgs = arrayOf("%$title%")
        val cursor = db.rawQuery(query, selectionArgs)

        while (cursor.moveToNext()) {
            val maSach = cursor.getInt(cursor.getColumnIndexOrThrow("id"))
            val tieuDe = cursor.getString(cursor.getColumnIndexOrThrow("TieuDe"))
            val tacGia = cursor.getString(cursor.getColumnIndexOrThrow("TacGia"))
            val theLoai = cursor.getString(cursor.getColumnIndexOrThrow("TheLoai"))
            val namXB = cursor.getString(cursor.getColumnIndexOrThrow("NamXB"))
            val moTa = cursor.getString(cursor.getColumnIndexOrThrow("MoTa"))
            val hinhAnh = cursor.getBlob(cursor.getColumnIndexOrThrow("HinhAnh"))

            val sach = Sach(maSach, tieuDe, tacGia, theLoai, namXB, moTa, hinhAnh)
            listBook.add(sach)
        }

        cursor.close()
        db.close()
        return listBook
    }



}

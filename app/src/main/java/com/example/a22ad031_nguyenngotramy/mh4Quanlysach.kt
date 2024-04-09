package com.example.a22ad031_nguyenngotramy
import android.app.Activity
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.a22ad031_nguyenngotramy.CustomAdapterListView
import com.example.a22ad031_nguyenngotramy.R

import com.example.a22ad031_nguyenngotramy.Sach
import com.example.a22ad031_nguyenngotramy.databinding.ActivityMh4QuanlysachBinding
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.OutputStream

class mh4Quanlysach : AppCompatActivity() {
    private lateinit var binding: ActivityMh4QuanlysachBinding
    private lateinit var bookadapter: CustomAdapterListView
    private lateinit var db: DatabaseHpl
    private var selectedImageUri: Uri? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMh4QuanlysachBinding.inflate(layoutInflater)
        setContentView(binding.root)
        db = DatabaseHpl(this)
        bookadapter = CustomAdapterListView(this,db.getAllBook())
        binding.listSach.adapter  = bookadapter
        setupListeners()

    }

    override fun onResume() {
        super.onResume()
        bookadapter.refreshData(db.getAllBook())
    }
    private fun setupListeners() {
        binding.btnNamXB.setOnClickListener {
            showDatePicker()
        }

        binding.img.setOnClickListener {
            openImagePicker()
        }

        binding.btnAdd.setOnClickListener {
            addBook()
            onResume()
        }
        binding.btnClean.setOnClickListener{
            Clear()
        }
        binding.btnSearch.setOnClickListener {
            val title = binding.edtTimKiem.text.toString()
            bookadapter.refreshData(db.getAllFindBook(title))
        }
        binding.btncancle.setOnClickListener {
            binding.edtTimKiem.setText("")
            bookadapter.refreshData(db.getAllBook())
        }

    }

    private fun showDatePicker() {
        val datePickerDialog = DatePickerDialog(
            this,
            DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                binding.txtNamXuatBan.setText("$year")
            },
            2024,
            1,
            1
        )
        datePickerDialog.show()
    }
    private fun openImagePicker() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.type = "image/*"

        if (intent.resolveActivity(packageManager) != null) {
            startActivityForResult(intent, PICK_IMAGE_REQUEST)
        } else {
            Toast.makeText(this, "Không tìm thấy ứng dụng xử lý ảnh.", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            selectedImageUri = data.data

            try {
                val inputStream = contentResolver.openInputStream(selectedImageUri!!)
                val bitmap = BitmapFactory.decodeStream(inputStream)
                inputStream?.close()

                binding.img.setImageBitmap(bitmap)
            } catch (e: IOException) {
                e.printStackTrace()
                Toast.makeText(this, "Lỗi khi đọc ảnh từ Gallery.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun addBook() {
        val tieuDe = binding.edtTieuDe.text.toString()
        val tacGia = binding.edtTacGia.text.toString()
        val theLoai = binding.edtTheLoai.text.toString()
        val namXB = binding.txtNamXuatBan.text.toString()
        val moTa = binding.edtMoTa.text.toString()

        if (selectedImageUri != null) {
            try {


                val bitmap = (binding.img.drawable as BitmapDrawable).bitmap
                val byteArray = bitmapToByteArray(bitmap)
                saveBitmapToDrawableFolder(this, "my_image.png", bitmap)

                db?.addSach(Sach1(tieuDe, tacGia, theLoai, namXB, moTa,byteArray))
                Toast.makeText(this, "Thêm thành công", Toast.LENGTH_SHORT).show()
                Clear()

            } catch (e: IOException) {
                e.printStackTrace()
                Toast.makeText(this, "Lỗi khi đọc ảnh từ Gallery.", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "Vui lòng chọn hình ảnh.", Toast.LENGTH_SHORT).show()
        }
    }
    private fun bitmapToByteArray(bitmap: Bitmap): ByteArray {
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
        return stream.toByteArray()
    }
    private fun saveBitmapToDrawableFolder(context: Context, filename: String, bitmap: Bitmap) {
        try {
            val outputStream = context.openFileOutput(filename, Context.MODE_PRIVATE)
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
            outputStream.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun Clear(){
        binding.edtTacGia.setText("")
        binding.edtTieuDe.setText("")
        binding.edtTheLoai.setText("")
        binding.edtMoTa.setText("")
        binding.txtNamXuatBan.setText("2024")
        binding.img.setImageResource(0)
    }

    companion object {
        private const val PICK_IMAGE_REQUEST = 1
    }
}

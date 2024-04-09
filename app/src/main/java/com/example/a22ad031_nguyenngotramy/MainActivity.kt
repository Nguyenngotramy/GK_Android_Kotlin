package com.example.a22ad031_nguyenngotramy

import android.content.Intent
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.example.a22ad031_nguyenngotramy.databinding.ActivityMainBinding



class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var contextMenuButton: AppCompatButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        contextMenuButton = binding.contextMenu
        registerForContextMenu(contextMenuButton)
    }

    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
        if (v == contextMenuButton) {
            menu?.apply {
                add(1, 11, 1, "Số nguyên tố")
                add(1, 12, 2, "ax+b = 0")
                add(1, 13, 3, "Quản lý sách")
                setHeaderTitle("Lựa chọn")
            }
        }
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            11 -> {
                val i = Intent(this,mh2SoNguyenTo::class.java)
                Toast.makeText(this, "Bạn đã chọn Số nguyên tố", Toast.LENGTH_SHORT).show()
                startActivity(i)
                return true
            }
            12 -> {
                val i1 = Intent(this,mh3Ptbn::class.java)
                Toast.makeText(this, "Bạn đã chọn ax+b = 0", Toast.LENGTH_SHORT).show()
                startActivity(i1)
                return true
            }
            13 -> {
                val i2 = Intent(this, mh4Quanlysach::class.java)
                Toast.makeText(this, "Bạn đã chọn Quản lý sách", Toast.LENGTH_SHORT).show()
                startActivity(i2)
                return true
            }
            else -> return super.onContextItemSelected(item)
        }
    }
}

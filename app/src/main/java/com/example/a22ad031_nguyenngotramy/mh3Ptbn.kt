package com.example.a22ad031_nguyenngotramy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.TextKeyListener.clear
import com.example.a22ad031_nguyenngotramy.databinding.ActivityMh3PtbnBinding

private lateinit var binding:ActivityMh3PtbnBinding
class mh3Ptbn : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mh3_ptbn)
        binding = ActivityMh3PtbnBinding.inflate(layoutInflater)
        setContentView(binding.root)
        calculate()
        clear()
        backHome()
    }
    private fun backHome() {
        binding.btnHome.setOnClickListener {
            val home = Intent(this@mh3Ptbn,MainActivity::class.java)
            startActivity(home)
        }
    }
    private fun clear() {
        binding.btnClear.setOnClickListener {
            binding.edtA.setText("")
            binding.edtB.setText("")
        }
    }

    private fun calculate() {
        binding.btncalculate.setOnClickListener {
            val a = binding.edtA.text.toString()
            val b = binding.edtB.text.toString()
            if (a.isEmpty() || b.isEmpty()) {
                binding.txtResult.setText("Vui lòng nhập lại a hoặc b")
            } else if (a.any { it.isLetter() } || b.any { it.isLetter() }) {
                binding.txtResult.setText("Vui lòng nhập số")
            } else {
                val A: Double = a.toDouble()
                val B: Double = b.toDouble()
                val result: Double = -(B / A)
                binding.txtResult.setText("Kết quả: x = $result")
            }
        }

    }
}
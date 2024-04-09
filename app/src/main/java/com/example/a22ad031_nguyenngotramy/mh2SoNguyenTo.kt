package com.example.a22ad031_nguyenngotramy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.a22ad031_nguyenngotramy.databinding.ActivityMh2SoNguyenToBinding

private lateinit var binding:ActivityMh2SoNguyenToBinding
class mh2SoNguyenTo : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mh2_so_nguyen_to)
        binding = ActivityMh2SoNguyenToBinding.inflate(layoutInflater)
        setContentView(binding.root)

        checkSNT()
        clearSNT()
        backHome()
    }

    private fun backHome() {
        binding.btnHome.setOnClickListener {
            val home = Intent(this@mh2SoNguyenTo,MainActivity::class.java)
            startActivity(home)
        }
    }

    private fun clearSNT() {
        binding.btnClear.setOnClickListener {
            binding.editSNT.setText("")
            binding.txtResult.setText("Hiển thị kết quả ở đây")
        }
    }

    private fun checkSNT() {
        binding.btnCheck.setOnClickListener {
            val input = binding.editSNT.text.toString()
            if (input.isEmpty()) {
                binding.txtResult.text = "Vui lòng không để trống"
            } else if (input.any { it.isLetter() }) {
                binding.txtResult.text = "Vui lòng nhập số"
            } else {
                val so: Int =input.toInt()
                if (so >= 2) {
                    var dem: Int = 0
                    for (i in 1..so) {
                        if (so % i == 0) {
                            dem += 1
                        }
                    }
                    if (dem == 2) {
                        binding.txtResult.text = "Số $so là số nguyên tố"
                    } else {
                        binding.txtResult.text = "Số $so không phải là số nguyên tố"
                    }
                } else {
                    binding.txtResult.text = "Số $so không phải là số nguyên tố"
                }
            }
        }
    }
}

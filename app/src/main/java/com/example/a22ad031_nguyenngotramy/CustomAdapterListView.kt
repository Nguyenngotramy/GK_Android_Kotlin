package com.example.a22ad031_nguyenngotramy

import android.app.Activity
import android.app.AlertDialog
import android.graphics.BitmapFactory
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast

class CustomAdapterListView(val activity:Activity, var list: List<Sach>):ArrayAdapter<Sach>(activity,R.layout.list_item){

    private val db: DatabaseHpl = DatabaseHpl(context)

    override fun getCount(): Int {
        return list.size
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater = activity.layoutInflater
        val rowView = inflater.inflate(R.layout.list_item, parent, false)

        val sach = list[position]

        val id = rowView.findViewById<TextView>(R.id.txtMaSach)
        val tieuDe = rowView.findViewById<TextView>(R.id.txtTieude)
        val tacGia = rowView.findViewById<TextView>(R.id.txtTacGia)
        val theLoai = rowView.findViewById<TextView>(R.id.txtTheLoai)
        val namXB = rowView.findViewById<TextView>(R.id.txtNamXB)
        val moTa = rowView.findViewById<TextView>(R.id.txtMoTa)
        val hinhAnh = rowView.findViewById<ImageView>(R.id.imgs)
       val btnDelete = rowView.findViewById<Button>(R.id.btnDelete)


        tieuDe.text = sach.tieuDe
        tacGia.text = sach.tacGia
        theLoai.text = sach.theLoai
        namXB.text = sach.namXB
        moTa.text = sach.moTa

        val bitmap = BitmapFactory.decodeByteArray(sach.hinhAnh, 0, sach.hinhAnh.size)
        hinhAnh.setImageBitmap(bitmap)
        val maSachValue = sach.maSach
        id.text = maSachValue.toString()
        val idBook = maSachValue.toString().toInt()
        btnDelete.setOnClickListener {

            val dialog = AlertDialog.Builder(context)
            dialog.apply {
                setTitle("Deleta this book")
                setMessage("Bạn có muốn xóa cuốn sách này??")
              setPositiveButton("Yes"){
                      dialogInterface,_->
                  db.deleteBook(idBook)
                  refreshData(db.getAllBook())
                  Toast.makeText(context,"Đã xóa cuốn sách này",Toast.LENGTH_SHORT).show()
              }
                setNegativeButton("No"){
                    dialogInterface,_->
                    dialogInterface.dismiss()
                }
                setCancelable(false)
            }
            dialog.show()
        }

        return rowView
    }
    fun refreshData(newBooks: List<Sach>) {
        list = newBooks
        notifyDataSetChanged()
    }

}

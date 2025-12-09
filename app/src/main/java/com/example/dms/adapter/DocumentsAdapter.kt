package com.example.documents

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.example.dms.R
import com.example.dms.models.Document

class DocumentsAdapter(
    private val list: List<Document>
) : RecyclerView.Adapter<DocumentsAdapter.DocViewHolder>() {

    inner class DocViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvNumber: TextView = view.findViewById(R.id.tvNumber)
        val tvTitle: TextView = view.findViewById(R.id.tvTitle)
        val tvStatus: TextView = view.findViewById(R.id.tvStatus)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DocViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_document, parent, false)
        return DocViewHolder(view)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: DocViewHolder, position: Int) {
        val item = list[position]

        holder.tvNumber.text = item.number.toString()
        holder.tvTitle.text = item.fileName
        holder.tvStatus.text = item.status

        when (item.status) {
            "Проверен" -> holder.tvStatus.setTextColor(Color.parseColor("#3E9E00"))
            "На рассмотрении" -> holder.tvStatus.setTextColor(Color.parseColor("#E57C04"))
            else -> holder.tvStatus.setTextColor(Color.BLACK)
        }
    }
}

package com.example.dms.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dms.R
import com.example.dms.models.News
import com.example.dms.ui.NewsDetailActivity

class NewsAdapter(private val items: List<News>) :
    RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.newsTitle)
        val description: TextView = itemView.findViewById(R.id.newsDescription)
        val date: TextView = itemView.findViewById(R.id.newsDate)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_news, parent, false)

        // Устанавливаем отступы между карточками
        val layoutParams = view.layoutParams as RecyclerView.LayoutParams
        val marginInPx = (8 * parent.context.resources.displayMetrics.density).toInt()
        layoutParams.setMargins(marginInPx, marginInPx, marginInPx, marginInPx)
        view.layoutParams = layoutParams

        return NewsViewHolder(view)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val news = items[position]
        holder.title.text = news.title
        holder.description.text = news.description
        holder.date.text = news.date

        // Клик по карточке
        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, NewsDetailActivity::class.java)
            intent.putExtra("title", news.title)
            intent.putExtra("description", news.description)
            intent.putExtra("date", news.date)
            context.startActivity(intent)
        }
    }

    override fun getItemCount() = items.size
}

package com.example.dms.ui

import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dms.R
import com.example.dms.adapters.NewsAdapter
import com.example.dms.models.News

class NewsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_news, container, false)

        val recyclerView = view.findViewById<RecyclerView>(R.id.newsRecycler)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val exampleNews = listOf(
            News("Первая новость", "Описание первой новости...", "2025-11-30"),
            News("Вторая новость", "Описание второй новости...", "2025-11-29"),
            News("Третья новость", "Описание третьей новости...", "2025-11-28")
        )

        recyclerView.adapter = NewsAdapter(exampleNews)

        // Настройка отступов между карточками
        val verticalDp = 8 // верхний и нижний отступ
        val horizontalDp = 4 // боковые отступы
        val verticalPx = (verticalDp * resources.displayMetrics.density).toInt()
        val horizontalPx = (horizontalDp * resources.displayMetrics.density).toInt()

        recyclerView.addItemDecoration(object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(
                outRect: Rect,
                view: View,
                parent: RecyclerView,
                state: RecyclerView.State
            ) {
                outRect.set(horizontalPx, verticalPx, horizontalPx, verticalPx)
            }
        })

        return view
    }
}

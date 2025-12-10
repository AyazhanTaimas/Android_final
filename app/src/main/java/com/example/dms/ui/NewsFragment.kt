package com.example.dms.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dms.R
import com.example.dms.adapters.NewsAdapter
import com.example.dms.models.News
import com.example.dms.utils.SessionManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsFragment : Fragment() {

    private lateinit var sessionManager: SessionManager
    private lateinit var recyclerView: RecyclerView
    private lateinit var addButton: Button
    private var newsList: MutableList<News> = mutableListOf()
    private lateinit var adapter: NewsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_news, container, false)
        sessionManager = SessionManager(requireContext())

        recyclerView = view.findViewById(R.id.newsRecycler)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        addButton = view.findViewById(R.id.btnAddNews)

        updateAddButtonVisibility() // показываем/скрываем кнопку по роли

        // Передаем роль пользователя в адаптер
        adapter = NewsAdapter(newsList, sessionManager.getUserRole(), ::onEditClick, ::onDeleteClick)
        recyclerView.adapter = adapter

        fetchNews()
        return view
    }

    override fun onResume() {
        super.onResume()
        updateAddButtonVisibility() // обновляем видимость кнопки при возврате на экран
        if (sessionManager.getToken() != null) fetchNews()
    }

    private fun updateAddButtonVisibility() {
        val role = sessionManager.getUserRole()
        Log.d("NewsFragment", "Role from SessionManager: '$role'")

        if (role.equals("admin", true) || role.equals("manager", true)) {
            addButton.visibility = View.VISIBLE
            addButton.setOnClickListener { openAddNews() }
        } else {
            addButton.visibility = View.GONE
        }
    }

    private fun fetchNews() {
        val token = sessionManager.getToken() ?: return
        val api = RetrofitClient.getInstance(token)

        api.getAllNews().enqueue(object : Callback<List<News>> {
            override fun onResponse(call: Call<List<News>>, response: Response<List<News>>) {
                if (response.isSuccessful && response.body() != null) {
                    newsList.clear()
                    newsList.addAll(response.body()!!)
                    adapter.notifyDataSetChanged()
                } else {
                    Toast.makeText(requireContext(), "Ошибка загрузки новостей", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<News>>, t: Throwable) {
                Toast.makeText(requireContext(), "Ошибка сети: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun onEditClick(news: News) {
        if (news.id == null) return
        val intent = Intent(requireContext(), AddEditNewsActivity::class.java)
        intent.putExtra("news_id", news.id)
        intent.putExtra("news_title", news.title)
        intent.putExtra("news_description", news.description)
        startActivity(intent)
    }

    private fun onDeleteClick(news: News) {
        val token = sessionManager.getToken() ?: return
        val api = RetrofitClient.getInstance(token)
        if (news.id == null) return

        api.deleteNews(news.id).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Toast.makeText(requireContext(), "Новость удалена", Toast.LENGTH_SHORT).show()
                    fetchNews()
                } else {
                    Toast.makeText(requireContext(), "Ошибка удаления", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Toast.makeText(requireContext(), "Ошибка сети: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun openAddNews() {
        val intent = Intent(requireContext(), AddEditNewsActivity::class.java)
        startActivity(intent)
    }
}

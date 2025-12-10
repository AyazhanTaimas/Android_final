package com.example.dms.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dms.R
import com.example.dms.models.Document
import com.example.documents.DocumentsAdapter

class DocumentsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_documents, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val rvDocuments = view.findViewById<RecyclerView>(R.id.rvDocuments)
        val btnUpload = view.findViewById<Button>(R.id.btnUpload)

        val docs = listOf(
            Document(1, "Удостак", "Проверен"),
            Document(2, "Местожительство", "На рассмотрении"),
            Document(3, "", ""),
            Document(4, "", "")
        )

        rvDocuments.layoutManager = LinearLayoutManager(requireContext())
        rvDocuments.adapter = DocumentsAdapter(docs)

        btnUpload.setOnClickListener {
            // TODO: загрузка файла
        }
    }
}


package com.example.dms.ui

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.dms.R
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textview.MaterialTextView

class ProfileFragment : Fragment() {

    private lateinit var profileImage: ImageView
    private lateinit var profileName: MaterialTextView
    private lateinit var profileId: MaterialTextView
    private lateinit var profileEmail: TextInputEditText
    private lateinit var profilePhone: TextInputEditText
    private lateinit var changePasswordButton: MaterialButton
    private lateinit var saveProfileButton: MaterialButton

    private val PREFS_NAME = "user_prefs"
    private val KEY_PROFILE_IMAGE = "profile_image"

    // Выбор изображения через файловый менеджер
    private val pickImageLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val imageUri: Uri? = result.data?.data
                if (imageUri != null) {
                    // Сохранение URI в SharedPreferences
                    saveProfileImageUri(imageUri)

                    // Отображение изображения
                    Glide.with(this)
                        .load(imageUri)
                        .circleCrop()
                        .into(profileImage)
                }
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        profileImage = view.findViewById(R.id.profileImage)
        profileName = view.findViewById(R.id.profileName)
        profileId = view.findViewById(R.id.profileId)
        profileEmail = view.findViewById(R.id.profileEmail)
        profilePhone = view.findViewById(R.id.profilePhone)
        changePasswordButton = view.findViewById(R.id.changePasswordButton)
        saveProfileButton = view.findViewById(R.id.saveProfileButton)

        // Загрузка сохранённого аватара (если есть)
        loadProfileImage()

        // Открытие файлового менеджера при клике на аватар
        profileImage.setOnClickListener {
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
                type = "image/*"
                addCategory(Intent.CATEGORY_OPENABLE)
            }
            pickImageLauncher.launch(intent)
        }

        changePasswordButton.setOnClickListener {
            Toast.makeText(requireContext(), "Изменение пароля", Toast.LENGTH_SHORT).show()
        }

        saveProfileButton.setOnClickListener {
            Toast.makeText(requireContext(), "Изменения сохранены", Toast.LENGTH_SHORT).show()
        }

        return view
    }

    private fun saveProfileImageUri(uri: Uri) {
        val prefs = requireContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        prefs.edit().putString(KEY_PROFILE_IMAGE, uri.toString()).apply()
    }

    private fun loadProfileImage() {
        val prefs = requireContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val savedUri = prefs.getString(KEY_PROFILE_IMAGE, null)
        if (!savedUri.isNullOrEmpty()) {
            Glide.with(this)
                .load(Uri.parse(savedUri))
                .circleCrop()
                .into(profileImage)
        }
    }
}

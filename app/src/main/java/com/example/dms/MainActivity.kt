package com.example.dms

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.example.dms.ui.NewsFragment
import com.example.dms.ui.ProfileFragment
import com.example.dms.ui.ResidenceFragment
import com.example.dms.ui.SportsRegistrationFragment
import com.example.dms.utils.SessionManager
import com.example.dms.ui.DocumentsFragment
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navView: NavigationView
    private lateinit var menuButton: ImageView
    private lateinit var toolbarTitle: TextView
    private lateinit var sessionManager: SessionManager

    private val CURRENT_FRAGMENT_KEY = "current_fragment"
    private val CURRENT_TITLE_KEY = "current_title"

    private var currentFragmentTag: String? = null
    private var currentTitle: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sessionManager = SessionManager(this)

        // Проверка токена
        val token = sessionManager.getToken()
        if (token == null) {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
            return
        }

        setContentView(R.layout.activity_main)

        drawerLayout = findViewById(R.id.drawer_layout)
        navView = findViewById(R.id.nav_view)
        menuButton = findViewById(R.id.menuButton)
        toolbarTitle = findViewById(R.id.fragmentTitle)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        menuButton.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }

        // Восстанавливаем фрагмент и заголовок после поворота
        if (savedInstanceState != null) {
            currentFragmentTag = savedInstanceState.getString(CURRENT_FRAGMENT_KEY)
            currentTitle = savedInstanceState.getString(CURRENT_TITLE_KEY)
            val fragment = supportFragmentManager.findFragmentByTag(currentFragmentTag)
            fragment?.let {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.main_container, it, currentFragmentTag)
                    .commit()
                toolbarTitle.text = currentTitle
            }
        } else {
            // По умолчанию открываем Новости
            openFragment(NewsFragment(), R.id.nav_home, "Новости")
        }

        navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_home -> openFragment(NewsFragment(), R.id.nav_home, "Новости")
                R.id.nav_profile -> openFragment(ProfileFragment(), R.id.nav_profile, "Личная информация")
                R.id.nav_living -> openFragment(ResidenceFragment(), R.id.nav_living, "Проживание")
                R.id.nav_sport -> openFragment(SportsRegistrationFragment(), R.id.nav_sport, "Запись на занятие физкультурой")
                R.id.nav_docs -> openFragment(DocumentsFragment(), R.id.nav_docs, "Документы")
                R.id.nav_finance -> showPlaceholder("Финансовый кабинет", R.id.nav_finance)
                R.id.nav_repair -> showPlaceholder("Запросы на ремонт", R.id.nav_repair)
                R.id.nav_logout -> {
                    sessionManager.clearToken()
                    startActivity(Intent(this, LoginActivity::class.java))
                    finish()
                }
            }
            drawerLayout.closeDrawers()
            true
        }
    }

    private fun showPlaceholder(message: String, menuId: Int) {
        android.widget.Toast.makeText(this, message, android.widget.Toast.LENGTH_SHORT).show()
        navView.setCheckedItem(menuId)
        toolbarTitle.text = message
        currentFragmentTag = null
        currentTitle = message
    }

    private fun openFragment(fragment: Fragment, menuId: Int, title: String) {
        val tag = fragment::class.java.simpleName
        currentFragmentTag = tag
        currentTitle = title

        supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, fragment, tag)
            .commit()

        navView.setCheckedItem(menuId)
        toolbarTitle.text = title
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(CURRENT_FRAGMENT_KEY, currentFragmentTag)
        outState.putString(CURRENT_TITLE_KEY, currentTitle)
    }
}

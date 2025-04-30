package com.example.kotlinsample.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.kotlinsample.R
import com.example.kotlinsample.entity.UseCaseCategory
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class UseCaseActivity @Inject constructor() : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_use_case)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    companion object {
        private const val EXTRA_USE_CASE_CATEGORY = "EXTRA_USE_CASES"
        fun newIntent(context: Context, useCaseCategory: UseCaseCategory) =
            Intent(context, UseCaseActivity::class.java).apply {
                putExtra(EXTRA_USE_CASE_CATEGORY, useCaseCategory)
            }
    }
}

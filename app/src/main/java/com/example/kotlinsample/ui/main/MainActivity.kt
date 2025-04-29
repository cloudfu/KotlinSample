package com.example.kotlinsample.ui.main

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kotlinsample.R
import com.example.kotlinsample.adapter.UseCaseListAdapter
import com.example.kotlinsample.data.userCaseCategories
import com.example.kotlinsample.databinding.ActivityMainBinding
import com.example.kotlinsample.data.UseCaseCategory
import com.example.model.ui.BaseActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

//@AndroidEntryPoint
class MainActivity: BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    private lateinit var mUseCaseCategoryAdapter: UseCaseListAdapter


    /***
     * 需要在依赖注入的ViewModel添加//@ActivityRetainedScoped
     * @Inject
     * lateinit var mLoginViewModel: LoginViewModel
     * */

    /***
     * 需要在依赖注入的ViewModel添加@HiltViewModel
     * Activity 中使用 val mLoginViewModel: LoginViewModel by viewModels()
     * */
//    private val vMainViewModel: MainViewModel by viewModels()

    /***
     * RecycleView Item Click
     */
    private var onUseCaseCategoryItemClick: (UseCaseCategory) -> Unit = { clickedUseCaseCategory->
        val intent = UseCaseActivity.newIntent(applicationContext, clickedUseCaseCategory)
        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        init()
    }

    private fun init(){

        /***
         * Init recyclerView.Adapter
         */
        mUseCaseCategoryAdapter = UseCaseListAdapter()
//        mUseCaseCategoryAdapter.dataSource = vMainViewModel.loadNextPage()?: listOf()
        mUseCaseCategoryAdapter.onListItemClicked { clickedUseCaseCategory ->
//            val intent = UseCaseActivity.newIntent(applicationContext, clickedUseCaseCategory)
//            startActivity(intent)
            onUseCaseCategoryItemClick(clickedUseCaseCategory)
        }

        /***
         * Page data binding operation
         */
        binding {
            recyclerView.apply {
                adapter = mUseCaseCategoryAdapter
                hasFixedSize()
                layoutManager = LinearLayoutManager(this@MainActivity)
                addItemDecoration(mUseCaseCategoryAdapter.getItemDecoration(this@MainActivity,
                    R.drawable.recyclerview_divider
                ))
            }
        }
    }
}
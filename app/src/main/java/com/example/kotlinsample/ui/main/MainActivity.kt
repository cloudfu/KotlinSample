package com.example.kotlinsample.ui.main

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kotlinsample.R
import com.example.kotlinsample.adapter.UseCaseListAdapter
import com.example.kotlinsample.databinding.ActivityMainBinding
import com.example.kotlinsample.entity.UseCaseCategory
import com.example.model.data.ResultPackage
import com.example.model.ui.BaseActivity
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity @Inject constructor(): BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

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
    private val mMainViewModel: MainViewModel by viewModels()

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

    @SuppressLint("NotifyDataSetChanged")
    private fun init(){

        /***
         * Init recyclerView.Adapter
         */
        mUseCaseCategoryAdapter = UseCaseListAdapter()
        mUseCaseCategoryAdapter.dataSource = mMainViewModel.useCaseCategories.value!!
        mMainViewModel.loading.observe(this){
            if (it != null) {
                render(it)
            }
        }


        mMainViewModel.useCaseCategories.observe(this@MainActivity) {
            mUseCaseCategoryAdapter.dataSource = it
            mUseCaseCategoryAdapter.submitList(it)
        }
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
//                hasFixedSize()
                layoutManager = LinearLayoutManager(this@MainActivity)
                addItemDecoration(mUseCaseCategoryAdapter.getItemDecoration(this@MainActivity,
                    R.drawable.recyclerview_divider
                ))
            }
        }

        mMainViewModel.fetchNextPage()
        mBinding.recyclerView.setOnScrollChangeListener { _, _, scrollY, _, _ ->
            if (scrollY == mBinding.recyclerView.getChildAt(0).top) {
                mMainViewModel.fetchNextPage()
            }
        }
    }

    private fun render(loading: Boolean) {
        Timber.d("loading:$loading")
    }

}
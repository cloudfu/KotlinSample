package com.example.model.ui

import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class BaseActivity<T : ViewDataBinding>(
    @LayoutRes private val layoutId: Int
): AppCompatActivity() {

    private var mBindingComponent: DataBindingComponent? = DataBindingUtil.getDefaultComponent()
    protected val mBinding: T by lazy(LazyThreadSafetyMode.NONE) {
        DataBindingUtil.setContentView(this, layoutId, mBindingComponent)
    }

    init {
        // TODO:是否可以去除
        addOnContextAvailableListener {
            mBinding.notifyChange()
        }
    }
    
    
    /**
     * 针对页面View进行数据绑定
     */
    protected inline fun binding(block: T.() -> Unit): T {
        return mBinding.apply(block)
    }

    override fun onDestroy() {
        super.onDestroy()
        mBinding.unbind()
    }

}
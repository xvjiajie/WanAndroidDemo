package com.xujiajie.wanandroid

import android.os.Bundle
import android.widget.SeekBar
import androidx.fragment.app.Fragment
import com.gyf.immersionbar.ImmersionBar
import com.gyf.immersionbar.ktx.immersionBar
import com.xujiajie.wanandroid.base.BaseMActivity
import com.xujiajie.wanandroid.databinding.ActivityMainBinding
import com.xujiajie.wanandroid.ui.fragment.HomeFragment1

class MainActivity : BaseMActivity<MainViewModel, ActivityMainBinding>() {
    private val fragments = arrayListOf<Fragment>()
    private var lastPosition: Int = 0
    private var mPosition: Int = 0
    private var currentFragment: Fragment? = null//要显示的Fragment
    private var hideFragment: Fragment? = null//要隐藏的Fragment
    override fun initView(savedInstanceState: Bundle?) {

        fragments?.apply {
            add(HomeFragment1())
        }
        mBinding?.bottomNavView?.apply {
            setOnNavigationItemSelectedListener {
                when (it.itemId) {
                    R.id.home_page -> {
                        setSelectedFragment(0)
                    }
                    R.id.home_system -> {
                    }
                    R.id.home_project -> {
                    }
                    R.id.home_plaza -> {
                    }
                    R.id.home_mine -> {
                    }
                }
                true
            }
        }
    }

    override fun initImmersionBar() {
        super.initImmersionBar()
        /*ImmersionBar.with(this)
            .titleBar(mBinding?.tb?.toolbar) //                .setOnBarListener(this::adjustView)
            .init()*/
        /*immersionBar {
            transparentStatusBar()
        }*/
    }

    override fun getContentLayout(): Int {
        return R.layout.activity_main
    }

    /**
     * 根据位置选择Fragment
     * @param position 要选中的fragment的位置
     */
    private fun setSelectedFragment(position: Int) {
        mBinding?.bottomNavView?.menu?.getItem(position)?.isChecked = true
        val fragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        currentFragment =
            fragmentManager.findFragmentByTag("fragment$position")//要显示的fragment(解决了activity重建时新建实例的问题)
        hideFragment =
            fragmentManager.findFragmentByTag("fragment$lastPosition")//要隐藏的fragment(解决了activity重建时新建实例的问题)
        if (position != lastPosition) {//如果位置不同
            //如果要隐藏的fragment存在，则隐藏
            hideFragment?.let { transaction.hide(it) }
            if (currentFragment == null) {//如果要显示的fragment不存在，则新加并提交事务
                currentFragment = fragments[position]
                currentFragment?.let { transaction.add(R.id.fl_container, it, "fragment$position") }
            } else {//如果要显示的存在则直接显示
                currentFragment?.let {
                    transaction.show(it)
                }
            }
        }

        if (position == lastPosition) {//如果位置相同
            if (currentFragment == null) {//如果fragment不存在(第一次启动应用的时候)
                currentFragment = fragments[position]
                currentFragment?.let { transaction.add(R.id.fl_container, it, "fragment$position") }
            }//如果位置相同，且fragment存在，则不作任何操作
        }
        transaction.commit()//提交事务
        lastPosition = position//更新要隐藏的fragment的位置
        mPosition = position
    }
}
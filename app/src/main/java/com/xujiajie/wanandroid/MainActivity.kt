package com.xujiajie.wanandroid

import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.os.StatFs
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.xujiajie.wanandroid.base.BaseMActivity
import com.xujiajie.wanandroid.databinding.ActivityMainBinding
import com.xujiajie.wanandroid.ui.fragment.HomeFragment1
import com.xujiajie.wanandroid.ui.fragment.WebFragment
import com.xujiajie.wanandroid.ui.fragment.HomeProjectFragment
import com.xujiajie.wanandroid.ui.fragment.HomeSetUpFragment
import com.xujiajie.wanandroid.utils.ToastUtils

class MainActivity : BaseMActivity<MainViewModel, ActivityMainBinding>() {
    private val fragments = arrayListOf<Fragment>()
    private var lastPosition: Int = 0
    private var mPosition: Int = 0
    private var currentFragment: Fragment? = null//要显示的Fragment
    private var hideFragment: Fragment? = null//要隐藏的Fragment
    override fun initView(savedInstanceState: Bundle?) {
        mLocationHelp = LocationHelp(activityResultRegistry, mContext)
        lifecycle.addObserver(mLocationHelp)
        mLocationHelp.onCreate(this@MainActivity)
        fragments?.apply {
            add(HomeFragment1())
            add(HomeSetUpFragment.newInstance())
            add(HomeProjectFragment())
            add(WebFragment())
        }
        val statFs = StatFs(Environment.getExternalStorageDirectory().path)


        //这两个方法是直接输出总内存和可用空间，也有getFreeBytes
        //API level 18（JELLY_BEAN_MR2）引入


        //这两个方法是直接输出总内存和可用空间，也有getFreeBytes
        //API level 18（JELLY_BEAN_MR2）引入
        val totalSize = statFs.totalBytes
        val availableSize = statFs.availableBytes
        val a = (totalSize shr 30)
        Log.d(TAG, "init:totalSize= $a   ${totalSize/1024/1024/1024}")


        mBinding?.bottomNavView?.apply {
            setOnNavigationItemSelectedListener {
                when (it.itemId) {
                    R.id.home_page -> {
                        setSelectedFragment(0)
                    }
                    R.id.home_system -> {
                        setSelectedFragment(1)
                    }
                    R.id.home_project -> {
                        setSelectedFragment(2)
                    }
                    R.id.home_plaza -> {
                        setSelectedFragment(3)
                    }
                    R.id.home_mine -> {

                    }
                }
                true
            }
        }
        setSelectedFragment(mPosition)
    }

    private lateinit var mLocationHelp: LocationHelp
    override fun initImmersionBar() {
        super.initImmersionBar()

        observer = MyLifecycleObserver(getActivityResultRegistry())
        lifecycle.addObserver(observer)

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

    lateinit var observer: MyLifecycleObserver

    // 请求单个权限
    private val requestPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            // Do something if permission granted
            if (isGranted) ToastUtils.showToast(mContext, "Permission is granted")
            else ToastUtils.showToast(mContext, "Permission is denied")
        }

    // 请求一组权限
    private val requestMultiplePermissions =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions: Map<String, Boolean> ->
            // Do something if some permissions granted or denied
            permissions.entries.forEach {
                // Do checking here
            }
        }

    // 请求一组权限
    private val requestPickContact =
        registerForActivityResult(ActivityResultContracts.PickContact()) {
            it
            Log.d(TAG, ": $it")
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


    override fun onResume() {
        super.onResume()
        val list = arrayListOf<String>()
        for (i in 0..10){
            if ( i == 3){
                log()
            }
        }

        for (i in 0 until 10){

        }
        list.forEachIndexed { index, s ->  }
        for (s in list) {

        }
    }

    private fun log() {
        Log.d(TAG, "onResume: **********3${aaa(1,2)}")
    }

    private fun aaa(a:Int, b :Int):Int{
        return a*b
    }
}
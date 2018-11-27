package com.moodsmap.waterlogging.presentation.base_mvp.base

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import com.moodsmap.waterlogging.R
import com.moodsmap.waterlogging.presentation.kotlinx.extensions.showToast
import com.moodsmap.waterlogging.ui.main.MainContract
import com.moodsmap.waterlogging.view.view.BottomTabView
import kotlinx.android.synthetic.main.activity_base_bottom_tab.*
import java.util.*

/**
 * 应用主tab activity  继承者无需做任何事
 */
abstract class BottomTabBaseActivity : BaseActivity<MainContract.View, MainContract.Presenter>(), MainContract.View {

    protected abstract val tabViews: List<BottomTabView.TabItemView>

   /* protected val centerView: View
        get() = inflater.inflate(R.layout.main_center,null)
*/
    protected lateinit var adapter: FragmentPagerAdapter

//    protected abstract fun onTabItemSelect(pos:Int)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base_bottom_tab)

        //中心圆形按钮
       /* val centerView=inflater.inflate(R.layout.main_center,null)
        val img=centerView.findViewById<ImageView>(R.id.main_center_img)
        val tv=centerView.findViewById<TextView>(R.id.main_center_tv)
        centerView.setOnClickListener {
            viewPager.setCurrentItem(4, false)
            img.isSelected=true
            tv.setTextColor(takeColor(R.color.mainColor))
        }*/
        bottomTabView.setTabItemViews(tabViews, null)

        viewPager.offscreenPageLimit = getFragments().size
        adapter = object : FragmentPagerAdapter(supportFragmentManager) {
            override fun getItem(position: Int): Fragment {
                return getFragments()[position]
            }

            override fun getCount(): Int {
                return getFragments().size
            }
        }

        viewPager.adapter = adapter

        bottomTabView.setOnTabItemSelectListener { position ->
//            img.isSelected=false
//            tv.setTextColor(takeColor(R.color.text_2))
            viewPager.setCurrentItem(position, false)
        }

        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
                bottomTabView.updatePosition(position)
            }

            override fun onPageScrollStateChanged(state: Int) {

            }
        })
       /* viewPager.setCurrentItem(4, false)
        img.isSelected=true
        bottomTabView.updatePosition(4)*/
    }

    protected abstract fun getFragments(): List<Fragment>


    private var isExit = false
    @SuppressLint("MissingSuperCall")
    override fun onBackPressed() {
        if (!isExit) {
            isExit = true
            showToast(R.string.quit_ask)
            Timer().schedule(object : TimerTask() {
                override fun run() {
                    isExit = false
                }
            }, 2000)
        } else {
            finish()
        }
    }
}

package com.sniperking.keeplovepet.ui.view

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.text.TextUtils
import android.util.AttributeSet
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomnavigation.LabelVisibilityMode
import com.sniperking.keeplovepet.R.*
import com.sniperking.keeplovepet.utils.AppConfig
import com.sniperking.keeplovepet.utils.dp2px


class AppBottomBar : BottomNavigationView {

    val icons = arrayListOf<Int>(drawable.ic_home_black_24dp,drawable.ic_dashboard_black_24dp,drawable.ic_notifications_black_24dp)

    constructor(context: Context?) : this(context,null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context,attrs,0)
    @SuppressLint("RestrictedApi")
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context,attrs,defStyleAttr){
        val bottomBar = AppConfig.bottomBar
        val tabs = bottomBar.tabs

        val state = arrayOfNulls<IntArray>(2)
        state[0] = intArrayOf(android.R.attr.state_selected)
        state[1] = intArrayOf()
        val colors = intArrayOf(
            Color.parseColor(bottomBar.activeColor),
            Color.parseColor(bottomBar.inActiveColor)
        )
        val colorStateList = ColorStateList(state,colors)

        //icon选中颜色变化
        itemIconTintList = colorStateList
        //text选中颜色变化
        itemTextColor = colorStateList
        //文字显示模式
        //LABEL_VISIBILITY_LABELED:设置按钮的文本为一直显示模式
        //LABEL_VISIBILITY_AUTO:当按钮个数小于三个时一直显示，或者当按钮个数大于3个且小于5个时，被选中的那个按钮文本才会显示
        //LABEL_VISIBILITY_SELECTED：只有被选中的那个按钮的文本才会显示
        //LABEL_VISIBILITY_UNLABELED:所有的按钮文本都不显示
        labelVisibilityMode = LabelVisibilityMode.LABEL_VISIBILITY_LABELED

        //选中tab的id
        selectedItemId = bottomBar.selectTab

        tabs.forEach {
            if (!it.enable){
                return
            }
            val id = getId(it.pageUrl)
            if (id < 0){
                return
            }
            val menuItem = menu.add(0, id, it.index, it.title)
            menuItem.setIcon(icons[it.index])
        }
        tabs.forEach{
            val i = dp2px(it.size)
            val menuView : BottomNavigationMenuView = getChildAt(0) as BottomNavigationMenuView
            val itemView : BottomNavigationItemView = menuView.getChildAt(it.index) as BottomNavigationItemView
            itemView.setIconSize(i)
            if (TextUtils.isEmpty(it.title)){
                //设置icon颜色
                itemView.setIconTintList(ColorStateList.valueOf(Color.parseColor(it.tintColor)))
                //按下不让他上线浮动
                itemView.setShifting(false)
            }
        }
    }

    private fun getId(pageUrl: String): Int {
        val destination = AppConfig.destConfig[pageUrl] ?: return -1
        return destination.id
    }
}
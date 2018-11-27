package com.moodsmap.waterlogging.presentation.navigation

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import com.moodsmap.waterlogging.R
import com.moodsmap.waterlogging.di.scope.PerActivity
import com.moodsmap.waterlogging.presentation.kotlinx.extensions.log
import javax.inject.Inject
import com.moodsmap.waterlogging.presentation.utils.Experimental

/**
 */

@PerActivity
class Navigator @Inject constructor(private val activity: AppCompatActivity,
                                    private val fragmentManager: FragmentManager) {

    interface FragmentChangeListener {
        fun onFragmentChanged(currentTag: String, currentFragment: Fragment) {}
    }

    private var fragmentMap: LinkedHashMap<String, Screen> = linkedMapOf()
    lateinit var fragmentChangeListener: FragmentChangeListener

    private val containerId = R.id.container
    public var activeTag: String? = null
    private var rootTag: String? = null
    private var isCustomAnimationUsed = false

    private fun runDebugLog() {
        log {
            "Chain [${fragmentMap.size}] - ${fragmentMap.keys.joinToString(" -> ") {
                val split: List<String> = it.split(".")
                split[split.size - 1]
            }}"
        }
    }

    private fun addOpenTransition(transaction: FragmentTransaction, withCustomAnimation: Boolean) {
        if (withCustomAnimation) {
            isCustomAnimationUsed = true
            transaction.setCustomAnimations(R.anim.slide_in_start, 0)
        } else {
            isCustomAnimationUsed = false
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        }
    }

    private fun invokeFragmentChangeListener(tag: String?) {
        tag?.let {
            val screen = fragmentMap[it]
            screen?.let {
                fragmentChangeListener.onFragmentChanged(tag, it.fragment)
            }
        }
    }

    fun getFragment(tag: String):Fragment?{
        val screen = fragmentMap[tag]
        return screen?.fragment
    }

    fun getState(): NavigationState {
        return NavigationState(activeTag, rootTag, isCustomAnimationUsed)
    }

    fun restore(state: NavigationState) {
        activeTag = state.activeTag
        rootTag = state.firstTag
        isCustomAnimationUsed = state.isCustomAnimationUsed
        state.clear()

        fragmentMap.clear()
//        fragmentManager.fragments.forEach {
//            log {
//                "Fragment manager fragment - ${it::class.java.simpleName}"
//            }
//        }
        fragmentManager.fragments
                .filter { it.tag!!.contains(activity.applicationContext.packageName) }
                .forEach {
                    fragmentMap[it.tag!!] = Screen(it, BackStrategy.KEEP)
                }

        fragmentManager.inTransaction {
            fragmentMap
                    .filter { it.key != activeTag }
                    .forEach {
                        hide(it.value.fragment)
                    }
            show(fragmentMap[activeTag]?.fragment)
        }
        invokeFragmentChangeListener(activeTag)
        runDebugLog()
    }

    inline fun <reified T : Fragment> goTo(keepState: Boolean = true,
                                           withCustomAnimation: Boolean = false,
                                           arg: Bundle = Bundle.EMPTY,
                                           @Experimental
                                           backStrategy: BackStrategy = BackStrategy.DESTROY) {
        val tag = T::class.java.name
        goTo(tag, keepState, withCustomAnimation, arg, backStrategy)
    }

    @PublishedApi
    internal fun goTo(tag: String,
                      keepState: Boolean,
                      withCustomAnimation: Boolean,
                      arg: Bundle,
                      backStrategy: BackStrategy) {
        if (activeTag == tag)
            return

        if (!fragmentMap.containsKey(tag) || !keepState) {
            val fragment = Fragment.instantiate(activity, tag)
            if (!arg.isEmpty) {
                fragment.arguments = arg
            }


            if (!keepState) {
                val weakFragment = fragmentManager.findFragmentByTag(tag)
                weakFragment?.let {
                    fragmentManager.inTransaction {
                        remove(weakFragment)
                    }
                }
            }
            fragmentManager.inTransaction {
                addOpenTransition(this, withCustomAnimation)
                add(containerId, fragment, tag)
            }


            fragmentMap.put(tag, Screen(fragment, backStrategy))

            if (activeTag == null) {
                rootTag = tag
            }
        }

        fragmentManager.inTransaction {
            addOpenTransition(this, withCustomAnimation)
            fragmentMap
                    .filter { it.key != tag }
                    .forEach {
                        hide(it.value.fragment)
                    }
            show(fragmentMap[tag]?.fragment)
        }
        activeTag = tag
        invokeFragmentChangeListener(tag)

        fragmentMap.replaceValue(tag, fragmentMap[tag])

        runDebugLog()
    }

    fun hasBackStack(): Boolean {
        val b=fragmentMap.size > 0 /*&& activeTag != rootTag*/
        if(fragmentMap.size==1)destory()
        return b
    }

    fun hasBackStack2(): Boolean {
        val b=fragmentMap.size > 1 && activeTag != rootTag
        return b
    }

    fun fragmetGoBack() {
        val b=fragmentMap.size > 0
        if(b){
            if(fragmentMap.size==1)destory()
            else{
                goBack()
            }
        }
    }

    fun destory(){
         fragmentMap.forEach{
             fragmentManager.inTransaction {
                 remove(it.value.fragment)
             }
         }
         activeTag=null
         fragmentMap.clear()
     }

    fun destory(tag:String){
        fragmentMap.remove(tag)
    }

    fun goBack() {
        val b=fragmentMap.size > 1 && activeTag != rootTag
        if(!b)return
        val screen = fragmentMap[activeTag]
        var backStrategy = screen?.backStrategy
        var isKeep = backStrategy is BackStrategy.KEEP
//        backStrategy = BackStrategy.DESTROY
//        isKeep=false
        fragmentManager.inTransaction {
            if (isCustomAnimationUsed)
                setCustomAnimations(0, R.anim.slide_out_finish)
            if (isKeep) {
                hide(screen?.fragment)
            } else if (backStrategy is BackStrategy.DESTROY) {
                remove(screen?.fragment)
            }
        }

        val keys = fragmentMap.keys
        val currentTag = if (isKeep) {
            val index = keys.indexOf(activeTag)
            keys.elementAt(index - 1)
        } else {
            fragmentMap.remove(activeTag)
            keys.last()
        }

        fragmentManager.inTransaction {
            if (!isCustomAnimationUsed) {
                setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            }
            show(fragmentMap[currentTag]?.fragment)
        }
        isCustomAnimationUsed = false
        activeTag = currentTag
        invokeFragmentChangeListener(currentTag)
        runDebugLog()
    }

    private inline fun FragmentManager.inTransaction(transaction: FragmentTransaction.() -> Unit) {
        val fragmentTransaction = this.beginTransaction()
        fragmentTransaction.transaction()
        fragmentTransaction.commitNow()
    }

    private fun <K, V> MutableMap<K, V>.replaceValue(key: K, value: V?) {
        this.remove(key)
        value?.let {
            this.put(key, value)
        }
    }
}

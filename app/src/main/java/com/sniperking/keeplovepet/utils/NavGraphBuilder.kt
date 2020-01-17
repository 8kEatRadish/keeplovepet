package com.sniperking.keeplovepet.utils

import android.content.ComponentName
import android.util.Log
import androidx.navigation.*
import androidx.navigation.fragment.FragmentNavigator

class NavGraphBuilder {
    companion object {
        fun builder(controller: NavController) {
            val provider = controller.navigatorProvider
            val fragmentNavigator = provider.getNavigator(FragmentNavigator::class.java)
            val activityNavigator = provider.getNavigator(ActivityNavigator::class.java)
            val navGraph = NavGraph(NavGraphNavigator(provider))

            val destConfig = AppConfig.destConfig
            for (value in destConfig.values) {
                if (value.isFragment) {
                    var destination = fragmentNavigator.createDestination()
                    destination.className = value.className
                    destination.id = value.id
                    destination.addDeepLink(value.pageUrl)


                    navGraph.addDestination(destination)
                } else {
                    var destination = activityNavigator.createDestination()
                    destination.setComponentName(
                        ComponentName(
                            AppGlobals.getApplication().packageName,
                            value.className
                        )
                    )
                    destination.id = value.id
                    destination.addDeepLink(value.pageUrl)

                    navGraph.addDestination(destination)
                }

                if (value.asStarter) {
                    navGraph.startDestination = value.id
                }
                Log.d("suihw >> " , "${value.className}")
            }
            controller.graph = navGraph
        }
    }
}
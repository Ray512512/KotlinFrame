package com.ray.frame.presentation.navigation

import com.ray.frame.presentation.utils.Experimental

/**
 */
@Experimental
sealed class BackStrategy {

    object KEEP : BackStrategy()
    object DESTROY : BackStrategy()
}
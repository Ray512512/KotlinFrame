package com.moodsmap.waterlogging.presentation.navigation

import com.moodsmap.waterlogging.presentation.utils.Experimental

/**
 */
@Experimental
sealed class BackStrategy {

    object KEEP : BackStrategy()
    object DESTROY : BackStrategy()
}
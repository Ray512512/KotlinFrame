package com.moodsmap.waterlogging.data.api.exception

import java.lang.RuntimeException

/**
 * Created by gong on 2017/10/23.
 */
class ApiException(var code: Int, var msg: String?) : RuntimeException()
package com.moodsmap.waterlogging.data

import com.moodsmap.waterlogging.App



/**
 * Created by gong on 2017/11/22.
 */
object AppConst {

    val CACHE_DIR = App.instance.cacheDir.absolutePath
    val warn_type = arrayOf("干旱", "台风", "暴雨", "暴雪", "寒潮", "大风", "沙尘暴", "高温", "雷电", "冰雹", "霜冻", "大雾", "霾", "雷雨大风", "道路结冰", "海上大风", "重污染天气", "地址灾害")
    val warn_type_code = arrayOf("11A52", "11B01", "11B03", "11B04", "11B05", "11B06", "11B07", "11B09", "11B14", "11B15", "11B16", "11B17", "11B19", "11B20", "11B21", "11B23", "11B29", "11D00", "default")

}
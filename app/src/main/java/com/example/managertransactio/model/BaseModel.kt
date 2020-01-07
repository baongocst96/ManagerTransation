package com.example.managervalueapplication.data

import com.example.managervalueapplication.model.Rates
import java.io.Serializable
import java.util.*

interface basmodel: Serializable
/**
 * Created by Ngoctb on 04/12/2019.
 * VNPT Company LTD
 * ngoctb.tgg@vnpt.vn
 */
class BaseModel: basmodel {
//    var userId:  Int = 0
//    var id: Int = 0
//    var title: String = ""
//    var body: String = ""
    var disclaimer:String = ""
    var license: String = ""
    var timestamp: String = ""
    var base: String= ""
    var rates: Map<String, Any> = mapOf("test" to 0.0)


}
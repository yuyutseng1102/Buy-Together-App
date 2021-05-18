package com.chloe.buytogether.collection

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.chloe.buytogether.data.Collections
import com.chloe.buytogether.data.Order
import com.chloe.buytogether.data.source.Repository

class CollectionViewModel(private val repository: Repository): ViewModel() {

    private val _collection = MutableLiveData<List<Collections>>()
    val collection: LiveData<List<Collections>>
        get() = _collection

    // Handle navigation to manage
    private val _navigateToManage = MutableLiveData<Collections>()
    val navigateToManage: LiveData<Collections>
        get() = _navigateToManage

    fun navigateToManage(collection: Collections) {
        Log.d("navigationseries", "navigateToManage_00_inViewmodel = ${_navigateToManage.value}")

        _navigateToManage.value = collection
        Log.d("navigationseries", "navigateToManage_01_inViewmodel = ${_navigateToManage.value}")

    }

    fun onManageNavigated() {
        _navigateToManage.value = null
    }

    //mockData
    private val mockUserId = 193798L
    private val time: Long= java.util.Calendar.getInstance().timeInMillis
    private val method = "揪團團購"
    private val category = "女裝"
    private val country = "日本"
    private val source = ""
    private val isStandard = false
    private val option = listOf("全網站")
    private val deliveryMethod  = "711"
    private val conditionType = 1
    private val deadLine = java.util.Calendar.getInstance().timeInMillis
    private val condition = 5000
    private val status: Int = 0
    private val order:List<Order>? = listOf()




    fun addMockData(){
        val mockCollectList: MutableList<Collections> = mutableListOf()
        mockCollectList.add(Collections(389089,mockUserId,time,method,"https://img3.momoshop.com.tw/1619364953/goodsimg/0008/361/096/8361096_B.jpg", listOf(),"romand唇釉","",category,country,source,isStandard,option,deliveryMethod,conditionType,deadLine,condition,status,order))
        mockCollectList.add(Collections(332798,mockUserId,time,method,"https://img2.momoshop.com.tw/expertimg/0007/989/213/mobile//1.jpg", listOf(),"CHANEL淡香水","",category,country,source,isStandard,option,deliveryMethod,conditionType,deadLine,condition,status,order))
        mockCollectList.add(Collections(885433,mockUserId,time,method,"https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTz-DzMBHjD69t2DehnZqVk7iqMH2NxsRGIBQ&usqp=CAU", listOf(),"ETUDE HOUSE玩轉色彩四色眼彩盤","",category,country,source,isStandard,option,deliveryMethod,conditionType,deadLine,condition,status,order))
        mockCollectList.add(Collections(324566,mockUserId,time,method,"https://img4.momoshop.com.tw/1619225003/goodsimg/0008/435/142/8435142_R.jpg", listOf(),"ettusais絕不失手眼線膠筆","",category,country,source,isStandard,option,deliveryMethod,conditionType,deadLine,condition,status,order))
        mockCollectList.add(Collections(432435,mockUserId,time,method,"https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQKhEkNviKcGv5VF1HKlgNfYr7eV2hqBvydYA&usqp=CAU", listOf(),"niko and...","",category,country,source,isStandard,option,deliveryMethod,conditionType,deadLine,condition,status,order))
        mockCollectList.add(Collections(353254,mockUserId,time,method,"https://img4.momoshop.com.tw/1619308533/goodsimg/0005/951/668/5951668_R.jpg", listOf(),"ettusais高機能毛孔淨透凝膠","",category,country,source,isStandard,option,deliveryMethod,conditionType,deadLine,condition,status,order))
        mockCollectList.add(Collections(432543,mockUserId,time,method,"https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQyY877kzJmXsn21vfh0kDfJoWM11ZWGzvxVA&usqp=CAU", listOf(),"【DHC】濃密保濕潤色唇膏","",category,country,source,isStandard,option,deliveryMethod,conditionType,deadLine,condition,status,order))
        mockCollectList.add(Collections(464575,mockUserId,time,method,"https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTzGTV2OR_4UJC__k6wmYq-9MuiSw6ReecI7w&usqp=CAU", listOf(),"復古おまち堂拉麵主題陶瓷碗","",category,country,source,isStandard,option,deliveryMethod,conditionType,deadLine,condition,status,order))
        _collection.value = mockCollectList
        }
}
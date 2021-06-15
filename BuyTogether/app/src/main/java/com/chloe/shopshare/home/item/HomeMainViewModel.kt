package com.chloe.shopshare.home.item

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.chloe.shopshare.MyApplication
import com.chloe.shopshare.R
import com.chloe.shopshare.data.Shop
import com.chloe.shopshare.data.Order
import com.chloe.shopshare.data.Product
import com.chloe.shopshare.data.Result
import com.chloe.shopshare.data.source.Repository
import com.chloe.shopshare.host.CategoryType
import com.chloe.shopshare.network.LoadApiStatus
import com.chloe.shopshare.util.UserManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.*

class HomeMainViewModel(private val repository: Repository) :ViewModel() {

    private val _shopLinearTop = MutableLiveData<List<Shop>>()
    val shopLinearTop: LiveData<List<Shop>>
        get() = _shopLinearTop

    private val _shopLinearBottom = MutableLiveData<List<Shop>>()
    val shopLinearBottom: LiveData<List<Shop>>
        get() = _shopLinearBottom

    private val _shopGrid = MutableLiveData<List<Shop>>()
    val shopGrid: LiveData<List<Shop>>
        get() = _shopGrid

    private val _navigateToDetail = MutableLiveData<String>()

    val navigateToDetail: LiveData<String>
        get() = _navigateToDetail


    fun navigateToDetail(shop:Shop){
        _navigateToDetail.value = shop.id
    }

    fun onDetailNavigated(){
        _navigateToDetail.value = null
    }

    // status: The internal MutableLiveData that stores the status of the most recent request
    private val _status = MutableLiveData<LoadApiStatus>()

    val status: LiveData<LoadApiStatus>
        get() = _status

    // error: The internal MutableLiveData that stores the error of the most recent request
    private val _error = MutableLiveData<String>()

    val error: LiveData<String>
        get() = _error

    // status for the loading icon of swl
    private val _refreshStatus = MutableLiveData<Boolean>()

    val refreshStatus: LiveData<Boolean>
        get() = _refreshStatus

    // Create a Coroutine scope using a job to be able to cancel when needed
    private var viewModelJob = Job()

    // the Coroutine runs using the Main (UI) dispatcher
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    val categoryTypeToDisplayTop = CategoryType.WOMAN
    val categoryTypeToDisplayBottom = CategoryType.FOOD

    init {
        getHotShopByTypeTop(categoryTypeToDisplayTop.category)
        getHotShopByTypeBottom(categoryTypeToDisplayBottom.category)
        getNewShop()
    }

    private fun getHotShopByTypeTop(category: Int) {

        coroutineScope.launch {

            _status.value = LoadApiStatus.LOADING

            val result = repository.getHotShopByType(category)

            _shopLinearTop.value =
            when (result) {
                is Result.Success -> {
                    _error.value = null
                    _status.value = LoadApiStatus.DONE
                    result.data
                }
                is Result.Fail -> {
                    _error.value = result.error
                    _status.value = LoadApiStatus.ERROR
                    null
                }
                is Result.Error -> {
                    _error.value = result.exception.toString()
                    _status.value = LoadApiStatus.ERROR
                    null
                }
                else -> {
                    _error.value = MyApplication.instance.getString(R.string.result_fail)
                    _status.value = LoadApiStatus.ERROR
                    null
                }
            }
            _refreshStatus.value = false
        }
    }

    private fun getHotShopByTypeBottom(category: Int) {

        coroutineScope.launch {

            _status.value = LoadApiStatus.LOADING

            val result = repository.getHotShopByType(category)

            _shopLinearBottom.value =
                when (result) {
                    is Result.Success -> {
                        _error.value = null
                        _status.value = LoadApiStatus.DONE
                        result.data
                    }
                    is Result.Fail -> {
                        _error.value = result.error
                        _status.value = LoadApiStatus.ERROR
                        null
                    }
                    is Result.Error -> {
                        _error.value = result.exception.toString()
                        _status.value = LoadApiStatus.ERROR
                        null
                    }
                    else -> {
                        _error.value = MyApplication.instance.getString(R.string.result_fail)
                        _status.value = LoadApiStatus.ERROR
                        null
                    }
                }
            _refreshStatus.value = false
        }
    }


    private fun getNewShop() {

        coroutineScope.launch {

            _status.value = LoadApiStatus.LOADING

            val result = repository.getNewShop()

            _shopGrid.value = when (result) {
                is Result.Success -> {
                    _error.value = null
                    _status.value = LoadApiStatus.DONE
                    result.data
                }
                is Result.Fail -> {
                    _error.value = result.error
                    _status.value = LoadApiStatus.ERROR
                    null
                }
                is Result.Error -> {
                    _error.value = result.exception.toString()
                    _status.value = LoadApiStatus.ERROR
                    null
                }
                else -> {
                    _error.value = MyApplication.instance.getString(R.string.result_fail)
                    _status.value = LoadApiStatus.ERROR
                    null
                }
            }
            _refreshStatus.value = false
        }
    }

    fun refresh() {
        if (status.value != LoadApiStatus.LOADING) {
            getHotShopByTypeTop(categoryTypeToDisplayTop.category)
            getHotShopByTypeBottom(categoryTypeToDisplayBottom.category)
            getNewShop()
        }
    }

}




//mock

//mockData
//    description,category,country,source,isStandard1,option1,deliveryMethod,conditionType,deadLine,condition,status,order
//    description,category,country,source,isStandard2,option2,deliveryMethod,conditionType,deadLine,condition,status,order
//private val mockId = "387986522"
//private val mockUserId = "1873585"
//private val time: Long= java.util.Calendar.getInstance().timeInMillis
//private val method = 1
//private val category = 101
//private val country = 12
//private val source = "Amazon"
//private val isStandard1 = false
//private val isStandard2 = true
//private val option1 = listOf("全網站")
//private val option2 = listOf("棉麻上衣白色/S","棉麻上衣白色/M","法式雪紡背心/S","法式雪紡背心/M","開襟洋裝/M","開襟洋裝/L")
//private val deliveryMethod  = listOf(10,11,12,20)
//private val conditionType = 1
//private val deadLine = java.util.Calendar.getInstance().timeInMillis
//private val condition = 5000
//private val status: Int = 0
//private val description: String ="外觀基本的實用帽款，採用透氣性好的棉麻素材，非常適合接下來的季節。前側的LOGO是為整體畫龍點珠的一點。"
//
//private val orderId1 = "1245"
//private val orderId2 = "1243"
//private val orderId3 = "1241"
//private val orderTime: Long= Calendar.getInstance().timeInMillis
//private val userId = "193798"
//private val products:List<Product> = listOf(Product("棉麻上衣白色/M",1), Product("法式雪紡背心/M",2), Product("開襟洋裝/M",5))
//private val products2:List<Product> = listOf(Product("棉麻上衣白色/M",1), Product("法式雪紡背心/M",2), Product("開襟洋裝/M",5), Product("法式雪紡背心/M",2), Product("開襟洋裝/M",5))
//private val price: Int = 2000
//private val name: String = "艾倫"
//private val phone:String = "0988888888"
//private val delivery: Int = 10
//private val address: String = "永和門市"
//private val note: String? = "無"
//private val mockPaymentStatus: Int = 0
//private val ordersize = 0
//
//
//private val order:List<Order>? =
//    listOf(
//        Order(orderId1, orderTime, userId, products, price, name,phone, delivery,address, note, mockPaymentStatus),
//        Order(orderId2, orderTime, userId, products2, price, name,phone, delivery,address,note,mockPaymentStatus),
//        Order(orderId3, orderTime, userId, products, price, name,phone, delivery,address,note,mockPaymentStatus)
//    )
//
//fun addMockData(){
//    val mockList: MutableList<Shop> = mutableListOf()
//    mockList.add(Shop(mockId,mockUserId,time,method,"https://img3.momoshop.com.tw/1619364953/goodsimg/0008/361/096/8361096_B.jpg", listOf(),"romand唇釉",description,category,country,source,isStandard1,option1,deliveryMethod,conditionType,deadLine,condition,status,ordersize, order))
//    mockList.add(Shop(mockId,mockUserId,time,method,"https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTM7fwC0HwUKjfafbdBYsukgf8t1ZBYo762Fw&usqp=CAU", listOf(),"CHANEL淡香水",description,category,country,source,isStandard1,option1,deliveryMethod,conditionType,deadLine,condition,status,ordersize, order))
//    mockList.add(Shop(mockId,mockUserId,time,method,"https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTq5uNadIKjcytGyeDoALcCLKNsBMvPo2K5rw&usqp=CAU", listOf(),"ETUDE HOUSE玩轉色彩四色眼彩盤",description,category,country,source,isStandard1,option1,deliveryMethod,conditionType,deadLine,condition,status,ordersize, order))
//    mockList.add(Shop(mockId,mockUserId,time,method,"https://img4.momoshop.com.tw/1619225003/goodsimg/0008/435/142/8435142_R.jpg",listOf(),"ettusais絕不失手眼線膠筆", description,category,country,source,isStandard1,option1,deliveryMethod,conditionType,deadLine,condition,status,ordersize, order))
//    mockList.add(Shop(mockId,mockUserId,time,method,"https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT6F63P91Nm2rDYhlBlQGBE1Geg3i07VMxs4A&usqp=CAU", listOf(),"SK-II青春露",description,category,country,source,isStandard1,option1,deliveryMethod,conditionType,deadLine,condition,status,ordersize, order))
//    mockList.add(Shop(mockId,mockUserId,time,method,"https://img4.momoshop.com.tw/1619308533/goodsimg/0005/951/668/5951668_R.jpg",listOf(),"ettusais高機能毛孔淨透凝膠",description,category,country,source,isStandard1,option1,deliveryMethod,conditionType,deadLine,condition,status,ordersize, order))
//    mockList.add(Shop(mockId,mockUserId,time,method,"https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSBgA0NLIkaz8aZ_551fWH-ZfX-zxWexkBoLw&usqp=CAU", listOf(),"【DHC】濃密保濕潤色唇膏","",category,country,"",true,
//        listOf(),deliveryMethod,1,null,100,0,ordersize, listOf()))
//    _shopLinearTop.value = mockList
//
//    val mockList2: MutableList<Shop> = mutableListOf()
//    mockList2.add(Shop(mockId,mockUserId,time,method,"https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQHcBECIeBdgVS0kZwY1Kit5EqnX1sL5GLm7w&usqp=CAU", listOf(),"romand唇釉",description,category,country,source,isStandard2,option2,deliveryMethod,conditionType,deadLine,condition,status,ordersize, order))
//    mockList2.add(Shop(mockId,mockUserId,time,method,"https://img4.momoshop.com.tw/1619308533/goodsimg/0005/951/668/5951668_R.jpg", listOf(),"CHANEL淡香水",description,category,country,source,isStandard2,option2,deliveryMethod,conditionType,deadLine,condition,status,ordersize, order))
//    mockList2.add(Shop(mockId,mockUserId,time,method,"https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcStDmKsN45KBovX2jvcklVHwRoUF0O3qTBsUA&usqp=CAU", listOf(),"ETUDE HOUSE玩轉色彩四色眼彩盤",description,category,country,source,isStandard2,option2,deliveryMethod,conditionType,deadLine,condition,status,ordersize, order))
//    mockList2.add(Shop(mockId,mockUserId,time,method,"https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSEYAlp9mqIc1TjRklZfeS4j-q8r9UDRWkYCQ&usqp=CAU", listOf(),"ettusais絕不失手眼線膠筆",description,category,country,source,isStandard2,option2,deliveryMethod,conditionType,deadLine,condition,status,ordersize, order))
//    mockList2.add(Shop(mockId,mockUserId,time,method,"https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT25FbAx7XhNBFsZ9mxe0e3vXCyu0bSJoA4VQ&usqp=CAU", listOf(),"SK-II青春露","",category,country,"",true,
//        listOf(),deliveryMethod,1,null,100,0, ordersize, listOf()))
//    mockList2.add(Shop(mockId,mockUserId,time,method,"https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRxVdU_us_VI59gPLoY-tW9wK_l80EjkFO3ww&usqp=CAU", listOf(),"ettusais高機能毛孔淨透凝膠","",category,country,"",true,
//        listOf(),deliveryMethod,1,null,100,0, ordersize, listOf()))
//    mockList2.add(Shop(mockId,mockUserId,time,method,"https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRX_Erjc7ZfyPlnYYEER_hphagqPox8UuHMiQ&usqp=CAU", listOf(),"【DHC】濃密保濕潤色唇膏","",category,country,"",true,
//        listOf(),deliveryMethod,1,null,100,0,ordersize,  listOf()))
//    __shopLinearBottom.value = mockList2
//
//    val mockListGrid: MutableList<Shop> = mutableListOf()
//    mockListGrid.add(Shop(mockId,mockUserId,time,method,"https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRVcD28UyVPjc4yZn1MBlbngYMmrdHB57niuw&usqp=CAU", listOf(),"romand唇釉","",category,country,"",true,
//        listOf(),deliveryMethod,1,null,100,0,ordersize,  listOf()))
//    mockListGrid.add(Shop(mockId,mockUserId,time,method,"https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQIi1pFjy7wfN6OT18Q0OjM2Bp_5tQ1Xy2s2g&usqp=CAU", listOf(),"CHANEL淡香水","",category,country,"",true,
//        listOf(),deliveryMethod,1,null,100,0,ordersize,  listOf()))
//    mockListGrid.add(Shop(mockId,mockUserId,time,method,"https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRGULjUvp1hL76aChWt2LtyHLZcwn4N0AuqSg&usqp=CAU", listOf(),"ETUDE HOUSE玩轉色彩四色眼彩盤","",category,country,"",true,
//        listOf(),deliveryMethod,1,null,100,0,ordersize,  listOf()))
//    mockListGrid.add(Shop(mockId,mockUserId,time,method,"https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQiBr7hpjfc5sUm3TcnBJZproLCLrp8obfBAw&usqp=CAU", listOf(),"ettusais絕不失手眼線膠筆","",category,country,"",true,
//        listOf(),deliveryMethod,1,null,100,0,ordersize,  listOf()))
//    mockListGrid.add(Shop(mockId,mockUserId,time,method,"https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSWjcZR0gbTmt2ZWLBdbfIDNoEGXurMPNvYbQ&usqp=CAU", listOf(),"SK-II青春露","",category,country,"",true,
//        listOf(),deliveryMethod,1,null,100,0,ordersize,  listOf()))
//    mockListGrid.add(Shop(mockId,mockUserId,time,method,"https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ48NRZEaUb8bSV4driOgIHXxbuveLedLw0MQ&usqp=CAU", listOf(),"ettusais高機能毛孔淨透凝膠","",category,country,"",true,
//        listOf(),deliveryMethod,1,null,100,0,ordersize,  listOf()))
//    mockListGrid.add(Shop(mockId,mockUserId,time,method,"https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ2XDqhWCB12h1G4FavwejaaAFwOuc3AbBnSw&usqp=CAU", listOf(),"【DHC】濃密保濕潤色唇膏","",category,country,"",true,
//        listOf(),deliveryMethod,1,null,100,0,ordersize,  listOf()))
//    mockListGrid.add(Shop(mockId,mockUserId,time,method,"https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSevYHbNb5ggVTKRTEp4TWLvPAWsnXNMkOtbg&usqp=CAU", listOf(),"ettusais高機能毛孔淨透凝膠","",category,country,"",true,
//        listOf(),deliveryMethod,1,null,100,0,ordersize,  listOf()))
//    mockListGrid.add(Shop(mockId,mockUserId,time,method,"https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ07YBrSY_g7NpwPXS3hqL1yccnzauNIH4dTw&usqp=CAU", listOf(),"【DHC】濃密保濕潤色唇膏","",category,country,"",true,
//        listOf(),deliveryMethod,1,null,100,0,ordersize,  listOf()))
//    mockListGrid.add(Shop(mockId,mockUserId,time,method,"https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRruo74uO75A4VvhHvO0b431n6KxTS8KiibcA&usqp=CAU", listOf(),"【DHC】濃密保濕潤色唇膏","",category,country,"",true,
//        listOf(),deliveryMethod,1,null,100,0,ordersize,  listOf()))
//    mockListGrid.add(Shop(mockId,mockUserId,time,method,"https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTZ5iWRj57J-q_vkI_bhiVqInN6SCgSZWoZFQ&usqp=CAU", listOf(),"【DHC】濃密保濕潤色唇膏","",category,country,"",true,
//        listOf(),deliveryMethod,1,null,100,0,ordersize,  listOf()))
//    mockListGrid.add(Shop(mockId,mockUserId,time,method,"https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRyT_Ar-uhQmIoIqau5-WkzgnFNcPeOyo6NSw&usqp=CAU", listOf(),"【DHC】濃密保濕潤色唇膏","",category,country,"",true,
//        listOf(),deliveryMethod,1,null,100,0,ordersize,  listOf()))
//    _shopGrid.value = mockListGrid
//}


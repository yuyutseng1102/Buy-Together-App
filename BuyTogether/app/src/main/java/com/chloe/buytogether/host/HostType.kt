package com.chloe.buytogether.host

import com.chloe.buytogether.R
import com.chloe.buytogether.util.Util.getString


enum class CategoryType(val positionOnSpinner: Int,val category: Int,val title:String) {
    WOMAN(0, 101,getString(R.string.woman)),
    MAN(1, 102,getString(R.string.man)),
    CHILD(2, 103,getString(R.string.child)),
    SHOES_BAG(3, 104,getString(R.string.shoes_bag)),
    MAKEUP(4, 201,getString(R.string.makeup)),
    HEALTH(5,202,getString(R.string.health)),
    FOOD(6, 301,getString(R.string.food)),
    LIVING(7, 401,getString(R.string.living)),
    APPLIANCE(8, 402,getString(R.string.appliance)),
    PET(9, 501,getString(R.string.pet)),
    STATIONARY(10, 601,getString(R.string.stationary)),
    SPORT(11, 701,getString(R.string.sport)),
    COMPUTER(12, 602,getString(R.string.computer)),
    TICKET(13,801,getString(R.string.ticket)),
    OTHER(14,901,getString(R.string.other))
}


enum class CountryType(val positionOnSpinner: Int,val country: Int,val title:String) {
    TAIWAN(0,11,getString(R.string.taiwan)),
    JAPAN(1,12,getString(R.string.japan)),
    KOREA(2,13,getString(R.string.korea)),
    CHINA(3,14,getString(R.string.china)),
    USA(4,21,getString(R.string.usa)),
    CANADA(5,22,getString(R.string.canada)),
    EU(6,30,getString(R.string.eu)),
    AUSTRALIA(7,40,getString(R.string.australia)),
    SOUTH_EAST_ASIA(8,15,getString(R.string.south_east_asia)),
    OTHER(9,50,getString(R.string.other))
}

enum class ConditionType(val positionOnSpinner: Int) {
    BY_PRICE(0),
    BY_QUANTITY(1),
    BY_MEMBER(2)
}

enum class DeliveryMethod(val delivery: Int,val title:String) {
    SEVEN_ELEVEN(10,getString(R.string.seven_eleven)),
    FAMILY_MART(11,getString(R.string.family_mart)),
    HI_LIFE(12,getString(R.string.hi_life)),
    OK(13,getString(R.string.ok)),
    HOME_DELIVERY(20,getString(R.string.home_delivery)),
    BY_HAND(21,getString(R.string.by_hand))
}
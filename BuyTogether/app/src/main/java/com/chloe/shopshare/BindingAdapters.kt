package com.chloe.shopshare

import android.content.res.ColorStateList
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.Shape
import android.util.Log
import android.view.View
import android.widget.*
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.chloe.shopshare.shop.ShopAdapter
import com.chloe.shopshare.shop.OrderStatusType
import com.chloe.shopshare.shop.PaymentStatusType
import com.chloe.shopshare.manage.MemberAdapter
import com.chloe.shopshare.manage.MemberProductAdapter
import com.chloe.shopshare.data.Shop
import com.chloe.shopshare.data.Notify
import com.chloe.shopshare.data.Order
import com.chloe.shopshare.data.Product
import com.chloe.shopshare.detail.dialog.ProductListAdapter
import com.chloe.shopshare.detail.item.DetailDeliveryAdapter
import com.chloe.shopshare.ext.toDisplayFormat
import com.chloe.shopshare.host.CategoryType
import com.chloe.shopshare.host.CountryType
import com.chloe.shopshare.host.item.GatherOptionAdapter
import com.chloe.shopshare.home.item.HomeCollectAdapter
import com.chloe.shopshare.home.item.HomeGridAdapter
import com.chloe.shopshare.home.item.HomeHots1stAdapter
import com.chloe.shopshare.home.item.HomeHots2ndAdapter
import com.chloe.shopshare.host.DeliveryMethod
import com.chloe.shopshare.host.HostImageAdapter
import com.chloe.shopshare.network.LoadApiStatus
import com.chloe.shopshare.notify.NotifyAdapter
import com.chloe.shopshare.participate.ParticipateAdapter
import com.chloe.shopshare.util.Util.getColor
import com.google.android.material.textfield.TextInputEditText

/**
 * According to [LoadApiStatus] to decide the visibility of [ProgressBar]
 */
//@BindingAdapter("setupApiStatus")
//fun bindApiStatus(){
//}

/**
 * Uses the Glide library to load an image by URL into an [ImageView]
 */
@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgUrl: String?) {
    imgUrl?.let {
        val imgUri = it.toUri().buildUpon().build()
        Glide.with(imgView.context)
            .load(imgUri)
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.ic_placeholder)
                    .error(R.drawable.ic_placeholder))
            .into(imgView)
    }
}

@BindingAdapter("collections")
fun bindRecyclerViewWithCollections(recyclerView: RecyclerView, shop: List<Shop>?) {
    shop?.let {
        recyclerView.adapter?.apply {
            when (this) {
                is HomeHots1stAdapter -> submitList(it)
                is HomeHots2ndAdapter -> submitList(it)
                is HomeCollectAdapter -> submitList(it)
                is HomeGridAdapter -> submitList(it)
                is ShopAdapter -> submitList(it)
            }
        }
    }
}

@BindingAdapter("strings")
fun bindRecyclerViewWithOptionStrings(recyclerView: RecyclerView, options: List<String>?) {
    options?.let {
        recyclerView.adapter?.apply {
            Log.d("Chloe","summit the option list is ${options}")
            when (this) {
                is GatherOptionAdapter -> submitList(it)
                is HostImageAdapter -> submitList(it)
            }
        }
    }
}

@BindingAdapter("delivery")
fun bindRecyclerViewWithDeliveryInt(recyclerView: RecyclerView, delivery: List<Int>?) {
    delivery?.let {
        recyclerView.adapter?.apply {
            Log.d("Chloe","summit the delivery list is $delivery")
            when (this) {
                is DetailDeliveryAdapter -> submitList(it)
            }
        }
    }
}

@BindingAdapter("orders")
fun bindRecyclerViewWithOrders(recyclerView: RecyclerView, orders: List<Order>?) {
    orders?.let {
        recyclerView.adapter?.apply {

            Log.d("Chloe","summit the option list is ${orders}")
            when (this) {
                is MemberAdapter -> submitList(it)
            }
        }

    }
}

@BindingAdapter("products")
fun bindRecyclerViewWithProducts(recyclerView: RecyclerView, products: List<Product>?) {
    products?.let {
        recyclerView.adapter?.apply {
            Log.d("Chloe","summit the option list is ${products}")
            when (this) {
                is MemberProductAdapter -> submitList(it)
                is ProductListAdapter -> submitList(it)
                is ParticipateAdapter -> submitList(it)
            }
        }
    }
}

@BindingAdapter("notify")
fun bindRecyclerViewWithNotify(recyclerView: RecyclerView, notify: List<Notify>?) {
    notify?.let {
        recyclerView.adapter?.apply {
            Log.d("Chloe","summit the notify list is ${notify}")
            when (this) {
                is NotifyAdapter -> submitList(it)
            }
        }

    }
}


@BindingAdapter("timeToDisplayFormat")
fun bindDisplayFormatTime(textView: TextView, time: Long?) {
    textView.text = time?.toDisplayFormat()
}


@BindingAdapter("deadLineToDisplay","conditionType","conditionToDisplay")
fun bindDisplayCondition(textView: TextView,deadLine:Long?,conditionType:Int?,condition:Int?) {

    val deadLineToDisplay: String? = "預計${deadLine?.toDisplayFormat()}收團"

    val conditionToDisplay: String? =
        when (conditionType) {
            0 -> "滿額NT$${condition}止"
            1 -> "徵滿${condition}份止"
            2 -> "徵滿${condition}人止"
            else -> ""
        }

    textView.text =
        if (deadLine == null) {
            conditionToDisplay
        } else if (condition == null) {
            deadLineToDisplay
        } else {
            deadLineToDisplay + "或" + conditionToDisplay
        }
}

@BindingAdapter("categoryToDisplay")
fun bindDisplayCategory(textView:TextView,category:Int) {

        fun getTitle(category:Int): String {
            for (type in CategoryType.values()) {
                if (type.category == category) {
                    return type.title
                }
            }
            return ""
        }

    textView.text = getTitle(category)
}

@BindingAdapter("countryToDisplay")
fun bindDisplayCountry(textView:TextView,country:Int) {

    fun getTitle(country:Int): String {
        for (type in CountryType.values()) {
            if (type.country == country) {
                return type.title
            }
        }
        return ""
    }

    textView.text = getTitle(country)
}

@BindingAdapter("deliveryToDisplay")
fun bindDisplayDelivery(textView:TextView,delivery:Int) {

    fun getTitle(delivery:Int): String {
        for (type in DeliveryMethod.values()) {
            if (type.delivery == delivery) {
                return type.title
            }
        }
        return ""
    }

    textView.text = getTitle(delivery)
}


@BindingAdapter("orderStatusToDisplay")
fun bindDisplayOrderStatus(textView:TextView,status:Int) {

    fun getTitle(status:Int): String {
        for (type in OrderStatusType.values()) {
            if (type.status == status) {
                return type.title
            }
        }
        return ""
    }
    textView.text = getTitle(status)
}

@BindingAdapter("paymentStatusToDisplay")
fun bindDisplayPaymentStatus(textView:TextView,status:Int) {

    fun getTitle(status:Int): String {
        for (type in PaymentStatusType.values()) {
            if (type.paymentStatus == status) {
                return type.title
            }
        }
        return ""
    }
    textView.text = getTitle(status)
}

@BindingAdapter("editorMemberJoined")
fun bindEditorMemberJoined(toggleButton: ToggleButton, paymentStatus: Int) {

    toggleButton.apply {
        visibility =
                when (paymentStatus) {
                    0 -> View.VISIBLE
                    else -> View.GONE
                }
    }
}


@BindingAdapter("isMemberChecked")
fun bindEditorMemberChecked(toggleButton: ToggleButton, isChecked: Boolean) {

    Log.d("Chloe","isChecked really is $isChecked")

    toggleButton.setBackgroundResource(

            when(isChecked){
                true -> R.drawable.ic_check_circle
                else -> R.drawable.ic_check_circle_outline
            }

    )
}

@BindingAdapter("isExpandChecked")
fun bindExpandButtonChecked(toggleButton: ToggleButton, isChecked: Boolean) {

    Log.d("Chloe","isChecked really is $isChecked")

    toggleButton.setBackgroundResource(

            when(isChecked){
                true -> R.drawable.ic_baseline_keyboard_arrow_down_24
                else -> R.drawable.ic_baseline_chevron_right_24
            }

    )
}

@BindingAdapter("editorControllerStatus")
fun bindEditorControllerStatus(imageButton: ImageButton, enabled: Boolean = false) {

    imageButton.apply {
        foreground = ShapeDrawable(object : Shape() {
            override fun draw(canvas: Canvas, paint: Paint) {

                paint.color = Color.BLACK
                paint.style = Paint.Style.STROKE
                paint.strokeWidth = MyApplication.instance.resources
                    .getDimensionPixelSize(R.dimen.edge_select).toFloat()
                canvas.drawRect(0f, 0f, this.width, this.height, paint)
            }
        })
        isClickable = enabled
        backgroundTintList = ColorStateList.valueOf(
            getColor(
                when (enabled) {
                    true -> R.color.black_3f3a3a
                    false -> R.color.gray_999999
                }))
        foregroundTintList = ColorStateList.valueOf(
            getColor(
                when (enabled) {
                    true -> R.color.black_3f3a3a
                    false -> R.color.gray_999999
                }))
    }
}

@BindingAdapter("optionIsStandard","shortOptionDisplay")
fun bindDisplayShortOption(textView: TextView, isStandard:Boolean,option: List<String>?) {
    option?.let { option ->
        textView.apply {
            text =
                when (isStandard) {
                    false -> {
                        option[0]
                    }
                    true -> if (option.size > 2) {
                        "${option[0]}+${option[1]}...共${option.size}項"
                    } else if (option.size == 2) {
                        "${option[0]}+${option[1]}"
                    } else if (option.size == 1) {
                        option[0]
                    } else {
                        ""
                    }
                }
        }
    }

}

@BindingAdapter("memberNumberToDisplay")
fun bindDisplayMemberNumber(textView: TextView, number: Int?) {
    number?.let { number ->
        textView.apply {
            text =
                when (number) {
                    0 -> "尚無人跟團"
                    else -> "已跟團 + ${number}"
                }
        }
    }
}

@BindingAdapter("enableButtonStatus")
fun bindEnableButtonStatus(button: Button, enabled: Boolean = false) {

    button.apply {
        isClickable = enabled
        backgroundTintList = ColorStateList.valueOf(
            getColor(
                when (enabled) {
                    true -> R.color.colorPrimary
                    false -> R.color.gray_cccccc
                }))

    }
}

@BindingAdapter("quantity")
fun bindEditorStatus(textView: TextView, quantity: Int) {
    textView.apply {
        background = ShapeDrawable(object : Shape() {
            override fun draw(canvas: Canvas, paint: Paint) {

                paint.color = Color.BLACK
                paint.style = Paint.Style.STROKE
                paint.strokeWidth = MyApplication.instance.resources
                    .getDimensionPixelSize(R.dimen.edge_select).toFloat()
                canvas.drawRect(0f, 0f, this.width, this.height, paint)
            }
        })
        text =
            when (quantity){
                0 -> ""
                else -> "$quantity"
            }
    }
}


@BindingAdapter("isInvalid","inputTextColorHintPrice")
fun bindEditorPriceStatus(editText: TextInputEditText, isInvalid: Int?, price: Int?) {
    editText.apply {
        setHintTextColor(

                if (isInvalid != null && (price == null || price == 0)) {
                    R.color.red_500
                } else {
                    R.color.gray_646464
                })
    }
}

/**
 * According to [LoadApiStatus] to decide the visibility of [ProgressBar]
 */
@BindingAdapter("setupApiStatus")
fun bindApiStatus(view: ProgressBar, status: LoadApiStatus?) {
    when (status) {
        LoadApiStatus.LOADING -> view.visibility = View.VISIBLE
        LoadApiStatus.DONE, LoadApiStatus.ERROR -> view.visibility = View.GONE
    }
}

/**
 * According to [message] to decide the visibility of [ProgressBar]
 */
@BindingAdapter("setupApiErrorMessage")
fun bindApiErrorMessage(view: TextView, message: String?) {
    when (message) {
        null, "" -> {
            view.visibility = View.GONE
        }
        else -> {
            view.text = message
            view.visibility = View.VISIBLE
        }
    }
}


//
//@BindingAdapter("selected")
//fun bindTextCollectionStatus(textView: TextView, isSelected: Boolean?) {
//    textView.textColors ==
//            if (isSelected == true){
//                ColorSquare("#${it.code}", isSelected = isSelected)
//                ColorStateList.valueOf(getColor(R.color.black_3f3a3a))
//            }else{
//                ColorStateList.valueOf(getColor(R.color.white))
//            }
//
//}





//@BindingAdapter("editorPaymentStatus")
//fun bindEditorPaymentStatus(imageButton: ImageButton, paymentStatus: Int) {
//
//    imageButton.apply {
//        visibility =
//                when(paymentStatus){
//                    0 -> View.VISIBLE
//                    1 -> View.VISIBLE
//                    else -> View.GONE
//                }}
//        backgroundTintList =
//
//                getColor(
//                        when (enabled) {
//                            true -> R.color.black_3f3a3a
//                            false -> R.color.gray_999999
//                        }))
//        foregroundTintList = ColorStateList.valueOf(
//                getColor(
//                        when (enabled) {
//                            true -> R.color.black_3f3a3a
//                            false -> R.color.gray_999999
//                        }))
//    }




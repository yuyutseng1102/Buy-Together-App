package com.chloe.buytogether.detail.dialog

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.chloe.buytogether.NavigationDirections
import com.chloe.buytogether.R
import com.chloe.buytogether.data.Collections
import com.chloe.buytogether.data.Product
import com.chloe.buytogether.databinding.DialogDetailOptionBinding
import com.chloe.buytogether.detail.OptionSelector
import com.chloe.buytogether.ext.getVmFactory
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup


class DetailOptionDialog(private val collection:Collections,private val productList: List<Product>,private val optionSelector: OptionSelector?): BottomSheetDialogFragment()  {

    private val viewModel by viewModels<DetailOptionViewModel> { getVmFactory(collection,productList) }
    private lateinit var binding : DialogDetailOptionBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.DetailOptionDialog)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogDetailOptionBinding.inflate(inflater, container, false)
        binding.viewDialog.startAnimation(AnimationUtils.loadAnimation(context, R.anim.anim_slide_up))
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        val viewId = 0

        fun ChipGroup.addChip(context: Context?, option: List<String>) {
            for (i in option.indices) {
                Chip(context).apply {
                    id = viewId+i
                    text = option[i]
                    isCheckable = true
                    isCloseIconVisible = false
                    isFocusable = true
                    setBackgroundColor(R.drawable.bg_radio_button)
                    addView(this)
                }
                Log.d("Chloe","elements is ${i} to ${option[i]}")
            }

        }


        viewModel.selectedChip.observe(viewLifecycleOwner, Observer {
            it?.let {
                viewModel.getOption()
                Log.d("Chloe","what i choose is ${viewModel.productTitle}")
            }
        })





        var lastCheckedId = View.NO_ID
        binding.chipGroupOption.setOnCheckedChangeListener { group, checkedId ->
            if(checkedId == View.NO_ID) {
                // User tried to uncheck, make sure to keep the chip checked
                group.check(lastCheckedId)
                return@setOnCheckedChangeListener
            }
            lastCheckedId = checkedId

            // New selection happened, do your logic here.
            viewModel.selectedChip.value = lastCheckedId
            Log.d("Chloe","checkid  is ${lastCheckedId}")

        }


        viewModel.option.observe(viewLifecycleOwner, Observer {
            it?.let {
        binding.chipGroupOption.addChip(context,viewModel.option.value!!)
            }
        })


        viewModel.navigateToProductList.observe(viewLifecycleOwner, Observer {
            it?.let {
                optionSelector?.onOptionSelector(it)
                viewModel.onProductListNavigated()
                this.dismiss()
            }

        })

        viewModel.navigateToParticipate.observe(viewLifecycleOwner, Observer {
            it?.let {
                if (viewModel.collection.value!=null) {
                    Log.d("Chloe","navigate to participate is product = $it")
                    findNavController().navigate(
                        NavigationDirections.navigateToParticipateFragment(
                            viewModel.collection.value!!,
                            it.toTypedArray()
                        )
                    )
                    optionSelector?.onOptionSelector(it)
                    viewModel.onProductListNavigated()
                    this.dismiss()
                }
            }

        })


        viewModel.productTitle.observe(viewLifecycleOwner, Observer {
            it?.let {
                viewModel.isEditable()
            }
        })


        return binding.root
    }




}




//        binding.chipGroupOption.setOnCheckedChangeListener { group, checkedId ->
//            var lastCheckedId = View.NO_ID
//            Log.d("Chloe","checkedId  is $checkedId}")
//            if (checkedId == View.NO_ID) {
//                // User tried to uncheck, make sure to keep the chip checked
//                group.check(lastCheckedId)
//                Log.d("Chloe","if (checkedId == View.NO_ID) checkid  is ${lastCheckedId}")
//                return@setOnCheckedChangeListener
//            }
//            if (checkedId == View.NO_ID){
//                lastCheckedId = lastCheckedId
//            }else{
//                lastCheckedId = checkedId
//                Log.d("Chloe","checkid  is ${lastCheckedId}")
//            }
//            viewModel.selectedChip.value = lastCheckedId
//        }
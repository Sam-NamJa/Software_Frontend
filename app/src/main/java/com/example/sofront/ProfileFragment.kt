package com.example.sofront

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.get
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.sofront.databinding.ActivityProfileBinding

class ProfileFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = ActivityProfileBinding.inflate(layoutInflater)

        binding.profileImg.translationZ = 1f

        val prevPadding:Int = Math.round(resources.displayMetrics.density * 30) //30dp 변환값

        binding.profileScrollView.run {
            header = binding.header
            stickListener = { _ ->
                Log.d("스틱헤더 : ", "붙었다")
                binding.portfolioBtn.setPadding(0,0,0,0)
                binding.madePlanBtn.setPadding(0,0,0,0)
            }
            freeListener = { _ ->
                Log.d("스틱헤더 : ", "떨어졌다")
                binding.portfolioBtn.setPadding(0,0,prevPadding,0)
                binding.madePlanBtn.setPadding(prevPadding,0,0,0)
            }
        }

        binding.suboreditBtn.text = "편집"

        val fragmentList = listOf(ProfilePortfolioFragment(), ProfilePlanFragment())
        val adapter = ProfileVPAdapter(requireActivity())
        adapter.fragments = fragmentList
        binding.profileMainSheet.adapter = adapter

        binding.portfolioBtn.setOnClickListener{
            binding.profileMainSheet.setCurrentItem(0, true)
        }
        binding.madePlanBtn.setOnClickListener{
            binding.profileMainSheet.setCurrentItem(1, true)
        }

        binding.profileMainSheet.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                val view = (binding.profileMainSheet[0] as RecyclerView).layoutManager?.findViewByPosition(position)
                view?.post {
                    val wMeasureSpec =
                        View.MeasureSpec.makeMeasureSpec(view.width, View.MeasureSpec.EXACTLY)
                    val hMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
                    view.measure(wMeasureSpec, hMeasureSpec)
                    if (binding.profileMainSheet.layoutParams.height != view.measuredHeight) {
                        binding.profileMainSheet.layoutParams = (binding.profileMainSheet.layoutParams).also { lp ->
                            lp.height = view.measuredHeight
                        }
                    }
                }
                if(position == 0){
                    val pfShape : GradientDrawable = binding.portfolioBtn.background as GradientDrawable
                    pfShape.setColor(Color.parseColor("#61A4BC"))
                    val plShape : GradientDrawable = binding.madePlanBtn.background as GradientDrawable
                    plShape.setColor(Color.parseColor("#3F3F3F"))
                }else{
                    val plShape : GradientDrawable = binding.madePlanBtn.background as GradientDrawable
                    plShape.setColor(Color.parseColor("#61A4BC"))
                    val pfShape : GradientDrawable = binding.portfolioBtn.background as GradientDrawable
                    pfShape.setColor(Color.parseColor("#3F3F3F"))
                }
            }
        })
        return binding.root
    }

}
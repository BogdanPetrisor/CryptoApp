package com.example.cryptoapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.cryptoapp.databinding.FragmentHomeScreenBinding
import com.example.cryptoapp.movie.ResultModel
import com.example.cryptoapp.movie.ViewPageAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {
    private var _binding: FragmentHomeScreenBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        TheMovieDBRepository().apply {

            val galleryList = mutableListOf<ResultModel>(
                ResultModel(false,"/nmGWzTLMXy9x7mKd8NKPLmHtWGa.jpg"),
                ResultModel(true,"/k5SYb9AM0x4IGVxG8fYEEH4wK1I.jpg")
            )
            binding.viewPager.adapter = ViewPageAdapter().apply {
                submitList(galleryList)
            }

            binding.viewPager.registerOnPageChangeCallback(object :
                ViewPager2.OnPageChangeCallback() {
                override fun onPageScrollStateChanged(state: Int) {
                    super.onPageScrollStateChanged(state)
                    binding.indicator.onPageScrollStateChanged(state)
                }

                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    binding.indicator.onPageSelected(position)
                }
            })
            binding.indicator.setPageSize(
                galleryList.size
            )
            binding.indicator.notifyDataChanged()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
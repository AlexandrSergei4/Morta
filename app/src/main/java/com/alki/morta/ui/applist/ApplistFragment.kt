package com.alki.morta.ui.applist

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.alki.morta.R
import com.alki.morta.databinding.FragmentApplistBinding

class ApplistFragment : Fragment() {

    private lateinit var applistViewModel: ApplistViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        applistViewModel =
            ViewModelProvider(this, ApplistViewModel.Factory(requireActivity().application)).get(ApplistViewModel::class.java)
        val binding = FragmentApplistBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.appItems.adapter = ApplistAdapter()
        applistViewModel.appList.observe(this, Observer {
            (binding.appItems.adapter as ApplistAdapter).submitList(it)
        })
        return binding.root
    }
}
package com.alki.morta

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.alki.morta.databinding.InstalledAppsFragmentBinding
import com.alki.morta.ui.InstalledAppsAdapter

class InstalledApps : Fragment() {

    companion object {
        fun newInstance() = InstalledApps()
    }

    private lateinit var viewModel: InstalledAppsViewModel
    private lateinit var binding: InstalledAppsFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = InstalledAppsFragmentBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.installedAppsRecycleView.adapter = InstalledAppsAdapter()
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this, InstalledAppsViewModel.Factory(requireActivity().application)).get(InstalledAppsViewModel::class.java)
        binding.viewModel = viewModel

    }

}
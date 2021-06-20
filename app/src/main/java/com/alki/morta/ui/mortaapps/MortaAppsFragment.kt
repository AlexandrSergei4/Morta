package com.alki.morta.ui.mortaapps

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.alki.morta.databinding.MortaAppsFragmentBinding
import com.alki.morta.ui.appdetail.AppClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MortaAppsFragment : Fragment() {

    private val viewModel: MortaAppsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = MortaAppsFragmentBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        binding.appItems.adapter = MortaAppsListAdapter(AppClickListener {
            viewModel.onClickAppItem(it)
        })
        viewModel.navigateToMortaAppDetail.observe(
            viewLifecycleOwner,
            Observer {
                if (it != null) {
                    findNavController().navigate(
                        MortaAppsFragmentDirections.actionSensitiveAppsToAppDetailFragment(it)
                    )
                    viewModel.navigationDone()
                }
            }
        )
//        viewModel.appList.observe(viewLifecycleOwner, Observer {
//            (binding.appItems.adapter as MortaAppsListAdapter).submitList(it)
//        })
        return binding.root
    }
}
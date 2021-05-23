package com.alki.morta.ui.installedapps

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.alki.morta.databinding.InstalledAppsFragmentBinding
import com.alki.morta.ui.appdetail.AppClickListener
import com.alki.morta.ui.mortaapps.MortaAppsFragmentDirections

class InstalledAppsFragment : Fragment() {

    private lateinit var viewModel: InstalledAppsViewModel
    private lateinit var binding: InstalledAppsFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this,
            InstalledAppsViewModel.Factory(requireActivity().application)
        ).get(InstalledAppsViewModel::class.java)

        binding = InstalledAppsFragmentBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.installedAppsRecycleView.adapter = InstalledAppsAdapter(AppClickListener { viewModel.onClickAppItem(it)})
        binding.viewModel = viewModel

        viewModel.navigateToMortaAppDetail.observe(
            viewLifecycleOwner,
            Observer{
                if (it!=null) {
                    findNavController().navigate(
                        InstalledAppsFragmentDirections.actionInstalledAppsToAppDetailFragment(it)
                    )
                    viewModel.navigationDone()
                }
            }
        )

        return binding.root
    }

}
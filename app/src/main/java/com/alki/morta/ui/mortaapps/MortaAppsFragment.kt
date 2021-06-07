package com.alki.morta.ui.mortaapps

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.alki.morta.databinding.MortaAppsFragmentBinding
import com.alki.morta.ui.appdetail.AppClickListener

class MortaAppsFragment : Fragment() {

    private lateinit var viewModel: MortaAppsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel =
            ViewModelProvider(this, MortaAppsViewModel.Factory(requireActivity().application)).get(
                MortaAppsViewModel::class.java)
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onDetach() {
        super.onDetach()
    }
}
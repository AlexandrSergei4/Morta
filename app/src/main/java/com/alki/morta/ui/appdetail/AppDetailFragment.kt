package com.alki.morta.ui.appdetail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.alki.morta.databinding.AppDetailFragmentBinding

class AppDetailFragment : Fragment() {

    private lateinit var viewModel: AppDetailViewModel

    lateinit var binding: AppDetailFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = AppDetailFragmentBinding.inflate(inflater, container,false)
        val packageName = AppDetailFragmentArgs.fromBundle(requireArguments()).packageName
        viewModel = ViewModelProvider(this, AppDetailViewModel.Factory(requireActivity().application,packageName)).get(AppDetailViewModel::class.java)
        binding.viewModel = viewModel
        viewModel.installedAppDb.observe(viewLifecycleOwner,
        Observer {
            if (it != null) {
                setBaseAppProps(it.applicationName, it.packageName)
            }
        })

        viewModel.mortaAppDb.observe(viewLifecycleOwner,
            Observer {
                if (it != null) {
                    setBaseAppProps(it.applicationName, it.packageName)
                    binding.hasSecurityData.isChecked= true
                    binding.contactPhone.setText(it.phone, TextView.BufferType.EDITABLE)
                    binding.contactEmail.setText(it.email,TextView.BufferType.EDITABLE)
                    binding.howRestoreInfo.text = it.howRestoreInfo
                    binding.howToBlockInfo.text = it.howBlockInfo
                    binding.threatTypes.selectedThreatTypes = it.threatTypes.split(",").map { it.trim() }

                }
            })

        viewModel.allThreatTypes.observe(viewLifecycleOwner,
        Observer {
            binding.threatTypes.allThreatTypes = it.map { it.threatName }
        })

        return binding.root
    }

    private fun setBaseAppProps(applicationName:String, packageName:String){
        binding.applicationName.text = applicationName
        binding.applicationPackage.text = packageName
        binding.appIcon.setImageDrawable(requireContext().packageManager.getApplicationIcon(packageName))

    }
}
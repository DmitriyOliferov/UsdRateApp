package com.oliferov.usdrateapp.presentation.listusd

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.oliferov.usdrateapp.databinding.FragmentListUsdRateBinding
import com.oliferov.usdrateapp.databinding.LayoutDialogBinding
import com.oliferov.usdrateapp.di.UsdRateApplication
import com.oliferov.usdrateapp.presentation.ViewModelFactory
import javax.inject.Inject

class CustomDialog: DialogFragment() {

    private var _binding: LayoutDialogBinding? = null
    private val binding: LayoutDialogBinding
        get() = _binding ?: throw RuntimeException("LayoutDialogBinding is null")

    private lateinit var viewModel: UsdRateListViewModel

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val component by lazy {
        (requireActivity().application as UsdRateApplication).component
    }

    override fun onAttach(context: Context) {
        component.inject(this)
        super.onAttach(context)
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = LayoutDialogBinding.inflate(
            inflater,
            container,
            false
        )
        getDialog()?.setTitle("Уведомление");
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this,viewModelFactory)[UsdRateListViewModel::class.java]
        binding.btnActive.setOnClickListener{
            if(!binding.etRate.text.isNullOrBlank()) {
                viewModel.addNotificationUsdRate(binding.etRate.text.toString())
                dialog?.dismiss()
            } else {
                binding.etRate.error = "Некорректное значение"
            }
        }
        binding.btnCancel.setOnClickListener {
            dialog?.dismiss()
        }

    }
}
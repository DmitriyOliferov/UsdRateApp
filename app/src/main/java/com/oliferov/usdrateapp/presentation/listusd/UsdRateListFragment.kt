package com.oliferov.usdrateapp.presentation.listusd

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.utils.ColorTemplate
import com.oliferov.usdrateapp.R
import com.oliferov.usdrateapp.databinding.FragmentListUsdRateBinding
import com.oliferov.usdrateapp.di.UsdRateApplication
import com.oliferov.usdrateapp.presentation.ViewModelFactory
import javax.inject.Inject

class UsdRateListFragment : Fragment() {

    private var _binding: FragmentListUsdRateBinding? = null
    private val binding: FragmentListUsdRateBinding
        get() = _binding ?: throw RuntimeException("FragmentListUsdRateBinding is null")

    private lateinit var viewModel: UsdRateListViewModel

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val component by lazy {
        (requireActivity().application as UsdRateApplication).component
    }

    private val adapterUsdRateList by lazy {
        UsdRateListAdapter()
    }

    override fun onAttach(context: Context) {
        component.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListUsdRateBinding.inflate(
            inflater,
            container,
            false
        )
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        createAdapter()
        setNotification()
        loadData()
    }

    fun createAdapter() {
        viewModel = ViewModelProvider(
            this,
            viewModelFactory
        )[UsdRateListViewModel::class.java]
        binding.rvListUsdRate.adapter = adapterUsdRateList
        binding.rvListUsdRate.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.VERTICAL,
            false
        )
        viewModel.usdRateList.observe(viewLifecycleOwner) {
            adapterUsdRateList.submitList(it)
            createGraph(viewModel.getListForGraph().reversed())
            binding.tvUsd.text = getString(R.string.one_usd_rate, it.first().value.toString())
        }
    }

    private fun createGraph(list: List<BarEntry>) {
        val barDataSet = BarDataSet(list, "Курс Доллара за месяц")
        barDataSet.colors = ColorTemplate.MATERIAL_COLORS.asList()
        barDataSet.valueTextColor = Color.BLACK
        barDataSet.valueTextSize = 16f

        val barData = BarData(barDataSet)

        with(binding.barChart) {
            setFitBars(true)
            setData(barData)
            description.text = ("Usd rate")
            animateY(2000)
        }
    }

    private fun setNotification() {
        binding.ivSetting.setOnClickListener {
            CustomDialog().show(requireActivity().supportFragmentManager, "notification")
        }
    }

    private fun loadData() {
        binding.ivDownload.setOnClickListener {
            viewModel.loadData()
        }
    }
}
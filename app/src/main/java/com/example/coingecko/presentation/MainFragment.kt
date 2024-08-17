package com.example.coingecko.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.coingecko.R
import com.example.coingecko.app.App
import com.example.coingecko.databinding.FragmentMainBinding
import com.example.coingecko.utils.Constants
import com.example.coingecko.utils.Factory
import kotlinx.coroutines.launch

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<MainViewModel> {
        Factory {
            App.appComponent.mainComponent().viewModel()
        }
    }

    private lateinit var adapter: Adapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecycler()
        usdBtClick()


        binding.buttonUSD.setOnClickListener {
            usdBtClick()
        }

        binding.buttonRUB.setOnClickListener {
            rubBtClick()
        }

        binding.retry.setOnClickListener {
            viewModel.getCoinsList(CurrentCurrency.currentCurrencySearch)
            binding.coinsRecycler.visibility = View.VISIBLE
            binding.errorGroup.visibility = View.GONE
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.observeUi().collect { state ->
                    when(state.isLoading){
                        true -> binding.progressBar.visibility = View.VISIBLE
                        false -> binding.progressBar.visibility = View.GONE
                    }
                    adapter.submitList(state.coinsList)
                    state.error?.let {
                        binding.coinsRecycler.visibility = View.GONE
                        binding.errorGroup.visibility = View.VISIBLE
                    }
                }
            }
        }
    }

    private fun setupRecycler() {
        adapter = Adapter { title, id ->
            findNavController().navigate(
                R.id.action_mainFragment_to_detailsFragment,
                bundleOf("title" to title, "id" to id)
            )
        }
        binding.coinsRecycler.adapter = adapter
        binding.coinsRecycler.layoutManager = LinearLayoutManager(requireContext())
        binding.coinsRecycler.addItemDecoration(DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL))

    }

    private fun usdBtClick(){
        viewModel.getCoinsList(Constants.CURRENCY_USD)
        binding.buttonUSD.setTextAppearance(R.style.orange_style)
        binding.buttonRUB.setTextAppearance(R.style.gray_style)
        binding.buttonUSD.setBackgroundColor(ContextCompat.getColor(requireContext(),R.color.orange_button))
        binding.buttonRUB.setBackgroundColor(ContextCompat.getColor(requireContext(),R.color.gray_button))
        CurrentCurrency.currentCurrency = Constants.USD
        CurrentCurrency.currentCurrencySearch = Constants.CURRENCY_USD
    }

    private fun rubBtClick(){
        viewModel.getCoinsList(Constants.CURRENCY_RUB)
        binding.buttonRUB.setTextAppearance(R.style.orange_style)
        binding.buttonUSD.setTextAppearance(R.style.gray_style)
        binding.buttonRUB.setBackgroundColor(ContextCompat.getColor(requireContext(),R.color.orange_button))
        binding.buttonUSD.setBackgroundColor(ContextCompat.getColor(requireContext(),R.color.gray_button))
        CurrentCurrency.currentCurrency = Constants.RUB
        CurrentCurrency.currentCurrencySearch = Constants.CURRENCY_RUB
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
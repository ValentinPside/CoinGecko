package com.example.coingecko.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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
        viewModel.getCoinsList(Constants.CURRENCY_USD)

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.observeUi().collect { state ->
                    when(state.isLoading){
                        true -> binding.progressBar.visibility = View.VISIBLE
                        false -> binding.progressBar.visibility = View.GONE
                    }
                    adapter.submitList(state.coinsList)
                    state.error?.let { Toast.makeText(requireContext(), getString(it), Toast.LENGTH_SHORT).show() }
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
        binding.coinsRecycler.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                LinearLayoutManager.VERTICAL
            )
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
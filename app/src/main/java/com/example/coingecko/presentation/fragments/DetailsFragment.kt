package com.example.coingecko.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.coingecko.R
import com.example.coingecko.app.App
import com.example.coingecko.databinding.FragmentDetailsBinding
import com.example.coingecko.presentation.view_models.DetailsViewModel
import com.example.coingecko.utils.Factory
import io.noties.markwon.Markwon
import io.noties.markwon.html.HtmlPlugin
import kotlinx.coroutines.launch

class DetailsFragment : Fragment() {

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!
    private val title by lazy { requireArguments().getString("title") }
    private val id by lazy { requireArguments().getString("id") }
    private val viewModel by viewModels<DetailsViewModel> {
        Factory {
            App.appComponent.detailsComponent().create(requireNotNull(id)).viewModel()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setToolBar()

        binding.retry.setOnClickListener {
            id?.let { it1 -> viewModel.getCoinInfo(it1) }
            binding.errorGroup.visibility = View.GONE
            binding.infoGroup.visibility = View.VISIBLE
        }

        val markwon = Markwon.builder(requireContext())
            .usePlugin(HtmlPlugin.create())
            .build()

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.observeUi().collect { state ->
                    when (state.isLoading) {
                        true -> binding.progressBar.visibility = View.VISIBLE
                        false -> binding.progressBar.visibility = View.GONE
                    }
                    state.info?.let { markwon.setMarkdown(binding.info, it) }
                    state.category?.let { markwon.setMarkdown(binding.category, it) }
                    state.imageUrl?.let { setImage(it) }
                    state.error?.let {
                        binding.errorGroup.visibility = View.VISIBLE
                        binding.infoGroup.visibility = View.GONE
                    }
                }
            }
        }
    }

    private fun setImage(imgUrl: String) {
        Glide.with(this)
            .load(imgUrl)
            .placeholder(R.drawable.placeholder)
            .error(R.drawable.placeholder)
            .into(binding.imageView)
    }

    private fun setToolBar() {
        binding.specificToolbars.title = title
        binding.specificToolbars.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
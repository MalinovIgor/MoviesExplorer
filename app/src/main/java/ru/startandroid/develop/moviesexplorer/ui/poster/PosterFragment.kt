package ru.startandroid.develop.moviesexplorer.ui.poster

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import ru.startandroid.develop.moviesexplorer.databinding.FragmentActivityPosterBinding

class PosterFragment : Fragment() {

    companion object {
        private const val POSTER_URL = "poster_url"

        fun newInstance(posterUrl: String) = PosterFragment().apply {
            arguments = Bundle().apply {
                putString(POSTER_URL, posterUrl)
            }
        }
    }

    private val posterViewModel: PosterViewModel by viewModel {
        parametersOf(requireArguments().getString(POSTER_URL))
    }

    private lateinit var binding: FragmentActivityPosterBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentActivityPosterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        posterViewModel.observeUrl().observe(viewLifecycleOwner) {
            showPoster(it)
        }
    }

    private fun showPoster(url: String) {
        context?.let {
            Glide.with(it)
                .load(url)
                .into(binding.poster)
        }
    }
}
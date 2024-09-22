package startandroid.develop.moviesexplorer.ui.cast

import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import ru.startandroid.develop.moviesexplorer.databinding.ListItemHeaderBinding
import startandroid.develop.moviesexplorer.core.ui.RVItem
import startandroid.develop.moviesexplorer.databinding.ListItemCastBinding
import startandroid.develop.moviesexplorer.databinding.ListItemHeaderBinding
import startandroid.develop.moviesexplorer.presentation.cast.MoviesCastRVItem

// Делегат для заголовков на экране актёрского состава
fun movieCastHeaderDelegate() = adapterDelegateViewBinding<MoviesCastRVItem.HeaderItem, RVItem, ListItemHeaderBinding>(
    { layoutInflater, root -> ListItemHeaderBinding.inflate(layoutInflater, root, false) }
) {
    bind {
        binding.headerTextView.text = item.headerText
    }
}

// Делегат для отображения сотрудников на экране актёрского состава
fun movieCastPersonDelegate() = adapterDelegateViewBinding<MoviesCastRVItem.PersonItem, RVItem, ListItemCastBinding>(
    { layoutInflater, root -> ListItemCastBinding.inflate(layoutInflater, root, false) }
) {
    bind {
        if (item.data.image == null) {
            binding.actorImageView.isVisible = false
        } else {
            Glide.with(itemView)
                .load(item.data.image)
                .into(binding.actorImageView)
            binding.actorImageView.isVisible = true
        }

        binding.actorNameTextView.text = item.data.name
        binding.actorDescriptionTextView.text = item.data.description
    }
}

package ru.startandroid.develop.moviesexplorer.ui.movies
import android.os.Handler
import android.app.Activity
import android.os.Bundle
import android.os.Looper
import android.content.Intent
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import ru.startandroid.develop.moviesexplorer.MoviesSearchPresenter
import ru.startandroid.develop.moviesexplorer.util.Creator
import ru.startandroid.develop.moviesexplorer.ui.poster.PosterActivity
import ru.startandroid.develop.moviesexplorer.R
import ru.startandroid.develop.moviesexplorer.domain.models.Movie
import ru.startandroid.develop.moviesexplorer.presentation.movies.MoviesView
import ru.startandroid.develop.moviesexplorer.ui.movies.models.MoviesState
import moxy.MvpAppCompatActivity
class MoviesActivity :  MvpAppCompatActivity(), MoviesView {

    companion object {
        private const val CLICK_DEBOUNCE_DELAY = 1000L
    }

    private lateinit var queryInput: EditText
    private lateinit var placeholderMessage: TextView
    private lateinit var progressBar: ProgressBar
    private lateinit var moviesList: RecyclerView
    private var textWatcher: TextWatcher? = null

    private val adapter = MoviesAdapter {
        if (clickDebounce()) {
            val intent = Intent(this, PosterActivity::class.java)
            intent.putExtra("poster", it.image)
            startActivity(intent)
        }
    }

    private var isClickAllowed = true

    private val handler = Handler(Looper.getMainLooper())

    @InjectPresenter
    lateinit var moviesSearchPresenter: MoviesSearchPresenter

    @ProvidePresenter
    fun providePresenter(): MoviesSearchPresenter {
        Log.d("MoviesActivity", "ProvidePresenter called")
        return Creator.provideMoviesSearchPresenter(
            context = this.applicationContext,
        )
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("MoviesActivity", "onCreate called")
        setContentView(R.layout.activity_movies)

        placeholderMessage = findViewById(R.id.placeholderMessage)
        queryInput = findViewById(R.id.queryInput)
        moviesList = findViewById(R.id.locations)
        progressBar = findViewById(R.id.progressBar)

        moviesList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        moviesList.adapter = adapter
Log.e("presenter", "$moviesSearchPresenter")
        textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (::moviesSearchPresenter.isInitialized) {
                    moviesSearchPresenter.searchDebounce(
                        changedText = s?.toString() ?: ""
                    )
                }
            }

            override fun afterTextChanged(s: Editable?) {
            }
        }
        textWatcher?.let { queryInput.addTextChangedListener(it) }
    }


    override fun onDestroy() {
        super.onDestroy()
        textWatcher?.let { queryInput.removeTextChangedListener(it) }
        moviesSearchPresenter?.onDestroy()
    }

    private fun clickDebounce(): Boolean {
        val current = isClickAllowed
        if (isClickAllowed) {
            isClickAllowed = false
            handler.postDelayed({ isClickAllowed = true }, CLICK_DEBOUNCE_DELAY)
        }
        return current
    }

    fun showLoading() {
        moviesList.visibility = View.GONE
        placeholderMessage.visibility = View.GONE
        progressBar.visibility = View.VISIBLE    }

    fun showError(errorMessage: String) {
        moviesList.visibility = View.GONE
        placeholderMessage.visibility = View.VISIBLE
        progressBar.visibility = View.GONE

        placeholderMessage.text = errorMessage      }

    fun showEmpty(emptyMessage: String) {
        showError(emptyMessage)      }

    fun showContent(movies: List<Movie>) {
        moviesList.visibility = View.VISIBLE
        placeholderMessage.visibility = View.GONE
        progressBar.visibility = View.GONE

        adapter.movies.clear()
        adapter.movies.addAll(movies)
        adapter.notifyDataSetChanged()     }

    override fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    override fun render(state: MoviesState) {
        when (state) {
            is MoviesState.Loading -> showLoading()
            is MoviesState.Content -> showContent(state.movies)
            is MoviesState.Error -> showError(state.errorMessage)
            is MoviesState.Empty -> showEmpty(state.message)
        }
    }
}



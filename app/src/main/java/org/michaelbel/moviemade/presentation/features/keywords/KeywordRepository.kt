package org.michaelbel.moviemade.presentation.features.keywords

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.michaelbel.moviemade.BuildConfig.TMDB_API_KEY
import org.michaelbel.moviemade.core.entity.Movie
import org.michaelbel.moviemade.core.remote.KeywordsService
import org.michaelbel.moviemade.presentation.App
import org.michaelbel.moviemade.presentation.features.settings.AdultUtil
import java.util.*

class KeywordRepository(
        private val service: KeywordsService
): KeywordContract.Repository {

    override fun getMovies(keywordId: Int, page: Int): Observable<List<Movie>> =
        service.getMovies(keywordId, TMDB_API_KEY, Locale.getDefault().language, AdultUtil.includeAdult(App.appContext), page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map { it.movies }
}
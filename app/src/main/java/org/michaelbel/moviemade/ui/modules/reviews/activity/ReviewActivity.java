package org.michaelbel.moviemade.ui.modules.reviews.activity;

import android.os.Bundle;

import com.google.android.material.appbar.AppBarLayout;

import org.michaelbel.moviemade.R;
import org.michaelbel.moviemade.data.dao.Movie;
import org.michaelbel.moviemade.data.dao.Review;
import org.michaelbel.moviemade.ui.base.BaseActivity;
import org.michaelbel.moviemade.ui.modules.reviews.fragment.ReviewFragment;
import org.michaelbel.moviemade.utils.IntentsKt;
import org.michaelbel.moviemade.utils.SpannableUtil;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ReviewActivity extends BaseActivity {

    public Movie movie;
    public Review review;
    private Unbinder unbinder;

    @BindView(R.id.toolbar) public Toolbar toolbar;
    @BindView(R.id.toolbar_title) AppCompatTextView toolbarTitle;
    @BindView(R.id.toolbar_subtitle) AppCompatTextView toolbarSubtitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        unbinder = ButterKnife.bind(this);

        movie = (Movie) getIntent().getSerializableExtra(IntentsKt.MOVIE);
        review = (Review) getIntent().getSerializableExtra(IntentsKt.REVIEW);

        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> finish());

        AppBarLayout.LayoutParams params = (AppBarLayout.LayoutParams) toolbar.getLayoutParams();
        params.setScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL | AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS);

        toolbarTitle.setText(movie.getTitle());
        toolbarSubtitle.setText(SpannableUtil.boldText(getString(R.string.review_by), getString(R.string.review_by, review.getAuthor())));

        ReviewFragment fragment = (ReviewFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);
        if (fragment != null) {
            fragment.setReview(review);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
package com.arnela.rubiconapp.Ui.detail;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.arnela.rubiconapp.Data.DataManager;
import com.arnela.rubiconapp.Data.DataModels.TvMovieVm;
import com.arnela.rubiconapp.Data.Helper.RoutesConstants;
import com.arnela.rubiconapp.R;
import com.arnela.rubiconapp.Util.DialogFactory;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity implements DetailMvpView {

    private DetailPresenter mDetailPresenter;

    @BindView(R.id.img_header)
    ImageView imgHeader;
    @BindView(R.id.txt_title)
    TextView txtTitle;
    @BindView(R.id.txt_description)
    TextView txtDescription;
    @BindView(R.id.toolbarDetail)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        Intent intent = getIntent();

        DataManager dataManager = new DataManager();
        mDetailPresenter = new DetailPresenter(dataManager);

        mDetailPresenter.attachView(this);

        DetailPresenter.ScreenShow details = mDetailPresenter.getTv(intent);

        mDetailPresenter.loadInfoDetail(details.Id, details.IsScreenMovies);

        // add back arrow to toolbar
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        mDetailPresenter.detachView();
    }

    @Override
    public void showInfo(TvMovieVm movie) {

        txtTitle.setText(movie.Title);
        txtDescription.setText(movie.Overview);

        Picasso.get().load(RoutesConstants.BASE_URL_IMG + movie.BackdropPath)
                .placeholder(R.drawable.img_placeholder)
                .resize(500, 500)
                .into(imgHeader);
    }

    @Override
    public void showError() {
        DialogFactory.createGenericErrorDialog(this, getString(R.string.error_loading))
                .show();
    }

    // toolbar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }
}

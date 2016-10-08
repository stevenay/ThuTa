package com.padc.interactive_training.activities;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.padc.interactive_training.InteractiveTrainingApp;
import com.padc.interactive_training.R;
import com.padc.interactive_training.data.persistence.CoursesContract;
import com.padc.interactive_training.data.vos.ArticleVO;
import com.padc.interactive_training.utils.DateTimeUtils;
import com.padc.interactive_training.utils.InteractiveTrainingConstants;
import com.padc.interactive_training.utils.MMFontUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ArticleDetailActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    //region variable declaration
    private final static String IE_ARTICLE_ID = "IE_ARTICLE_ID";

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolBar;

    @BindView(R.id.tv_article_title)
    TextView tvArticleTitle;

    @BindView(R.id.tv_category_name)
    TextView tvCategoryName;

    @BindView(R.id.iv_header_photo)
    ImageView ivHeaderPhoto;

    @BindView(R.id.iv_stock_photo)
    ImageView ivStockPhoto;

    @BindView(R.id.iv_stock_photo_2)
    ImageView ivStockPhoto2;

    @BindView(R.id.tv_author_name)
    TextView tvAuthorName;

    @BindView(R.id.tv_published_date)
    TextView tvPublishedDate;

    @BindView(R.id.tv_introduction_content)
    TextView tvIntroContent;

    @BindView(R.id.tv_first_heading)
    TextView tvFirstHeading;

    @BindView(R.id.tv_second_heading)
    TextView tvSecondHeading;

    @BindView(R.id.tv_first_heading_content)
    TextView tvFirstHeadingContent;

    @BindView(R.id.tv_second_heading_content)
    TextView tvSecondHeadingContent;

    @BindView(R.id.tv_third_heading)
    TextView tvThirdHeading;

    @BindView(R.id.tv_third_heading_content)
    TextView tvThirdHeadingContent;

    private Menu articleMenu;
    private int mArticleId;
    private ArticleVO mArticle;

    //endregion

    public static Intent newIntent(int articleId) {
        Intent newIntent = new Intent(InteractiveTrainingApp.getContext(), ArticleDetailActivity.class);
        newIntent.putExtra(IE_ARTICLE_ID, articleId);
        return newIntent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_detail);
        ButterKnife.bind(this, this);

        //setContentView(R.layout.true_false_question);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        renderArticleTextSize(null);

        mArticleId = getIntent().getIntExtra(IE_ARTICLE_ID, 0);
        getSupportLoaderManager().initLoader(InteractiveTrainingConstants.ARTICLE_DETAIL_LOADER, null, this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_article, menu);
        this.articleMenu = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.action_bookmark:
                if (mArticle != null) {
                    if (mArticle.isBookmarked()) {
                        articleMenu.getItem(0).setIcon(getResources().getDrawable(R.drawable.ic_bookmark_border_24dp));
                        mArticle.setBookmarked(false);
                    } else {
                        articleMenu.getItem(0).setIcon(getResources().getDrawable(R.drawable.ic_bookmark_black_24dp));
                        mArticle.setBookmarked(true);
                    }
                }
                return true;
            case R.id.action_textsize:
                showRadioButtonDialog();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void showRadioButtonDialog() {
        // custom dialog
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_article_textsize);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        this.renderArticleTextSize(dialog);

        RadioGroup radioGroup = (RadioGroup) dialog.findViewById(R.id.rg_textsize);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int radioId) {
                switch (radioId) {
                    case R.id.rdo_extra_small:
                        InteractiveTrainingApp.setArticleTextSize("extra_small");
                        break;
                    case R.id.rdo_small:
                        InteractiveTrainingApp.setArticleTextSize("small");
                        break;
                    case R.id.rdo_medium:
                        InteractiveTrainingApp.setArticleTextSize("medium");
                        break;
                    case R.id.rdo_large:
                        InteractiveTrainingApp.setArticleTextSize("large");
                        break;
                    case R.id.rdo_extra_large:
                        InteractiveTrainingApp.setArticleTextSize("extra_large");
                        break;
                    default:
                        InteractiveTrainingApp.setArticleTextSize("medium");
                        break;
                }

                SharedPreferences pref = getApplicationContext().getSharedPreferences(getString(R.string.ArticlePreference), MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.putString(getString(R.string.ArticleTextSizePara), InteractiveTrainingApp.getArticleTextSize());
                editor.commit();

                renderArticleTextSize(null);
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void renderArticleTextSize(Dialog dialog)
    {
        switch (InteractiveTrainingApp.getArticleTextSize()) {
            case "extra_small":
                this.setArticleContentTextSize(12);
                if (dialog != null) {
                    RadioButton rdoExSmall = (RadioButton) dialog.findViewById(R.id.rdo_extra_small);
                    rdoExSmall.setChecked(true);
                }
                break;
            case "small":
                this.setArticleContentTextSize(14);
                if (dialog != null) {
                    RadioButton rdoSmall = (RadioButton) dialog.findViewById(R.id.rdo_small);
                    rdoSmall.setChecked(true);
                }
                break;
            case "medium":
                this.setArticleContentTextSize(16);
                if (dialog != null) {
                    RadioButton rdoMedium = (RadioButton) dialog.findViewById(R.id.rdo_medium);
                    rdoMedium.setChecked(true);
                }
                break;
            case "large":
                this.setArticleContentTextSize(18);
                if (dialog != null) {
                    RadioButton rdoLarge = (RadioButton) dialog.findViewById(R.id.rdo_large);
                    rdoLarge.setChecked(true);
                }
                break;
            case "extra_large":
                this.setArticleContentTextSize(20);
                if (dialog != null) {
                    RadioButton rdoExtraLarge = (RadioButton) dialog.findViewById(R.id.rdo_extra_large);
                    rdoExtraLarge.setChecked(true);
                }
                break;
            default:
                this.setArticleContentTextSize(16);
                if (dialog != null) {
                    RadioButton rdoDefault = (RadioButton) dialog.findViewById(R.id.rdo_medium);
                    rdoDefault.setChecked(true);
                }
                break;
        }
    }

    private void setArticleContentTextSize(int fontSize)
    {
        tvIntroContent.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize);
        tvFirstHeadingContent.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize);
        tvSecondHeadingContent.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize);
        tvThirdHeadingContent.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize);

        this.setArticleHeadingTextSize(fontSize+4);
    }

    private void setArticleHeadingTextSize(int fontSize)
    {
        tvFirstHeading.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize);
        tvSecondHeading.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize);
        tvThirdHeading.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize);
    }

    //region loader pattern methods
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(this,
                CoursesContract.ArticleEntry.buildArticleUriWithId(mArticleId),
                null,
                null,
                null,
                null
        );
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (data != null && data.moveToFirst()) {
            mArticle = ArticleVO.parseFromCursor(data);

            bindData(mArticle);
        }
    }


    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    //endregion

    private void bindData(ArticleVO mArticle) {

        tvCategoryName.setText("နည္းပညာ");
        tvAuthorName.setText(mArticle.getAuthor());

        String publishedDate = mArticle.getPublishedDateTime();
        Date covertPublishedDate = DateTimeUtils.parseStringToDateTime(publishedDate);
        String formmatedDate = DateTimeUtils.parseDateToString(covertPublishedDate);
        tvPublishedDate.setText(formmatedDate);

        tvIntroContent.setText(mArticle.getIntroContent());

        tvFirstHeading.setText(mArticle.getFirstHeading());
        tvSecondHeading.setText(mArticle.getSecondHeading());
        tvThirdHeading.setText(mArticle.getThirdHeading());

        tvFirstHeadingContent.setText(mArticle.getFirstHeadingContent());
        tvSecondHeadingContent.setText(mArticle.getSecondHeadingContent());
        tvThirdHeadingContent.setText(mArticle.getThirdHeadingContent());

        collapsingToolBar.setTitle("သုတေဆာင္းပါး");
        MMFontUtils.applyMMFontToCollapsingToolbar(collapsingToolBar);

        tvArticleTitle.setText(mArticle.getArticleTitle());
        if (mArticle.getArticleTitle().contains("မူးယစ္စြာေမာင္းႏွင္ျခင္းႏွင့္ မေတာ္တ")) {
            Glide.with(ivHeaderPhoto.getContext())
                    .load(R.drawable.ic_car)
                    .centerCrop()
                    .placeholder(R.drawable.misc_09_256)
                    .error(R.drawable.misc_09_256)
                    .into(ivHeaderPhoto);
        } else {
            Glide.with(ivHeaderPhoto.getContext())
                    .load(R.drawable.ic_building)
                    .centerCrop()
                    .placeholder(R.drawable.misc_09_256)
                    .error(R.drawable.misc_09_256)
                    .into(ivHeaderPhoto);
        }

        Glide.with(ivStockPhoto.getContext())
                .load(mArticle.getImageUrl1())
                .centerCrop()
                .placeholder(R.drawable.misc_09_256)
                .error(R.drawable.misc_09_256)
                .into(ivStockPhoto);

        Glide.with(ivStockPhoto2.getContext())
                .load(mArticle.getImageUrl2())
                .centerCrop()
                .placeholder(R.drawable.misc_09_256)
                .error(R.drawable.misc_09_256)
                .into(ivStockPhoto2);
    }
}
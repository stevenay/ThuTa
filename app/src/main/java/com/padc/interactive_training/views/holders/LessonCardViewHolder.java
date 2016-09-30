package com.padc.interactive_training.views.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.padc.interactive_training.R;
import com.padc.interactive_training.data.vos.LessonCardVO;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by NayLinAung on 9/9/2016.
 */
public class LessonCardViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.tv_page_number_top)
    TextView tvPageNumberTop;

    @BindView(R.id.btn_pin)
    ImageButton btnPin;

//    @BindView(R.id.btn_request_article)
//    ImageButton btnRequestArticle;


    private ControllerLessonCardItem mController;
    private LessonCardVO mLessonCard;

    public LessonCardViewHolder(View itemView, ControllerLessonCardItem controller) {
        super(itemView);
        ButterKnife.bind(this, itemView);

        this.mController = controller;
    }

    public void bindData(LessonCardVO lessonCard) {
        this.mLessonCard = lessonCard;

        tvPageNumberTop.setText(lessonCard.getCardOrderNumber() + " of 10 pages");
        this.setupClickableViews(this.mController);
    }

    private void setupClickableViews(final ControllerLessonCardItem controller) {
        this.btnPin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                controller.onTapPinButton(mLessonCard);
                if (mLessonCard.isBookmarked())
                    ((ImageButton) (view)).setImageResource(R.drawable.ic_pin_green_48);
                else
                    ((ImageButton) (view)).setImageResource(R.drawable.ic_pin_48);
            }
        });

//        this.btnRequestArticle.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                controller.onTapRequestButton();
//            }
//        });
    }

    public interface ControllerLessonCardItem {
        void onTapPinButton(LessonCardVO lessonCard);
        void onTapRequestButton();
    }
}
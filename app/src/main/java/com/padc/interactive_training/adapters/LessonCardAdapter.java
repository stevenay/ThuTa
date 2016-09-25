package com.padc.interactive_training.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;


import com.padc.interactive_training.R;
import com.padc.interactive_training.data.vos.LessonCardVO;
import com.padc.interactive_training.views.holders.LessonCardViewHolder;

import java.util.ArrayList;

/**
 * Created by NayLinAung on 9/8/2016.
 */
public class LessonCardAdapter extends ArrayAdapter<LessonCardVO> {

    private final ArrayList<LessonCardVO> cards;
    private final LayoutInflater layoutInflater;
    private LessonCardViewHolder.ControllerLessonCardItem controllerLessonCardItem;

    public LessonCardAdapter(Context context, ArrayList<LessonCardVO> cards,
                             LessonCardViewHolder.ControllerLessonCardItem controllerLessonCardItem) {
        super(context, -1);
        this.cards = cards;
        this.layoutInflater = LayoutInflater.from(context);

        this.controllerLessonCardItem = controllerLessonCardItem;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LessonCardViewHolder viewHolder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.view_item_my_course, parent, false);
            viewHolder = new LessonCardViewHolder(convertView, controllerLessonCardItem);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (LessonCardViewHolder) convertView.getTag();
        }

        viewHolder.bindData(getItem(position));
        return convertView;
    }

    @Override
    public LessonCardVO getItem(int position) {
        return cards.get(position);
    }

    @Override
    public int getCount() {
        return cards.size();
    }
}

package com.padc.interactive_training.data.vos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.google.gson.annotations.SerializedName;
import com.padc.interactive_training.InteractiveTrainingApp;
import com.padc.interactive_training.data.persistence.CoursesContract;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by NayLinAung on 9/8/2016.
 */
public class LessonCardVO {

    private String chapterId;
    private String courseTitle;

    @SerializedName("card_id")
    private String cardId;

    @SerializedName("card-id")
    private String cardIdWrongName;

    @SerializedName("card_image_url")
    private String lessonImageUrl;

    @SerializedName("card_description")
    private String lessonDescription;

    @SerializedName("card_order_number")
    private Integer cardOrderNumber;

    @SerializedName("bookmarked")
    private boolean bookmarked;

    @SerializedName("finished")
    private boolean finished;

    public String getChapterId() {
        return chapterId;
    }

    public void setChapterId(String chapterID) {
        this.chapterId = chapterID;
    }

    public String getLessonImageUrl() {
        return lessonImageUrl;
    }

    public void setLessonImageUrl(String lessonImageUrl) {
        this.lessonImageUrl = lessonImageUrl;
    }

    public String getLessonDescription() {
        return lessonDescription;
    }

    public void setLessonDescription(String lessonDescription) {
        this.lessonDescription = lessonDescription;
    }

    public int getCardOrderNumber() {
        return cardOrderNumber;
    }

    public void setCardOrderNumber(int cardOrderNumber) {
        this.cardOrderNumber = cardOrderNumber;
    }

    public boolean isBookmarked() {
        return bookmarked;
    }

    public void setBookmarked(boolean bookmarked) {
        this.bookmarked = bookmarked;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public static void saveLessonCards(String chapterId, String courseTitle, List<LessonCardVO> lessonCards) {
        Log.d(InteractiveTrainingApp.TAG, "Method: saveLessonCards. Loaded cards: " + lessonCards.size());

        ContentValues[] cardCVs = new ContentValues[lessonCards.size()];
        for (int index = 0; index < lessonCards.size(); index++) {
            LessonCardVO lessonCard = lessonCards.get(index);
            cardCVs[index] = lessonCard.parseToContentValues(chapterId, courseTitle);

            Log.d(InteractiveTrainingApp.TAG, "Method: saveLessonCards. Card Title: " + lessonCard.getLessonDescription());
        }

        Context context = InteractiveTrainingApp.getContext();
        int insertCount = context.getContentResolver().bulkInsert(CoursesContract.LessonCardEntry.CONTENT_URI, cardCVs);

        Log.d(InteractiveTrainingApp.TAG, "Bulk inserted into lesson_cards table : " + insertCount);
    }

    public ContentValues parseToContentValues(String cardChapterId, String courseTitle) {
        ContentValues cv = new ContentValues();

        cv.put(CoursesContract.LessonCardEntry.COLUMN_CHAPTER_ID, cardChapterId);
        cv.put(CoursesContract.LessonCardEntry.COLUMN_COURSE_TITLE, courseTitle);
        cv.put(CoursesContract.LessonCardEntry.COLUMN_CARD_ID, cardId == null ? cardIdWrongName : cardId);
        cv.put(CoursesContract.LessonCardEntry.COLUMN_CARD_DESCRIPTION, lessonDescription);
        cv.put(CoursesContract.LessonCardEntry.COLUMN_CARD_IMAGE_URL, lessonImageUrl);
        cv.put(CoursesContract.LessonCardEntry.COLUMN_CARD_ORDER_NUMBER, cardOrderNumber);
        cv.put(CoursesContract.LessonCardEntry.COLUMN_FINISHED, finished ? 1 : 0);
        cv.put(CoursesContract.LessonCardEntry.COLUMN_BOOKMARKED, bookmarked ? 1 : 0);

        return cv;
    }

    public static LessonCardVO parseFromCursor(Cursor data) {
        LessonCardVO card = new LessonCardVO();

        card.cardId = data.getString(data.getColumnIndex(CoursesContract.LessonCardEntry.COLUMN_CARD_ID));
        card.chapterId = data.getString(data.getColumnIndex(CoursesContract.LessonCardEntry.COLUMN_CHAPTER_ID));
        card.courseTitle = data.getString(data.getColumnIndex(CoursesContract.LessonCardEntry.COLUMN_COURSE_TITLE));
        card.cardOrderNumber = data.getInt(data.getColumnIndex(CoursesContract.LessonCardEntry.COLUMN_CARD_ORDER_NUMBER));
        card.lessonDescription = data.getString(data.getColumnIndex(CoursesContract.LessonCardEntry.COLUMN_CARD_DESCRIPTION));
        card.lessonImageUrl = data.getString(data.getColumnIndex(CoursesContract.LessonCardEntry.COLUMN_CARD_IMAGE_URL));
        card.finished = data.getInt(data.getColumnIndex(CoursesContract.LessonCardEntry.COLUMN_FINISHED)) == 1 ? true : false;
        card.bookmarked = data.getInt(data.getColumnIndex(CoursesContract.LessonCardEntry.COLUMN_BOOKMARKED)) == 1 ? true : false;

        return card;
    }

    public static List<LessonCardVO> loadLessonCardsByChapterId(String chapterId) {
        Context context = InteractiveTrainingApp.getContext();
        List<LessonCardVO> cards = new ArrayList<>();
        Cursor cursor = context.getContentResolver().query(CoursesContract.LessonCardEntry.buildLessonCardUriWithChapterId(chapterId),
                null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                LessonCardVO lessonCard = new LessonCardVO();
                lessonCard.setCardOrderNumber(cursor.getInt(cursor.getColumnIndex(CoursesContract.LessonCardEntry.COLUMN_CARD_ORDER_NUMBER)));
                lessonCard.setLessonDescription(cursor.getString(cursor.getColumnIndex(CoursesContract.LessonCardEntry.COLUMN_CARD_DESCRIPTION)));
                lessonCard.setLessonImageUrl(cursor.getString(cursor.getColumnIndex(CoursesContract.LessonCardEntry.COLUMN_CARD_IMAGE_URL)));
                lessonCard.setFinished(cursor.getInt(cursor.getColumnIndex(CoursesContract.LessonCardEntry.COLUMN_FINISHED)) == 1 ? true : false);
                lessonCard.setBookmarked(cursor.getInt(cursor.getColumnIndex(CoursesContract.LessonCardEntry.COLUMN_FINISHED)) == 1 ? true : false);

                Log.d(InteractiveTrainingApp.TAG, "loadLessonCardsByChapterId " + lessonCard.getCardOrderNumber());

                cards.add(lessonCard);
            } while (cursor.moveToNext());
        }

        return cards;
    }

    public static List<LessonCardVO> loadLessonCardsByCourseTitle(String courseTitle) {
        Context context = InteractiveTrainingApp.getContext();
        List<LessonCardVO> cards = new ArrayList<>();
        Cursor cursor = context.getContentResolver().query(CoursesContract.LessonCardEntry.buildLessonCardUriWithCourseTitle(courseTitle),
                null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                LessonCardVO lessonCard = new LessonCardVO();
                lessonCard.setCardOrderNumber(cursor.getInt(cursor.getColumnIndex(CoursesContract.LessonCardEntry.COLUMN_CARD_ORDER_NUMBER)));
                lessonCard.setLessonDescription(cursor.getString(cursor.getColumnIndex(CoursesContract.LessonCardEntry.COLUMN_CARD_DESCRIPTION)));
                lessonCard.setLessonImageUrl(cursor.getString(cursor.getColumnIndex(CoursesContract.LessonCardEntry.COLUMN_CARD_IMAGE_URL)));
                lessonCard.setFinished(cursor.getInt(cursor.getColumnIndex(CoursesContract.LessonCardEntry.COLUMN_FINISHED)) == 1 ? true : false);
                lessonCard.setBookmarked(cursor.getInt(cursor.getColumnIndex(CoursesContract.LessonCardEntry.COLUMN_FINISHED)) == 1 ? true : false);

                Log.d(InteractiveTrainingApp.TAG, "loadLessonCardsByChapterId " + lessonCard.getCardOrderNumber());

                cards.add(lessonCard);
            } while (cursor.moveToNext());
        }

        return cards;
    }
}

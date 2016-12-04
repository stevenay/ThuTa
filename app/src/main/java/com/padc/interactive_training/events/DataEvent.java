
package com.padc.interactive_training.events;

import com.padc.interactive_training.data.vos.ArticleVO;
import com.padc.interactive_training.data.vos.CourseVO;

import java.util.List;

/**
 * Created by NayLinAung on 9/22/16.
 */
public class DataEvent {
    public static class CourseDataLoadedEvent {

        private String extraMessage;
        private List<CourseVO> courseList;

        public CourseDataLoadedEvent(String extraMessage, List<CourseVO> attractionList) {
            this.extraMessage = extraMessage;
            this.courseList = attractionList;
        }

        public String getExtraMessage() {
            return extraMessage;
        }

        public List<CourseVO> getAttractionList() {
            return courseList;
        }
    }

    public static class ArticlesDataLoadedEvent {

        private String extraMessage;
        private List<ArticleVO> articleList;

        public ArticlesDataLoadedEvent(String extraMessage, List<ArticleVO> articleList) {
            this.extraMessage = extraMessage;
            this.articleList = articleList;
        }

        public String getExtraMessage() {
            return extraMessage;
        }

        public List<ArticleVO> getArticleList() {
            return articleList;
        }
    }

    public static class ArticlesErrorDataLoadedEvent {

        private String extraMessage;

        public ArticlesErrorDataLoadedEvent(String extraMessage) {
            this.extraMessage = extraMessage;
        }

        public String getExtraMessage() {
            return extraMessage;
        }
    }
}

package com.padc.interactive_training.utils;

/**
 * Created by NayLinAung on 9/11/2016.
 */
public class InteractiveTrainingConstants {
    public static final String IMAGE_ROOT_DIR = "http://www.aungpyaephyo.xyz/padc-fp/interactive-trainings/";

    public static final String COURSE_BASE_URL = "http://www.aungpyaephyo.xyz/padc-fp/interactive-trainings/";
    public static final String API_GET_COURSE_LIST = "GetCourses.php";
    public static final String API_GET_USER_LIST = "GetUsers.php";
    public static final String API_GET_ARTICLE_LIST = "GetArticles.php";
    public static final String API_REGISTER = "register.php";
    public static final String API_REGISTER_WITH_FACEBOOK = "registerWithFacebook.php";
    public static final String API_REGISTER_WITH_GOOGLE = "registerWithGoogle.php";
    public static final String API_LOGIN = "login.php";
    public static final String API_LOGIN_WITH_FACEBOOK = "loginWithFacebook.php";
    public static final String API_LOGIN_WITH_GOOGLE = "loginWithGoogle.php";

    public static final String PARAM_ACCESS_TOKEN = "access_token";

    public static final String PARAM_NAME = "name";
    public static final String PARAM_EMAIL = "email";
    public static final String PARAM_PASSWORD = "password";
    public static final String PARAM_DATE_OF_BIRTH = "date_of_birth";
    public static final String PARAM_COUNTRY_OF_ORIGIN = "country_of_origin";
    public static final String PARAM_FACEBOOK_ID = "facebook_id";
    public static final String PARAM_GOOGLE_ID = "google_id";
    public static final String PARAM_PROFILE_IMAGE = "profile_image";
    public static final String PARAM_COVER_IMAGE = "cover_image";

    public static final String ACCESS_TOKEN = "b002c7e1a528b7cb460933fc2875e916";

    //Loader ID
    public static final int COURSE_LIST_LOADER = 1;
    public static final int COURSE_DETAIL_LOADER = 2;
    public static final int CHAPTER_LIST_LOADER = 3;
    public static final int COURSE_FLOW_LOADER = 4;
    public static final int DISCUSSION_LIST_LOADER = 5;
    public static final int REPLY_LIST_LOADER = 6;
    public static final int TODO_LIST_LOADER = 7;
    public static final int ARTICLE_LIST_LOADER = 8;
    public static final int ARTICLE_DETAIL_LOADER = 9;

    //Regular Expression for checking email.
    public static final String EMAIL_PATTERN = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";

    public static final String ENCRYPT_MD5 = "MD5";

    public static final String URI_TO_OPEN_IN_MAP = "http://maps.google.com/maps?daddr=";

    public static final String CUSTOMER_SUPPORT_PHONE = "0912345678";
    public static final String CUSTOMER_SUPPORT_EMAIL = "customer-support@myanmarattractions.com";

    public static final int DIALOG_BUTTON_LABEL_LIMIT = 15;
}

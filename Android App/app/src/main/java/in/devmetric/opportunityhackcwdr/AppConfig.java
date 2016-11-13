package in.devmetric.opportunityhackcwdr;

/**
 * Created by @silverFoxA on 12/11/16.
 */
public class AppConfig {
    public static final int DEFAULT_RETRY_TIME = 30000;


    public static final String BASE_URL = "http://192.168.113.64:8000/";
    public static final String LOGIN = BASE_URL + "login";
    public static final String SEARCH = BASE_URL + "search?searchText=";
    public static final String CONTENTS = BASE_URL + "search?type=";
    public static final String QUESTION = BASE_URL + "content";
}

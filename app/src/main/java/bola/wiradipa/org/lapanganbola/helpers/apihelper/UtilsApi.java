package bola.wiradipa.org.lapanganbola.helpers.apihelper;

/**
 * Created by emrekabir on 2/08/2018.
 */

public class UtilsApi {

    public static final String BASE_URL = "http://app.lapangbola.com/";
    public static final String BASE_URL_API = "http://app.lapangbola.com/api/";
    public static final String BASE_URL_API_LIVE = "http://live.lapangbola.com/api/";

    public static BaseApiService getApiService(){
        return RetrofitClient.getClient(BASE_URL_API).create(BaseApiService.class);
    }

    public static BaseApiService getApiLiveService(){
        return RetrofitClient.getLiveClient(BASE_URL_API_LIVE).create(BaseApiService.class);
    }

}

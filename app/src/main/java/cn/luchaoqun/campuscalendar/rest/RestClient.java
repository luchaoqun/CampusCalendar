package cn.luchaoqun.campuscalendar.rest;

import cn.luchaoqun.campuscalendar.commen.ACache;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;

/**
 * Created by Administrator on 2015/8/9.
 */
public class RestClient {
    private static RestAdapter restAdapter;
    private static RequestInterceptor requestInterceptor;

    private RestClient(){

    }

    public static RequestInterceptor getInterceptor(){
        if(requestInterceptor == null) {
            requestInterceptor = new RequestInterceptor() {
                @Override
                public void intercept(RequestFacade request) {
                    request.addHeader("X-AVOSCloud-Application-Id", "lg8o06oe5nycpvjaijmf5ov8mcdcidlnkq7cp8bsdbgz8zfe");
                    request.addHeader("X-AVOSCloud-Application-Key", "jjbzaikmgmw607y3kz21dv134vhzflz8lzw9x9kk4jepi6ax");
                    //request.addHeader("Content-Type", "application/json");
                }
            };
        }

        return requestInterceptor;
    }

    public static RestService getInstance(){

        if(restAdapter == null){
            restAdapter = new RestAdapter.Builder()
                    .setEndpoint("https://leancloud.cn/1.1")
                    .setRequestInterceptor(getInterceptor())
                    .build();
        }
        RestService restService = restAdapter.create(RestService.class);
        return restService;
    }
}

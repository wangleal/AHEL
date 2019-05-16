package wang.leal.ahel.http.api;

import wang.leal.ahel.http.api.service.CancelService;
import wang.leal.ahel.http.api.service.CreateService;
import wang.leal.ahel.http.api.service.GetService;
import wang.leal.ahel.http.api.service.PostService;
import wang.leal.ahel.http.api.service.ServiceFactory;
import wang.leal.ahel.http.api.service.okhttp.OkHttpServiceFactory;

/**
 * Created by wang leal on 2017/12/7.
 */

final class ApiHelper {
    private static ServiceFactory serviceFactory = new OkHttpServiceFactory();

    static CreateService createService(){
        return serviceFactory.createService();
    }

    static GetService getService(String url){
        return serviceFactory.getService(url);
    }

    static PostService postService(String url){
        return serviceFactory.postService(url);
    }

    static CancelService cancelService(){
        return serviceFactory.cancelService();
    }
}

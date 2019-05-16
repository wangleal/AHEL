package wang.leal.ahel.http.api.service.retrofit;

import wang.leal.ahel.http.api.service.CancelService;
import wang.leal.ahel.http.api.service.CreateService;
import wang.leal.ahel.http.api.service.GetService;
import wang.leal.ahel.http.api.service.PostService;
import wang.leal.ahel.http.api.service.ServiceFactory;

/**
 * Created by wang leal on 2017/12/7.
 */

public class RetrofitServiceFactory implements ServiceFactory {
    @Override
    public CreateService createService() {
        return new RetrofitCreateService();
    }

    @Override
    public GetService getService(String url) {
        return new RetrofitGetService(url);
    }

    @Override
    public PostService postService(String url) {
        return new RetrofitPostService(url);
    }

    @Override
    public CancelService cancelService() {
        return CancelService.createInstance();
    }
}
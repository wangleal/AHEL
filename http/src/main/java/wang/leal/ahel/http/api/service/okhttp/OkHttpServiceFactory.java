package wang.leal.ahel.http.api.service.okhttp;

import wang.leal.ahel.http.api.service.CancelService;
import wang.leal.ahel.http.api.service.CreateService;
import wang.leal.ahel.http.api.service.GetService;
import wang.leal.ahel.http.api.service.PostService;
import wang.leal.ahel.http.api.service.ServiceFactory;

public class OkHttpServiceFactory implements ServiceFactory {
    @Override
    public CreateService createService() {
        return new OkHttpCreateService();
    }

    @Override
    public GetService getService(String url) {
        return new OkHttpGetService(url);
    }

    @Override
    public PostService postService(String url) {
        return new OkHttpPostService(url);
    }

    @Override
    public CancelService cancelService() {
        return CancelService.createInstance();
    }
}

package wang.leal.ahel.http.api.service;

/**
 * Created by wang leal on 2017/12/7.
 */

public interface ServiceFactory {

    CreateService createService();
    GetService getService(String url);
    PostService postService(String url);
    CancelService cancelService();
}

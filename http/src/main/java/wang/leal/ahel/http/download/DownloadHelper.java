package wang.leal.ahel.http.download;

import wang.leal.ahel.http.download.service.CancelService;
import wang.leal.ahel.http.download.service.DownloadService;
import wang.leal.ahel.http.download.service.RegisterService;
import wang.leal.ahel.http.download.service.ServiceFactory;
import wang.leal.ahel.http.download.service.okhttp.OkHttpServiceFactory;

final class DownloadHelper {
    private static ServiceFactory serviceFactory = new OkHttpServiceFactory();

    static DownloadService downloadService(){
        return serviceFactory.downloadService();
    }

    static RegisterService registerService(){
        return serviceFactory.registerService();
    }

    static CancelService cancelService() {
        return serviceFactory.cancelService();
    }
}

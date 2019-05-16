package wang.leal.ahel.http.download.service.okhttp;

import wang.leal.ahel.http.download.service.CancelService;
import wang.leal.ahel.http.download.service.DownloadService;
import wang.leal.ahel.http.download.service.RegisterService;
import wang.leal.ahel.http.download.service.ServiceFactory;

public class OkHttpServiceFactory implements ServiceFactory {

    @Override
    public DownloadService downloadService() {
        return new OkHttpDownloadService();
    }

    @Override
    public RegisterService registerService() {
        return new OkHttpRegisterService();
    }

    @Override
    public CancelService cancelService() {
        return CancelService.createInstance();
    }
}

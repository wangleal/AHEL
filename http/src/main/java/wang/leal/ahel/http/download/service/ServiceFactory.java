package wang.leal.ahel.http.download.service;

public interface ServiceFactory {

    DownloadService downloadService();
    RegisterService registerService();
    CancelService cancelService();

}

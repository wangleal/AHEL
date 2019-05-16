package wang.leal.ahel.http.download;

import wang.leal.ahel.http.download.service.CancelService;
import wang.leal.ahel.http.download.service.DownloadService;
import wang.leal.ahel.http.download.service.RegisterService;

/**
 * 多线程下载
 * 断点续传
 * 进度回调
 * Created by wang leal on 2017/9/28.
 */

public class Download {

    public static DownloadService with(String url){
        return DownloadHelper.downloadService();
    }

    public static RegisterService register(String url){
        return DownloadHelper.registerService();
    }

    public static CancelService cancel(String url){
        return DownloadHelper.cancelService();
    }

}

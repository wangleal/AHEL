package wang.leal.ahel.http.download.service;

public abstract class DownloadService {

    private String filePath;
    public DownloadService path(String filePath){
        this.filePath = filePath;
        return this;
    }

}

package wang.leal.ahel.http.api.service.entity;

import java.io.File;

public class FileEntity {
    private String name;//服务端获取文件的key
    private String mime;
    private File file;

    public FileEntity(String name,String mime,File file){
        this.name = name;
        this.mime = mime;
        this.file = file;
    }

    public String getName() {
        return name;
    }

    public String getMime() {
        return mime;
    }

    public File getFile() {
        return file;
    }
}

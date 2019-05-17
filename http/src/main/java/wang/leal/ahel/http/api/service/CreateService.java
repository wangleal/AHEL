package wang.leal.ahel.http.api.service;

/**
 * Created by wang leal on 2017/12/7.
 */

public abstract class CreateService {

    protected String baseUrl;//本次请求的baseUrl
    public abstract CreateService baseUrl(String baseUrl);
    public abstract <T> T create(final Class<T> service);
}

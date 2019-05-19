package wang.leal.ahel.sample.http;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface TestService {

    String BASE_URL = "http://test.leal.wang/";

    @GET("a")
    Observable<TestA> getA();
}

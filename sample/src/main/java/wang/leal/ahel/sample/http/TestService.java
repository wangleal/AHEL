package wang.leal.ahel.sample.http;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface TestService {

    @GET("a")
    Observable<TestA> getA();
}

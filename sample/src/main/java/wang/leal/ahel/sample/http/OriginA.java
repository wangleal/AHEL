package wang.leal.ahel.sample.http;

import wang.leal.ahel.http.api.response.Origin;

public class OriginA extends Origin {

    public int code;
    public String message;
    public Data data;

    public class Data{
        public String a;
    }
}

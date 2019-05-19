package wang.leal.ahel.http.api.config;

import okhttp3.OkHttpClient;
import wang.leal.ahel.http.okhttp.OkHttpManager;

public class ApiConfig {
    private OkHttpClient okHttpClient;
    private Result result;
    private Type type;
    private String baseUrl;//全局默认baseUrl
    private ApiConfig(Builder builder){
        this.okHttpClient = builder.okHttpClient;
        this.result = builder.result;
        this.type = builder.type;
        this.baseUrl = builder.baseUrl;
    }

    public OkHttpClient client(){
        return okHttpClient;
    }

    public Result result() {
        return result;
    }

    public String baseUrl(){
        return baseUrl;
    }

    public Type type() {
        return type;
    }

    public final static class Builder{
        private OkHttpClient okHttpClient;
        private Result result;
        private Type type;
        private String baseUrl;

        public Builder client(OkHttpClient okHttpClient){
            this.okHttpClient = okHttpClient;
            return this;
        }

        /**
         * 设置Response返回值的最外层字段，设置result之后，会自动取data字段的值封装成Observable的泛型，也就是onSuccess的参数。
         * 如果不设置此字段，那么会将整个Response封装成Observable的泛型，也就是onSuccess的参数。
         * @param result    Response最外层的字段
         * @return  Builder
         */
        public Builder result(Result result){
            this.result = result;
            return this;
        }

        public Builder type(Type type){
            this.type = type;
            return this;
        }

        public Builder baseUrl(String baseUrl){
            this.baseUrl = baseUrl;
            return this;
        }

        public ApiConfig build(){
            if (this.result==null){
                this.result = new Result("code","message","data",0);
            }
            if (this.type==null){
                this.type = Type.RETROFIT;
            }
            if (this.okHttpClient==null){
                this.okHttpClient = OkHttpManager.getApiOkHttpClient();
            }
            return new ApiConfig(this);
        }
    }

    public enum Type{
        OKHTTP,RETROFIT
    }
}

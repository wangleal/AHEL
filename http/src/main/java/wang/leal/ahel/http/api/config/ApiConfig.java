package wang.leal.ahel.http.api.config;

import okhttp3.OkHttpClient;

public class ApiConfig {
    private OkHttpClient okHttpClient;
    private Result result;
    private Type type;
    private ApiConfig(Builder builder){
        this.okHttpClient = builder.okHttpClient;
        this.result = builder.result;
        this.type = builder.type;
    }

    public OkHttpClient client(){
        return okHttpClient;
    }

    public Result result() {
        return result;
    }

    public Type type() {
        return type;
    }

    public final static class Builder{
        private OkHttpClient okHttpClient;
        private Result result;
        private Type type;

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

        public ApiConfig build(){
            return new ApiConfig(this);
        }
    }

    public enum Type{
        OKHTTP,RETROFIT
    }
}

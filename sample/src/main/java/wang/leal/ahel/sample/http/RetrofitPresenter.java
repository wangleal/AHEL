package wang.leal.ahel.sample.http;

import java.io.PrintWriter;
import java.io.StringWriter;

import wang.leal.ahel.http.api.Api;
import wang.leal.ahel.http.api.observable.transform.IOToMainTransform;
import wang.leal.ahel.http.api.observer.ApiObserver;

class RetrofitPresenter implements HttpPresenter{
    private HttpInfoView infoView;
    RetrofitPresenter(HttpInfoView infoView){
        this.infoView = infoView;
    }

    @Override
    public void create() {
        Api.create(TestService.class)
                .getA()
                .compose(new IOToMainTransform<>())
                .subscribe(new ApiObserver<TestA>() {
                    @Override
                    protected void onSuccess(TestA data) {
                        infoView.showInfo("success:"+data.a);
                    }

                    @Override
                    protected void onApiError(int errNo, String errMsg, String data) {
                        infoView.showInfo("api error:\r\n"+"errno:"+errNo+",message:"+errMsg+",data:"+data);
                    }

                    @Override
                    protected void onFailure(Throwable e) {
                        StringWriter stringWriter = new StringWriter();
                        PrintWriter printWriter = new PrintWriter(stringWriter);
                        e.printStackTrace(printWriter);
                        infoView.showInfo("failure:"+stringWriter.toString());
                    }

                    @Override
                    protected void onFinal() {
                        infoView.showInfo("\r\nfinal");
                    }
                });
    }

    @Override
    public void get() {
        Api.get("http://test.leal.wang/a")
                .header("a","a")
                .observable(TestA.class)
                .compose(new IOToMainTransform<>())
                .subscribe(new ApiObserver<TestA>() {
                    @Override
                    protected void onSuccess(TestA data) {
                        infoView.showInfo("success:"+data.a);
                    }

                    @Override
                    protected void onApiError(int errNo, String errMsg, String data) {
                        infoView.showInfo("api error:\r\n"+"errno:"+errNo+",message:"+errMsg+",data:"+data);
                    }

                    @Override
                    protected void onFailure(Throwable e) {
                        StringWriter stringWriter = new StringWriter();
                        PrintWriter printWriter = new PrintWriter(stringWriter);
                        e.printStackTrace(printWriter);
                        infoView.showInfo("failure:"+stringWriter.toString());
                    }

                    @Override
                    protected void onFinal() {
                        infoView.showInfo("\r\nfinal");
                    }
                });
    }

    @Override
    public void post() {
        Api.post("http://test.leal.wang/a")
                .header("a","a")
                .observable(TestA.class)
                .compose(new IOToMainTransform<>())
                .subscribe(new ApiObserver<TestA>() {
                    @Override
                    protected void onSuccess(TestA data) {
                        infoView.showInfo("success:"+data.a);
                    }

                    @Override
                    protected void onApiError(int errNo, String errMsg, String data) {
                        infoView.showInfo("api error:\r\n"+"errno:"+errNo+",message:"+errMsg+",data:"+data);
                    }

                    @Override
                    protected void onFailure(Throwable e) {
                        StringWriter stringWriter = new StringWriter();
                        PrintWriter printWriter = new PrintWriter(stringWriter);
                        e.printStackTrace(printWriter);
                        infoView.showInfo("failure:"+stringWriter.toString());
                    }

                    @Override
                    protected void onFinal() {
                        infoView.showInfo("\r\nfinal");
                    }
                });
    }

    @Override
    public void testOrigin() {
        Api.post("http://test.leal.wang/a")
                .header("a","a")
                .observable(OriginA.class)
                .compose(new IOToMainTransform<>())
                .subscribe(new ApiObserver<OriginA>() {
                    @Override
                    protected void onSuccess(OriginA data) {
                        infoView.showInfo("success:"+data.data.a);
                    }

                    @Override
                    protected void onApiError(int errNo, String errMsg, String data) {
                        infoView.showInfo("api error:\r\n"+"errno:"+errNo+",message:"+errMsg+",data:"+data);
                    }

                    @Override
                    protected void onFailure(Throwable e) {
                        StringWriter stringWriter = new StringWriter();
                        PrintWriter printWriter = new PrintWriter(stringWriter);
                        e.printStackTrace(printWriter);
                        infoView.showInfo("failure:"+stringWriter.toString());
                    }

                    @Override
                    protected void onFinal() {
                        infoView.showInfo("\r\nfinal");
                    }
                });
    }
}

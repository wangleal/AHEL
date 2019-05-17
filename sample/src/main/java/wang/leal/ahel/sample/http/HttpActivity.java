package wang.leal.ahel.sample.http;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import wang.leal.ahel.R;

public class HttpActivity extends AppCompatActivity implements HttpInfoView{

    private TextView tvInfo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_http);
        tvInfo = findViewById(R.id.tv_info);
        tvInfo.setMovementMethod(ScrollingMovementMethod.getInstance());
//        HttpPresenter httpPresenter = new RetrofitPresenter(this);
        HttpPresenter httpPresenter = new OkhttpPresenter(this);
        findViewById(R.id.bt_request).setOnClickListener(v -> {
            infoBuilder.setLength(0);
//            httpPresenter.create();
//            httpPresenter.get();
            httpPresenter.post();
        });
    }

    private StringBuilder infoBuilder = new StringBuilder();
    @Override
    public void showInfo(String info) {
        infoBuilder.append(info);
        if (tvInfo!=null){
            tvInfo.setText(infoBuilder.toString());
        }
    }
}

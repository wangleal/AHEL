package wang.leal.ahel.sample.http;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import wang.leal.ahel.R;

public class HttpActivity extends AppCompatActivity{

    private TextView tvInfo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_http);
        tvInfo = findViewById(R.id.tv_info);
        tvInfo.setMovementMethod(ScrollingMovementMethod.getInstance());
        findViewById(R.id.bt_request).setOnClickListener(v -> {

        });
    }

    public void showInfo(String info) {
        if (tvInfo!=null){
            tvInfo.setText(info);
        }
    }
}

package wang.leal.ahel.sample.socket;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import wang.leal.ahel.R;
import wang.leal.ahel.socket.SocketStatusListener;

public class SocketActivity extends AppCompatActivity{
    private StringBuilder stringBuilder = new StringBuilder();
    private TextView tvContent;
    private Disposable disposable;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_socket);
//        Socket socket = Socket.connect("62.234.130.115",10301)
//        Socket socket = Socket.connectOrGet("172.30.20.205",8080)
        Connection.connect("62.234.130.115", 10301, new SocketStatusListener() {
            @Override
            public void onConnected() {
                Connection.sendAuth("62.234.130.115",10301);
            }
        });
        disposable = Connection.registerMessage("62.234.130.115",10301)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(message -> {
                    stringBuilder.append(message);
                    stringBuilder.append("\r\n");
                    tvContent.setText(stringBuilder.toString());
                });
        EditText etInput = findViewById(R.id.et_input);
        findViewById(R.id.bt_send).setOnClickListener(v -> {
            String message = etInput.getText().toString();
            Connection.sendMessage("62.234.130.115",10301,message);
        });
        tvContent = findViewById(R.id.tv_content);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposable.dispose();
        Connection.disconnect("62.234.130.115",10301);
    }
}

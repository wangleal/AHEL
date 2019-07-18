package wang.leal.ahel.sample.socket;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import wang.leal.ahel.R;
import wang.leal.ahel.socket.Socket;

public class SocketActivity extends AppCompatActivity implements Socket.Callback {
    private StringBuilder stringBuilder = new StringBuilder();
    private TextView tvContent;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_socket);
//        Socket socket = Socket.connect("62.234.130.115",10301)
        Socket socket = Socket.connect("172.30.20.205",8080)
                .callback(this);
        EditText etInput = findViewById(R.id.et_input);
        findViewById(R.id.bt_send).setOnClickListener(v -> {
            String message = etInput.getText().toString();
            socket.send(message);
        });
        tvContent = findViewById(R.id.tv_content);
    }

    @Override
    public void onMessageReceive(String message) {
        stringBuilder.append(message);
        stringBuilder.append("\r\n");
        tvContent.setText(stringBuilder.toString());
    }
}

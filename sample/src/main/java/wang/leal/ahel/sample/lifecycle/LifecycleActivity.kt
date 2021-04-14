package wang.leal.ahel.sample.lifecycle

import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import wang.leal.ahel.R
import wang.leal.ahel.extension.processName
import wang.leal.ahel.extension.toDecimal
import wang.leal.ahel.lifecycle.ActivityLifecycle

class LifecycleActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lifecycle)
        findViewById<Button>(R.id.bt_test).setOnClickListener {
//            ActivityLifecycle.observable()
//                    .subscribeOn(Schedulers.computation())
//                    .subscribe {
//                        Log.e("Sample","activity:${it.activity},event:${it.event.name}")
//                    }
//            Log.e("Sample","currentActivity:${ActivityLifecycle.currentActivity()}")
//            Log.e("Sample","process:${processName()}")
            Log.e("Sample","3.14159->${3.14159f.toDecimal(2)}")
            Log.e("Sample","3.142->${3.142f.toDecimal(2)}")
            Log.e("Sample","3.10->${3.10f.toDecimal(2)}")
            Log.e("Sample","3.1->${3.1f.toDecimal(2)}")
        }
    }

}
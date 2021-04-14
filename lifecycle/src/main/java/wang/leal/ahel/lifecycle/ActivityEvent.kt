package wang.leal.ahel.lifecycle

import android.app.Activity
import androidx.lifecycle.Lifecycle

data class ActivityEvent(val activity:Activity, val event:Lifecycle.Event)
package wang.leal.ahel.storage

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.reflect.TypeToken
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import wang.leal.ahel.storage.json.GsonManager
import wang.leal.ahel.storage.room.RoomKV
import wang.leal.ahel.storage.room.StorageDatabase
import wang.leal.ahel.storage.room.RoomScheduler
import java.lang.reflect.Field

class RoomService(private val db: StorageDatabase?) {

    companion object {
        private val creators: HashMap<String, Parcelable.Creator<*>> = HashMap()
    }

    fun encode(key: String, value: String) {
        Observable.create<Unit> {
            val keyValue = RoomKV(key, value)
            db?.storageDao()?.insert(keyValue)
        }.subscribeOn(RoomScheduler.scheduler()).subscribe()
    }

    fun decodeString(key: String): Observable<String> {
        return decodeString(key, "")
    }

    fun decodeString(key: String, defaultValue: String): Observable<String> {
        return Observable.create<String> { emitter ->
            val value = db?.storageDao()?.findByKey(key)
            value?.value?.let {
                emitter.onNext(it)
            } ?: emitter.onNext(defaultValue)
            emitter.onComplete()
        }.subscribeOn(RoomScheduler.scheduler()).observeOn(AndroidSchedulers.mainThread())
    }

    fun encode(key: String, value: Int) {
        Observable.create<Unit> {
            val keyValue = RoomKV(key, value.toString())
            db?.storageDao()?.insert(keyValue)
        }.subscribeOn(RoomScheduler.scheduler()).subscribe()
    }

    fun decodeInt(key: String): Observable<Int> {
        return decodeInt(key, 0)
    }

    fun decodeInt(key: String, defaultValue: Int): Observable<Int> {
        return Observable.create<Int> { emitter ->
            val value = db?.storageDao()?.findByKey(key)
            value?.value?.let {
                emitter.onNext(it.toInt())
            } ?: emitter.onNext(defaultValue)
            emitter.onComplete()
        }.subscribeOn(RoomScheduler.scheduler()).observeOn(AndroidSchedulers.mainThread())
    }

    fun encode(key: String, value: Float) {
        Observable.create<Unit> {
            val keyValue = RoomKV(key, value.toString())
            db?.storageDao()?.insert(keyValue)
        }.subscribeOn(RoomScheduler.scheduler()).subscribe()
    }

    fun decodeFloat(key: String): Observable<Float> {
        return decodeFloat(key, 0f)
    }

    fun decodeFloat(key: String, defaultValue: Float): Observable<Float> {
        return Observable.create<Float> { emitter ->
            val value = db?.storageDao()?.findByKey(key)
            value?.value?.let {
                emitter.onNext(it.toFloat())
            } ?: emitter.onNext(defaultValue)
            emitter.onComplete()
        }.subscribeOn(RoomScheduler.scheduler()).observeOn(AndroidSchedulers.mainThread())
    }

    fun encode(key: String, value: Double) {
        Observable.create<Unit> {
            val keyValue = RoomKV(key, value.toString())
            db?.storageDao()?.insert(keyValue)
        }.subscribeOn(RoomScheduler.scheduler()).subscribe()
    }

    fun decodeDouble(key: String): Observable<Double> {
        return decodeDouble(key, 0.0)
    }

    fun decodeDouble(key: String, defaultValue: Double): Observable<Double> {
        return Observable.create<Double> { emitter ->
            val value = db?.storageDao()?.findByKey(key)
            value?.value?.let {
                emitter.onNext(it.toDouble())
            } ?: emitter.onNext(defaultValue)
            emitter.onComplete()
        }.subscribeOn(RoomScheduler.scheduler()).observeOn(AndroidSchedulers.mainThread())
    }

    fun encode(key: String, value: ByteArray) {
        Observable.create<Unit> {
            val keyValue = RoomKV(key, String(value))
            db?.storageDao()?.insert(keyValue)
        }.subscribeOn(RoomScheduler.scheduler()).subscribe()
    }

    fun decodeByteArray(key: String): Observable<ByteArray> {
        return decodeByteArray(key, ByteArray(0))
    }

    fun decodeByteArray(key: String, defaultValue: ByteArray): Observable<ByteArray> {
        return Observable.create<ByteArray> { emitter ->
            val value = db?.storageDao()?.findByKey(key)
            value?.value?.let {
                emitter.onNext(it.toByteArray())
            } ?: emitter.onNext(defaultValue)
            emitter.onComplete()
        }.subscribeOn(RoomScheduler.scheduler()).observeOn(AndroidSchedulers.mainThread())
    }

    fun encode(key: String, value: Long) {
        Observable.create<Unit> {
            val keyValue = RoomKV(key, value.toString())
            db?.storageDao()?.insert(keyValue)
        }.subscribeOn(RoomScheduler.scheduler()).subscribe()
    }

    fun decodeLong(key: String): Observable<Long> {
        return decodeLong(key, 0L)
    }

    fun decodeLong(key: String, defaultValue: Long): Observable<Long> {
        return Observable.create<Long> { emitter ->
            val value = db?.storageDao()?.findByKey(key)
            value?.value?.let {
                emitter.onNext(it.toLong())
            } ?: emitter.onNext(defaultValue)
            emitter.onComplete()
        }.subscribeOn(RoomScheduler.scheduler()).observeOn(AndroidSchedulers.mainThread())
    }

    fun encode(key: String, value: Boolean) {
        Observable.create<Unit> {
            val keyValue = RoomKV(key, value.toString())
            db?.storageDao()?.insert(keyValue)
        }.subscribeOn(RoomScheduler.scheduler()).subscribe()
    }

    fun decodeBoolean(key: String): Observable<Boolean> {
        return decodeBoolean(key, false)
    }

    fun decodeBoolean(key: String, defaultValue: Boolean): Observable<Boolean> {
        return Observable.create<Boolean> { emitter ->
            val value = db?.storageDao()?.findByKey(key)
            value?.value?.let {
                emitter.onNext(it.toBoolean())
            } ?: emitter.onNext(defaultValue)
            emitter.onComplete()
        }.subscribeOn(RoomScheduler.scheduler()).observeOn(AndroidSchedulers.mainThread())
    }

    fun encode(key: String, value: Parcelable) {
        Observable.create<Unit> {
            val source = Parcel.obtain()
            value.writeToParcel(source, value.describeContents())
            val bytes = source.marshall()
            source.recycle()
            val keyValue = RoomKV(key, String(bytes))
            db?.storageDao()?.insert(keyValue)
        }.subscribeOn(RoomScheduler.scheduler()).subscribe()
    }

    @Suppress("UNCHECKED_CAST")
    fun <T : Parcelable> decodeParcelable(key: String, clazz: Class<T>, defaultValue: T): Observable<T> {
        return Observable.create<T> { emitter ->
            val value = db?.storageDao()?.findByKey(key)
            value?.value?.let {
                val bytes = it.toByteArray()
                val source = Parcel.obtain()
                source.unmarshall(bytes, 0, bytes.size)
                source.setDataPosition(0)

                try {
                    val name: String = clazz.toString()
                    var creator: Parcelable.Creator<*>?
                    synchronized(creators) {
                        creator = creators[name]
                        if (creator == null) {
                            val f: Field = clazz.getField("CREATOR")
                            creator = f.get(null) as Parcelable.Creator<*>
                            creator?.let { creator ->
                                creators[name] = creator
                            }
                        }
                    }
                    creator?.let { param ->
                        emitter.onNext(param.createFromParcel(source) as T)
                    }
                            ?: emitter.onError(Exception("Parcelable protocol requires a non-null static Parcelable.Creator object called CREATOR on class $name"))
                } catch (e: Exception) {
                    e.printStackTrace()
                } finally {
                    source.recycle()
                }
            } ?: emitter.onNext(defaultValue)
            emitter.onComplete()
        }.subscribeOn(RoomScheduler.scheduler()).observeOn(AndroidSchedulers.mainThread())
    }

    fun encode(key: String, value: Set<String>) {
        Observable.create<Unit> {
            val keyValue = RoomKV(key, GsonManager.gson().toJson(value))
            db?.storageDao()?.insert(keyValue)
        }.subscribeOn(RoomScheduler.scheduler()).subscribe()
    }

    fun decodeSetString(key: String): Observable<Set<String>> {
        return decodeSetString(key, HashSet())
    }

    fun decodeSetString(key: String, defaultValue: Set<String>): Observable<Set<String>> {
        return Observable.create<Set<String>> { emitter ->
            val value = db?.storageDao()?.findByKey(key)
            value?.value?.let {
                val type = object : TypeToken<Set<String>>() {}.type
                emitter.onNext(GsonManager.gson().fromJson(it, type))
            } ?: emitter.onNext(defaultValue)
            emitter.onComplete()
        }.subscribeOn(RoomScheduler.scheduler()).observeOn(AndroidSchedulers.mainThread())
    }

    fun delete(key: String){
        Observable.create<Unit> {
            db?.storageDao()?.deleteByKey(key)
        }.subscribeOn(RoomScheduler.scheduler()).subscribe()
    }
}
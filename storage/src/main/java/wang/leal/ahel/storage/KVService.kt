package wang.leal.ahel.storage

import android.os.Parcelable
import com.tencent.mmkv.MMKV
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers

class KVService(name: String) {
    private val mmkv:MMKV = MMKV.mmkvWithID(name)
    fun encode(key: String, value: String): KVService {
        Observable.create<Unit> {
            mmkv.encode(key, value)
        }.subscribeOn(Schedulers.io()).subscribe()
        return this
    }

    fun decodeString(key: String):Observable<String>{
        return decodeString(key,"")
    }

    fun decodeString(key: String, defaultValue: String):Observable<String>{
        return Observable.create<String> {
            it.onNext(mmkv.decodeString(key,defaultValue))
            it.onComplete()
        }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    }

    fun encode(key: String, value: Int): KVService {
        Observable.create<Unit> {
            mmkv.encode(key, value)
        }.subscribeOn(Schedulers.io()).subscribe()
        return this
    }

    fun decodeInt(key: String):Observable<Int>{
        return decodeInt(key,0)
    }

    fun decodeInt(key: String, defaultValue: Int):Observable<Int>{
        return Observable.create<Int> {
            it.onNext(mmkv.decodeInt(key,defaultValue))
            it.onComplete()
        }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    }

    fun encode(key: String, value: Float): KVService {
        Observable.create<Unit> {
            mmkv.encode(key, value)
        }.subscribeOn(Schedulers.io()).subscribe()
        return this
    }

    fun decodeFloat(key: String):Observable<Float>{
        return decodeFloat(key,0.0F)
    }

    fun decodeFloat(key: String, defaultValue: Float):Observable<Float>{
        return Observable.create<Float> {
            it.onNext(mmkv.decodeFloat(key,defaultValue))
            it.onComplete()
        }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    }

    fun encode(key: String, value: Double): KVService {
        Observable.create<Unit> {
            mmkv.encode(key, value)
        }.subscribeOn(Schedulers.io()).subscribe()
        return this
    }

    fun decodeDouble(key: String):Observable<Double>{
        return decodeDouble(key,0.0)
    }

    fun decodeDouble(key: String, defaultValue: Double):Observable<Double>{
        return Observable.create<Double> {
            it.onNext(mmkv.decodeDouble(key,defaultValue))
            it.onComplete()
        }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    }

    fun encode(key: String, value: ByteArray): KVService {
        Observable.create<Unit> {
            mmkv.encode(key, value)
        }.subscribeOn(Schedulers.io()).subscribe()
        return this
    }

    fun decodeByteArray(key: String):Observable<ByteArray>{
        return decodeByteArray(key, ByteArray(0))
    }

    fun decodeByteArray(key: String, defaultValue: ByteArray):Observable<ByteArray>{
        return Observable.create<ByteArray> {
            it.onNext(mmkv.decodeBytes(key,defaultValue))
            it.onComplete()
        }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    }

    fun encode(key: String, value: Long): KVService {
        Observable.create<Unit> {
            mmkv.encode(key, value)
        }.subscribeOn(Schedulers.io()).subscribe()
        return this
    }

    fun decodeLong(key: String):Observable<Long>{
        return decodeLong(key, 0L)
    }

    fun decodeLong(key: String, defaultValue: Long):Observable<Long>{
        return Observable.create<Long> {
            it.onNext(mmkv.decodeLong(key,defaultValue))
            it.onComplete()
        }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    }

    fun encode(key: String, value: Boolean): KVService {
        Observable.create<Unit> {
            mmkv.encode(key, value)
        }.subscribeOn(Schedulers.io()).subscribe()
        return this
    }

    fun decodeBoolean(key: String):Observable<Boolean>{
        return decodeBoolean(key, false)
    }

    fun decodeBoolean(key: String, defaultValue: Boolean):Observable<Boolean>{
        return Observable.create<Boolean> {
            it.onNext(mmkv.decodeBool(key,defaultValue))
            it.onComplete()
        }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    }

    fun encode(key: String, value: Parcelable): KVService {
        Observable.create<Unit> {
            mmkv.encode(key, value)
        }.subscribeOn(Schedulers.io()).subscribe()
        return this
    }

    fun <T:Parcelable> decodeParcelable(key: String, clazz:Class<T>,defaultValue: T):Observable<T>{
        return Observable.create<T> {
            it.onNext(mmkv.decodeParcelable(key,clazz,defaultValue))
            it.onComplete()
        }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    }

    fun encode(key: String, value: Set<String>): KVService {
        Observable.create<Unit> {
            mmkv.encode(key, value)
        }.subscribeOn(Schedulers.io()).subscribe()
        return this
    }

    fun decodeStringSet(key: String):Observable<Set<String>>{
        return decodeStringSet(key, HashSet())
    }

    fun decodeStringSet(key: String, defaultValue: Set<String>):Observable<Set<String>>{
        return Observable.create<Set<String>> {
            it.onNext(mmkv.decodeStringSet(key,defaultValue))
            it.onComplete()
        }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    }

    fun delete(key:String):KVService{
        Observable.create<Unit> {
            mmkv.remove(key)
        }.subscribeOn(Schedulers.io()).subscribe()
        return this
    }

}
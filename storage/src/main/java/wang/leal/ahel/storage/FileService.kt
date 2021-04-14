package wang.leal.ahel.storage

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.io.File
import java.io.FileReader
import java.io.FileWriter
import java.util.concurrent.locks.Lock
import java.util.concurrent.locks.ReadWriteLock
import java.util.concurrent.locks.ReentrantReadWriteLock

class FileService(private val fileName: String, private val dir: File?) {
    private val readWriteLock: ReadWriteLock = ReentrantReadWriteLock()
    private val readLock: Lock = readWriteLock.readLock()
    private val writeLock: Lock = readWriteLock.writeLock()
    fun write(value: String){
        Observable.create<Unit> {
            writeLock.lock()
            try {
                val file = getFile()
                val fileWriter = FileWriter(file)
                fileWriter.write(value)
                fileWriter.flush()
                fileWriter.close()
            }finally {
                writeLock.unlock()
            }
        }.subscribeOn(Schedulers.io()).subscribe()
    }

    fun read():Observable<String>{
        return Observable.create<String> {
            readLock.lock()
            var value = ""
            try {
                val file = getFile()
                val fileReader = FileReader(file)
                value = fileReader.readText()
                fileReader.close()
            }finally {
                readLock.unlock()
                it.onNext(value)
                it.onComplete()
            }
        }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    }

    private fun getFile():File{
        val file = File(dir, fileName)
        if (!file.exists()){
            file.createNewFile()
        }
        return file
    }

    fun delete(){
        Observable.create<Unit> {
            writeLock.lock()
            try {
                val file = getFile()
                file.deleteOnExit()
            }finally {
                writeLock.unlock()
            }
        }.subscribeOn(Schedulers.io()).subscribe()
    }
}
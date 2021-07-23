package wang.leal.ahel.http.okhttp

import android.os.Build
import io.reactivex.rxjava3.core.Observable
import okhttp3.Dns
import org.xbill.DNS.*
import java.net.InetAddress
import java.net.UnknownHostException
import java.util.concurrent.TimeUnit

class DNSManager:Dns {
    override fun lookup(hostname: String): MutableList<InetAddress> {
        return try {
            Dns.SYSTEM.lookup(hostname).toMutableList()
        }catch (e:Exception){
            getInetAddress(hostname)
        }
//        return try {
//            getInetAddress(hostname)
//        }catch (e:Exception){
//            e.printStackTrace()
//            LogUtils.d("HttpApi","exception:${e.message}")
//            Dns.SYSTEM.lookup(hostname)
//        }
    }

    private fun getInetAddress(hostname: String):MutableList<InetAddress> {
        val addresses =  mutableListOf<InetAddress>()
        Observable.create<String> {
            try {
                if (Build.VERSION.SDK_INT<26){
                    it.onError(UnknownHostException("Broken system behaviour for OS<26 of $hostname"))
                }else{
                    val resolver: Resolver = SimpleResolver("8.8.8.8")
                    val lookup = Lookup(hostname, Type.A)
                    lookup.setResolver(resolver)
                    val records: Array<Record> = lookup.run()
                    if (lookup.result == Lookup.SUCCESSFUL) {
                        for (record in records) {
                            it.onNext(record.rdataToString())
                            println(record.rdataToString())
                        }
                        it.onComplete()
                    } else {
                        it.onError(UnknownHostException("Broken system behaviour for google(8.8.8.8) dns lookup of $hostname"))
                    }
                }
            }catch (e:Exception){
                it.onError(UnknownHostException("Broken system behaviour for google(8.8.8.8) dns lookup of $hostname"))
            }
        }.timeout(5, TimeUnit.SECONDS)
            .blockingIterable()
            .forEach {
                addresses.add(InetAddress.getByName(it))
            }
        if (addresses.size==0){
            throw UnknownHostException("Broken system behaviour for google(8.8.8.8) dns lookup of $hostname")
        }
        return addresses
    }
}
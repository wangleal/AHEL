package wang.leal.ahel.http.okhttp

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
                it.onError(UnknownHostException())
            }
        }.timeout(2,TimeUnit.SECONDS)
            .blockingIterable()
            .forEach {
                addresses.add(InetAddress.getByName(it))
            }
        return addresses
    }
}
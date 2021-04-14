package wang.leal.ahel.bus

import io.reactivex.rxjava3.subjects.BehaviorSubject

internal object SubjectFactory {

    private val publishSubjects = mutableMapOf<String, BehaviorSubject<String>>()

    @Synchronized fun publishSubject(server:String):BehaviorSubject<String>{
        return publishSubjects[server]?: create().also {
            publishSubjects[server] = it
        }
    }

    private fun create():BehaviorSubject<String> = BehaviorSubject.create()

}
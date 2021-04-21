package wang.leal.ahel.bus

import io.reactivex.rxjava3.subjects.BehaviorSubject
import io.reactivex.rxjava3.subjects.PublishSubject

internal object SubjectFactory {

    private val behaviorSubjects = mutableMapOf<String, BehaviorSubject<String>>()
    private val publishSubjects = mutableMapOf<String, PublishSubject<String>>()

    @Synchronized fun publishSubject(server:String): PublishSubject<String> {
        return publishSubjects[server]?: createPublish().also {
            publishSubjects[server] = it
        }
    }

    @Synchronized fun behaviorSubject(server:String):BehaviorSubject<String>{
        return behaviorSubjects[server]?: createBehavior().also {
            behaviorSubjects[server] = it
        }
    }

    private fun createBehavior():BehaviorSubject<String> = BehaviorSubject.create()

    private fun createPublish(): PublishSubject<String> = PublishSubject.create()

}
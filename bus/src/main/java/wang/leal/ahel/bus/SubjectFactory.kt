package wang.leal.ahel.bus

import io.reactivex.rxjava3.subjects.BehaviorSubject
import io.reactivex.rxjava3.subjects.PublishSubject

internal object SubjectFactory {

    private val behaviorSubjects = mutableMapOf<String, BehaviorSubject<Event>>()
    private val publishSubjects = mutableMapOf<String, PublishSubject<Event>>()

    @Synchronized fun publishSubject(server:String):PublishSubject<Event>{
        return publishSubjects[server]?: createPublish().also {
            publishSubjects[server] = it
        }
    }

    @Synchronized fun behaviorSubject(server:String):BehaviorSubject<Event>{
        return behaviorSubjects[server]?: createBehavior().also {
            behaviorSubjects[server] = it
        }
    }

    private fun createBehavior():BehaviorSubject<Event> = BehaviorSubject.create()

    private fun createPublish():PublishSubject<Event> = PublishSubject.create()

}
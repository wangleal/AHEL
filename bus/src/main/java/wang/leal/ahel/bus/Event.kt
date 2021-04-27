package wang.leal.ahel.bus

data class Event<T> (val action:String,val data:T)
package wang.leal.ahel.bus

interface Call {

    fun execute(params:String?,callback: Callback)
}
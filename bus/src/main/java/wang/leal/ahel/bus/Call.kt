package wang.leal.ahel.bus

interface Call {
    fun execute(request:Request,callback:Callback)
}
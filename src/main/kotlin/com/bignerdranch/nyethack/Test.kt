import java.lang.reflect.InvocationHandler
import java.lang.reflect.Proxy

interface Repository{
    fun fetchData(): String
    fun save(data: String)
}

class RealRepository : Repository {
    override fun fetchData() = "Data from db"
    override fun save(data: String) { println("Saved $data") }

}

class LoggingProxyRepository(private val target: Repository) : Repository by target{
    override fun fetchData(): String {
        println("[LOG]: Request for data reading")
        return target.fetchData()
    }
}

fun main(){
    val rr = RealRepository()
    val loggingProxyRepository = LoggingProxyRepository(rr)
    println(loggingProxyRepository.fetchData())

    val api = createDynamicProxy()
    println(api.getUserName(42))

}

interface ApiService {
    fun getUserName(id: Int): String
}

class MyApi: ApiService{
    override fun getUserName(id: Int): String {
        return " что-то"
    }

    fun doSmt(){

    }
}

fun createDynamicProxy(): ApiService{
    // обработчик
    val handler = InvocationHandler { proxy, method, args ->

        println("Captured method: ${method.name}, args: ${args?.joinToString()}")

        when (method.name){
            "getUserName" -> "User ${args?.get(0)}"
            else -> throw UnsupportedOperationException("Method is not supported")
        }
    }

    return Proxy.newProxyInstance(
        ApiService::class.java.classLoader, // место жительства
        arrayOf(ApiService::class.java), // чем он является
        handler  // что он делает
    ) as ApiService
}











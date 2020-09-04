import kotlinx.coroutines.*


fun main(){
    println("in coroutines: ")
    println("Started main coroutine")
// run two background value computations
    runBlocking {
        val v1 = async(CoroutineName("v1coroutine")) {
            delay(500)
            println("Computing v1")
            252
        }
        val v2 = async(CoroutineName("v2coroutine")) {
            delay(1000)
            println("Computing v2")
            6
        }
        println("The answer for v1 / v2 = ${v1.await() / v2.await()}")
    }

}
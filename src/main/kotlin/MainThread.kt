import java.util.*

fun main(){
    var currentDate = Date()
    val time1: Long = currentDate.time
    val thread1 = SimpleThread("A")
    thread1.start()
    val thread2 = Thread(SimpleRunnable("B"))
    thread2.start()
    currentDate = Date()
    var time2 = currentDate.time
    while (time2 - time1 < 2000) { //цикл, пока разница не составит 2 секунды
        currentDate = Date()
        time2 = currentDate.time
    }
    thread1.interrupt() //прерываем потоки
    thread2.interrupt()

}
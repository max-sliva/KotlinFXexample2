fun main(){
    val thread1 = SimpleThread("A")
    thread1.start()
    val thread2 = Thread(SimpleRunnable("B"))
    thread2.start()
}
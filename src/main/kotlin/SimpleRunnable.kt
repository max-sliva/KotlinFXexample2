class SimpleRunnable(private val s: String) : Runnable {
    @Volatile
    private var stopped = false //про volatile – после кода

    override fun run() {
        while (!stopped) {
            println(s)
            try {
                Thread.sleep(500)
            } catch (e: InterruptedException) {
                println("$s exit") //печатаем сообщение и выходим из потока
                return
            }
        }
        Thread.currentThread().interrupt() //прерываем поток
    }
    fun stopThread() { //метод для остановки потока
        stopped = true
    }
}
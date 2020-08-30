class SimpleThread (  val s:String) : Thread() {
    @Volatile
    private var stopped = false //про volatile – после кода
//    private val s: String = "" //для хранения выводимого сообщения от потока
    override fun run() {  //основная функция потока
        while (!stopped) { //цикл пока stopped не станет равным true
            println(s)
            try { //нужно для обработки вероятных ошибок при задержке потока
                sleep(500)  //задерживаем работу потока на 0,5 секунды
            } catch (e: InterruptedException) { //если возникла ошибка -
                println("$s exit") //печатаем сообщение			   				return; // и выходим из потока
            }
        }
        interrupt() //прерываем поток
    }

    fun stopThread() { //метод для остановки потока
        stopped = true
    }
}
import javafx.application.Platform
import javafx.scene.control.TextArea
import kotlinx.coroutines.awaitAll

class SimpleThread(val s: String) : Thread() {
    @Volatile //модификатор доступа, нужен для гарантировано разных значений в разных потоках
    private var stopped = false //признак остановки потока
    private var textArea: TextArea? = null
    private var suspendFlag = false

    fun setTextArea(tArea: TextArea) {
        textArea = tArea
    }

    override fun run() {  //основная функция потока
        while (!stopped) { //цикл пока stopped не станет равным true
            println(s) //вывод сообщения в потоке
            Platform.runLater(Runnable {
                textArea!!.appendText("$s\n")
            })
            try { //нужно для обработки вероятных ошибок при задержке потока
                sleep(500)  //задерживаем работу потока на 0,5 секунды
            } catch (e: InterruptedException) { //если возникла ошибка -
                println("$s exit") //печатаем сообщение
                return // и выходим из потока
            }
            synchronized(this) { //создаем блок с синхронизацией - монитор
                while (suspendFlag) { //пока suspendFlag равен true
                    try { //секция try … catch для безопасной работы с потоком
                        (this as java.lang.Object).wait() //приостанавливаем поток
                    } catch (ex: InterruptedException) { //в случае срабатывания исключения
                        println("$s exit") //печатаем сообщение и выходим из потока
                    }
                }
            }
        }
        interrupt() //прерываем поток
    }

    fun stopThread() { //метод для остановки потока
        stopped = true
    }

    @Synchronized
    fun suspendThread() { //синхронизированный метод для приостановки потока
//для остановки потока достаточно взвести флаг - в коде потока он проверится и вызовется метод wait(), //данный метод будем вызывать из главного класса
        suspendFlag = true
        Platform.runLater { textArea!!.appendText("Пауза потока $s\n") }
    }

    @Synchronized
    fun resumeThread() { //синхронизированный метод для возобновления потока
//данный метод будем вызывать из главного класса
        suspendFlag = false //сбрасываем флаг
        Platform.runLater { textArea!!.appendText("Возобновление потока $s\n") }
        (this as java.lang.Object).notify() //запускаем поток, который был остановлен (то есть сам себя)
    }

}
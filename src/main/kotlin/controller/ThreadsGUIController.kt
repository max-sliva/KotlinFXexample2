package controller

import SimpleRunnable
import SimpleThread
import javafx.event.ActionEvent
import javafx.fxml.FXML

class ThreadsGUIController {
    var threadA = SimpleThread("A") //первый объект – наследник Thread
    var threadB = SimpleRunnable("B") //второй объект – реализует Runnable
    var tempThread = Thread(threadB) //специальный поток для второго объекта

    @FXML //обработчик нажатия на кнопку запуска первого потока
    private fun handleOnStartA(e: ActionEvent) {
//если поток только создан (т.е. еще не запускался), то запускаем его
        if (threadA.state == Thread.State.NEW) threadA.start()
    }

    @FXML //обработчик нажатия на кнопку остановки первого потока
    private fun handleOnStopA(e: ActionEvent) {
        threadA.stopThread() //вызываем метод нашего класса, приводящий к остановке потока
        threadA = SimpleThread("A") //и создаем объект заново
    }

    @FXML //обработчик нажатия на кнопку запуска второго потока
    private fun handleOnStartB(e: ActionEvent) {
//если поток только создан (т.е. еще не запускался), то запускаем его
        if (tempThread.state == Thread.State.NEW) tempThread.start()
    }

    @FXML //обработчик нажатия на кнопку остановки второго потока
    private fun handleOnStopB(e: ActionEvent) {
        threadB.stopThread() //вызываем метод нашего класса, приводящий к остановке потока
        threadB = SimpleRunnable("B") //и создаем объект заново
        tempThread = Thread(threadB) //вместе с дополнительным потоком
    }
}

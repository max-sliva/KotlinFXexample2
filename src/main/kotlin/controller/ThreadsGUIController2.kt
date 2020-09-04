package controller

import SimpleThread
import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.scene.control.Button
import javafx.scene.control.TextArea


class ThreadsGUIController2 {
    @FXML lateinit var textArea: TextArea//наша текстовая область из интерфйеса

    var threadA = SimpleThread("A") //потоковые объекты
    var threadB = SimpleThread("B")

    @FXML //обработчик нажатия на кнопку запуска первого потока
    private fun handleOnStartA(e: ActionEvent) {
        textArea.appendText("StartA\n") //сообщение о запуске
        threadA.setTextArea(textArea) //связываем поток с текстовой областью
        if (threadA.state == Thread.State.NEW) threadA.start() //стартуем поток
    }

    @FXML //обработчик нажатия на кнопку остановки первого потока
    private fun handleOnStopA(e: ActionEvent) {
        threadA.stopThread() //вызываем метод остановки
        threadA = SimpleThread("A") //и создаем объект заново
    }

    //повторяем все для второго потока
    @FXML
    private fun handleOnStartB(e: ActionEvent) {
        textArea.appendText("StartB\n")
        threadB.setTextArea(textArea)
        if (threadB.state == Thread.State.NEW) threadB.start()
    }

    @FXML
    private fun handleOnStopB(e: ActionEvent) {
        threadB.stopThread()
        threadB = SimpleThread("B")
    }

    @FXML //обрабатываем нажатие на кнопку PauseA, для PauseB аналогичный код
    private fun handleOnPauseA(e: ActionEvent) {
        val button = e.source as Button //получаем ссылку на нажатую кнопку
        when (button.getText()) {
            "PauseA" -> {
                threadA.suspendThread() //то ставим поток на паузу
                button.setText("ResumeA") //и делаем надпись ResumeA
            }
            "ResumeA" -> {
                threadA.resumeThread() //то возобновляем поток
                button.setText("PauseA") //и делаем надпись PauseA
            }
        }
    }

}
package controller

import javafx.application.Platform
import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.scene.control.Button
import javafx.scene.control.ProgressIndicator
import javafx.scene.control.TextArea
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox
import javafx.stage.Stage
import java.io.BufferedReader
import java.io.DataOutputStream
import java.io.InputStreamReader
import java.net.ServerSocket
import java.net.Socket
import java.net.SocketException
import java.nio.charset.StandardCharsets


class SocketsController2 {
    @FXML lateinit var progressIndicator : ProgressIndicator
    @FXML lateinit var vBox : VBox
    @FXML lateinit var hBox : HBox
    @FXML lateinit var textTo : TextArea
    @FXML lateinit var textFrom : TextArea
    @FXML lateinit var startServer: Button
    @FXML lateinit var startClient: Button
    @FXML lateinit var sendButton: Button
    lateinit var serverSock : ServerSocket
    lateinit var clientSocket : Socket
    lateinit var inputSock : Socket
    lateinit var outToClient : DataOutputStream

    @FXML  //обработчик нажатия на серверную кнопку
    private fun handleStartServer(event: ActionEvent) {
        println("Starting server... ")
        (vBox.scene.window as Stage).title = "Server" //делаем заголовок окна
        disableButtons() //деактивируем кнопки (метод описан ниже)
        //вставляем индикатор прогресса в компоновку
        vBox.children.add(1, progressIndicator)
        serverSock = ServerSocket(2424) //создаем серверный сокет
        Thread{ //отдельный поток для ожидания подключения клиента
            inputSock = serverSock.accept() //ждем подключения клиента
            println("Connected!") //эта строка будет выведена, когда клиент подключится
            Platform.runLater { //для обращения к элементам интерфейса из другого потока
                vBox.children.remove(progressIndicator)  //убираем индикатор прогресса
                activateGUI() //активируем неактивные элементы интерфейса
            }  //(метод описан ниже)
            listenSocket() //запускаем метод для приема сообщений из сокета (описан ниже)
//получаем выходной сокет для отправки данных клиенту
            outToClient = DataOutputStream(inputSock.getOutputStream())
        }.start() //сразу стартуем поток
    }

    private fun listenSocket() {
//метод для запуска потока для приема сообщений из сокета
        Thread{ //отдельный поток для приема сообщений из сокета
            println("Listening Socket...")
            val inFromSocket = BufferedReader(InputStreamReader(inputSock.getInputStream(),
                    StandardCharsets.UTF_8))
            while (true) { //бесконечный цикл для приема сообщений из сокета
//создаем буфер для чтения данных из потока ввода от сокета (и указываем кодировку)
                var clientSentence: String? = null
                try {
                    clientSentence = inFromSocket.readLine() //считываем сообщение из потока сокета
                } catch (e: SocketException) {
                    break
                }
                if (clientSentence != null) { //если сообщение было
                    println("From Socket: $clientSentence")
                    Platform.runLater {
//в текстовое окно выводим текст, trim отсекает пустые начальные и конечные символы,
                        textFrom.appendText(clientSentence.trim().toString() + "\n") //если появятся
                    }
                    //если в сообщении содержится слово exit, то выходим из цикла
                    if (clientSentence.contains("exit")) break
                }
            }
            inputSock.close() //по завершении цикла закрываем сокет
        }.start() //сразу стартуем поток
        vBox.scene.window.setOnCloseRequest {
            println("Closing")
            try {
                outToClient.writeUTF("exit") //пишем в поток вывода
                outToClient.flush() //и проталкиваем поток вывода, чтоб данные точно ушли
            } catch (e: SocketException){
                Platform.exit()
            }
            inputSock.close()
        }
    }

    private fun disableButtons() { //метод для деактивации кнопок
        startServer.isDisable = true
        startClient.isDisable = true
    }

    private fun activateGUI() { //метод для активации интерфейса
        textFrom.isDisable = false
        textTo.isDisable = false
        sendButton.isDisable = false
    }

    @FXML  //обработчик нажатия на клиентскую кнопку
    private fun handleStartClient(event: ActionEvent) {
        println("Starting client... ")
        (vBox.scene.window as Stage).title = "Client" //делаем заголовок окна
        disableButtons() //деактивируем кнопки
//создаем сокет для подключения к серверу по указанному адресу и порту
        clientSocket = Socket("localhost", 2424)
        println("Connected!")
//получаем поток для отправки данных серверу
        outToClient = DataOutputStream(clientSocket.getOutputStream())
        activateGUI() //активируем неактивные элементы интерфейса
        inputSock = clientSocket
        listenSocket()
    }
    @FXML  //обработчик отправки сообщений
    private fun handleSendButton(event: ActionEvent) {
        val sentence = textTo.text //получаем текст c текстовой области
        println("To Socket: $sentence") //отладочный вывод в консоль
        outToClient.writeUTF(sentence + '\n') //пишем в поток вывода
        outToClient.flush() //и проталкиваем поток вывода, чтоб данные точно ушли
    }

    fun initialize() {
        println("Sockets!")
        vBox.children.remove(progressIndicator) //убираем индикатор прогресса
    }
}

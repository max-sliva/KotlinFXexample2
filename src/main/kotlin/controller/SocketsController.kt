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
import java.security.PrivilegedActionException


class SocketsController {
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
    @Volatile
    private var inputThread: Thread = Thread()

    @FXML
    private fun handleStartServer(event: ActionEvent) { //обработчик нажатия на серверную кнопку
        (vBox.scene.window as Stage).title = "Server"
        println("Starting server... ")
        disableButtons() //деактивируем кнопки (метод описан ниже)
        vBox.children.add(1, progressIndicator) //вставляем индикатор прогресса в компоновку
        serverSock = ServerSocket(2424) //создаем серверный сокет
        Thread{ //отдельный поток для ожидания подключения клиента
            inputSock = serverSock.accept() //ждем подключения клиента
            println("Connected!") //эта строка будет выведена, когда клиент подключится
            Platform.runLater { //для обращения к элементам интерфейса из другого потока
                vBox.children.remove(progressIndicator) //убираем индикатор прогресса
                activateGUI() //активируем неактивные элементы интерфейса (метод описан ниже)
            }
            listenSocket() //запускаем метод для приема сообщений из сокета (описан ниже)
            outToClient = DataOutputStream(inputSock.getOutputStream()) //получаем выходной сокет для отправки данных клиенту
        }.start() //сразу стартуем поток
//        println("After ")
    }

    private fun listenSocket() { //метод для запуска потока для приема сообщений из сокета
        //TODO разобраться с остановкой потока после закрытия окна
//        Thread{ //отдельный поток для приема сообщений из сокета
//            println("Listening Socket...")
//            while (true) { //бесконечный цикл для приема сообщений из сокета
//        //создаем буфер для чтения данных из потока ввода от сокета (и указываем кодировку)
//                val inFromSocket = BufferedReader(InputStreamReader(inputSock.getInputStream(), StandardCharsets.UTF_8))
//                val clientSentence = inFromSocket.readLine() //считываем сообщение из потока сокета
//                if (clientSentence != null) { //если сообщение было
//                    println("From Socket: $clientSentence")
//                    Platform.runLater { //в текстовое окно выводим текст, trim отсекает пустые начальные и конечные символы,
//                        textFrom.appendText(clientSentence.trim().toString() + "\n") //если появятся
//                    }
//                    //если в сообщении содержится слово exit, то выходим из цикла
//                    if (clientSentence.contains("exit")) break
//                }
//            }
//            inputSock.close() //по завершении цикла закрываем сокет
//        }.start() //сразу стартуем поток
        val sRun = SimpleRunnable2(inputSock, textFrom)
        inputThread = Thread(sRun)
        inputThread.start()
        vBox.scene.window.setOnCloseRequest{
            println("Closing")
            if (inputThread.isAlive) {
                println("inputThread is running")
                try {
                    outToClient.writeUTF("exit") //пишем в поток вывода
                    outToClient.flush() //и проталкиваем поток вывода, чтоб данные точно ушли
                } catch (e: SocketException){
                    sRun.stopThread()
                    inputThread.interrupt()
                    Platform.exit()
                }
                sRun.stopThread()
                inputThread.interrupt()
            }
//            Platform.exit()
//            while (inputThread.isAlive){}
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

    @FXML
    private fun handleStartClient(event: ActionEvent) { //обработчик нажатия на клиентскую кнопку
        println("Starting client... ")
        (vBox.scene.window as Stage).title = "Client"
        disableButtons() //деактивируем кнопки
        clientSocket = Socket("localhost", 2424) //создаем сокет для подключения к серверу по указанному адресу и порту
        println("Connected!")
        outToClient = DataOutputStream(clientSocket.getOutputStream()) //получаем поток для отправки данных серверу
        activateGUI() //активируем неактивные элементы интерфейса
        inputSock = clientSocket
        listenSocket()
    }

    @FXML
    private fun handleSendButton(event: ActionEvent) { //обработчик отправки сообщений
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

class SimpleRunnable2(private val inputSock: Socket, private val textFrom: TextArea) : Runnable {
    @Volatile
    private var stopped = false

    override fun run() {
        val inFromSocket = BufferedReader(InputStreamReader(inputSock.getInputStream(), StandardCharsets.UTF_8))
        var clientSentence: String
        while (!stopped) {
            //создаем буфер для чтения данных из потока ввода от сокета (и указываем кодировку)
           try {
               clientSentence = inFromSocket.readLine() //считываем сообщение из потока сокета

           } catch (e: SocketException){
               return
           }

            if (clientSentence != null) { //если сообщение было
                println("From Socket: $clientSentence")
//                Platform.runLater { //в текстовое окно выводим текст, trim отсекает пустые начальные и конечные символы,
                    textFrom.appendText(clientSentence.trim().toString() + "\n") //если появятся
//                }
                //если в сообщении содержится слово exit, то выходим из цикла
                if (clientSentence.contains("exit")) return
            }
            inFromSocket.ready()
            try {
                Thread.sleep(500)
                println("Thread is running...")
            } catch (e: InterruptedException) {
                println("thread exit") //печатаем сообщение и выходим из потока
                return
            }

        }
//        println("StopThread")
        Thread.currentThread().interrupt()

    }

    fun stopThread() {
        stopped = true
        println("StopThread")
        inputSock.close()
        Thread.currentThread().interrupt()
    }
}

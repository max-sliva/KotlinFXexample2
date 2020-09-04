import javafx.application.Application
import javafx.event.ActionEvent
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.layout.StackPane
import javafx.stage.Stage


class Main1 : Application() {

    override fun start(primaryStage: Stage?) {
        val btn = Button() //создаем кнопку
        btn.setText("Say 'Hello World'") //задаем ей текст
        btn.setOnAction {  //обработчик нажатия
            println("Hello World!")
        }
        val root = StackPane() //другая компоновка
        root.children.add(btn) //добавляем в нее кнопку
        val scene = Scene(root, 300.0, 250.0) //создаем сцену
        primaryStage!!.title = "Hello World" //создаем окно с заголовком
        primaryStage!!.scene = scene //вставляем в окно сцену
        primaryStage!!.show() //показываем окно
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            launch(Main1::class.java)
        }
    }
}
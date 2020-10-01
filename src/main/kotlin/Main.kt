import javafx.application.Application
import javafx.fxml.FXMLLoader.load
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.scene.canvas.Canvas
import javafx.scene.canvas.GraphicsContext
import javafx.scene.layout.BorderPane
import javafx.scene.paint.Color
import javafx.stage.Stage

class Main : Application() {

    var root: BorderPane? = null
    var scene: Scene? = null
    var gc: GraphicsContext? = null
    var primaryStage: Stage? = null
    override fun start(primaryStage: Stage?) {
        root = BorderPane() //создаем BorderPane
        scene = Scene(root, 200.0, 200.0) //создаем сцену с нашим BorderPane
        primaryStage!!.scene = scene //вставляем сцену в окно
        primaryStage!!.minWidth = 200.0 //устанавливаем минимальную ширину
        primaryStage!!.minHeight = 200.0 // и высоту окна
        primaryStage!!.show() //делаем окно видимым
        var myCanvas = Canvas() //создаем канвас
        myCanvas.width = primaryStage!!.width //устанавливаем его размеры по размерам окна
        myCanvas.height = primaryStage!!.height
        gc = myCanvas.graphicsContext2D //получаем графический контекст канваса
        gc!!.fill = Color.BLACK //устанавливаем черный цвет заливки
//рисуем залитый прямоугольник в центре канваса
        gc!!.fillOval(primaryStage!!.width / 2 - 20, primaryStage!!.height / 2 - 20, 40.0, 40.0)
        root!!.center = myCanvas //вставляем канвас в центр окна
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            launch(Main::class.java)
        }
    }
}
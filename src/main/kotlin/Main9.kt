import javafx.application.Application
import javafx.fxml.FXMLLoader.load
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.stage.Stage

class Main9 : Application() {

    val layout = "Sockets.fxml"

    override fun start(primaryStage: Stage?) {
        primaryStage?.scene = Scene(load<Parent?>(Main8.javaClass.getResource(layout)))
        primaryStage?.show()
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            launch(Main9::class.java)
        }
    }
}
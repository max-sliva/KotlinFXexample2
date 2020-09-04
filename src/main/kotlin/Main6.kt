import javafx.application.Application
import javafx.fxml.FXMLLoader.load
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.stage.Stage

class Main6 : Application() {

    val layout = "ThreadsGUI.fxml"

    override fun start(primaryStage: Stage?) {
        primaryStage?.scene = Scene(load<Parent?>(Main6.javaClass.getResource(layout)))
        primaryStage?.show()
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            launch(Main6::class.java)
        }
    }
}
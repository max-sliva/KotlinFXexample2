import javafx.application.Application
import javafx.fxml.FXMLLoader.load
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.stage.Stage

class MainMongo : Application() {

    val layout = "MongoExample1.fxml"

    override fun start(primaryStage: Stage?) {
        primaryStage?.scene = Scene(load<Parent?>(Main.javaClass.getResource(layout)))
        primaryStage?.show()
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            launch(MainMongo::class.java)
        }
    }
}
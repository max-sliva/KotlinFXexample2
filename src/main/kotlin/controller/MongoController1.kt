package controller

import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.scene.layout.VBox
import javafx.stage.Modality
import javafx.stage.Stage


class MongoController1{
    @FXML lateinit var mainPane: VBox

    @FXML
    private fun handleAccountOnPress(event: ActionEvent) {
        println("In this window")
//загружаем данные из файла в объект, это наша таблица
        val root = FXMLLoader.load<Parent>(Main.javaClass.getResource("table.fxml"))
        mainPane.children.add(root) //загружаем таблицу в окно

        println("Second window")
//загружаем данные из файла в объект, это наша таблица
//        val root = FXMLLoader.load<Parent>(Main.javaClass.getResource("table.fxml"))
//        val stage = Stage() //создаем новое окно
//        stage.scene = Scene(root) //загружаем в него таблицу
//        stage.initModality(Modality.WINDOW_MODAL) //делаем окно модальным
//        stage.initOwner(mainPane.scene.window) //и его владельцем делаем главное окно
//        stage.show()//показываем новое окно
    }
}
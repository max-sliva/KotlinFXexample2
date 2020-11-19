package controller

import Account
import Main
import addUser
import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.scene.control.*
import javafx.scene.control.ButtonBar.ButtonData
import javafx.scene.layout.BorderPane
import javafx.scene.layout.GridPane
import javafx.scene.layout.VBox
import javafx.stage.Modality
import javafx.stage.Stage
import md5


class MainControllerAdmin {

    @FXML lateinit var button1: Button
    @FXML lateinit var mainPane: VBox

    @FXML //обязательное указание
    private fun handleAccountOnPress(event: ActionEvent) { // import javafx.event.ActionEvent;
        println("Hello")
        val root = FXMLLoader.load<Parent>(Main.javaClass.getResource("table.fxml"))
//        val stage = Stage()
//        stage.scene = Scene(root)
//        stage.initModality(Modality.WINDOW_MODAL)
//        stage.initOwner(mainPane.scene.window)
//        stage.show()
        mainPane.children.add(root)
    }

    @FXML
    private fun handleAddUser(event: ActionEvent) {
        val dialog: Dialog<Account> = Dialog()
        dialog.title = "Добавление пользователя"
        dialog.headerText = "Введите данные пользователя:"
        dialog.isResizable = true

        val grid = GridPane()
        dialog.dialogPane.content = grid

        val labels = arrayOf(Label("Логин:"), Label("ФИО:"), Label("Статус: "), Label("Пароль:"))
        val textFields = Array<TextField>(4){TextField()}

        for (i in labels.indices){
            grid.add(labels[i], 1, i)
            grid.add(textFields[i], 2, i)
        }

        val buttonTypeOk = ButtonType("Ok", ButtonData.OK_DONE)
        val buttonTypeCancel = ButtonType("Cancel", ButtonData.CANCEL_CLOSE)
        dialog.dialogPane.buttonTypes.addAll(buttonTypeOk, buttonTypeCancel)

        dialog.setResultConverter { b ->
            if (b == buttonTypeOk) {
                Account(textFields[0].text, textFields[1].text, textFields[2].text, textFields[3].text.md5())
            } else null
        }

        val result = dialog.showAndWait()

        if (result.isPresent) {
            println("Result: " + result.get())
            addUser(result.get())
            mainPane.children.removeAt(mainPane.children.size-1)
            handleAccountOnPress(ActionEvent())
//            accountTable.items = getUsers()
        }
    }

    fun initialize() {
        println("Controller working")
//        button1.setOnAction {
//            println("Button clicked!")
//        }
    }
}
package controller

import Account
import addUser
import getUsers
import javafx.application.Platform
import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.scene.control.*
import javafx.scene.control.ButtonBar.ButtonData
import javafx.scene.control.cell.PropertyValueFactory
import javafx.scene.layout.GridPane
import md5
import java.util.concurrent.atomic.AtomicLong


class MainControllerLogin {

    @FXML lateinit var button1: Button
    @FXML lateinit var accountTable: TableView<Account>
    @FXML lateinit var loginCol: TableColumn<Account, String>
    @FXML lateinit var fioCol: TableColumn<Account, String>
    @FXML lateinit var statusCol: TableColumn<Account, String>
    @FXML lateinit var passCol: TableColumn<Account, String>

    @FXML //обязательное указание
    private fun handleButtonOnPress(event: ActionEvent) { // import javafx.event.ActionEvent;
        println("Hello")
        loginCol.cellValueFactory = PropertyValueFactory("login")
        fioCol.cellValueFactory = PropertyValueFactory("fio")
        statusCol.cellValueFactory = PropertyValueFactory("status")
        passCol.cellValueFactory = PropertyValueFactory("pass")
        accountTable.items = getUsers() //загружаем все из data в таблицу на окне
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
            accountTable.items = getUsers()
        }
    }

    fun initialize() {
        println("Controller working")
//        button1.setOnAction {
//            println("Button clicked!")
//        }
    }
}
package controller

import Account
import getUsers
import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.scene.control.*
import javafx.scene.control.cell.PropertyValueFactory
import javafx.scene.input.MouseEvent


class AccountController {
    @FXML lateinit var accountTable: TableView<Account>
    @FXML lateinit var loginCol: TableColumn<Account, String>
    @FXML lateinit var fioCol: TableColumn<Account, String>
    @FXML lateinit var statusCol: TableColumn<Account, String>
    @FXML lateinit var passCol: TableColumn<Account, String>

    fun initialize() {
        println("Account table!")
        loginCol.cellValueFactory = PropertyValueFactory("login")
        fioCol.cellValueFactory = PropertyValueFactory("fio")
        statusCol.cellValueFactory = PropertyValueFactory("status")
        passCol.cellValueFactory = PropertyValueFactory("pass")
        accountTable.items = getUsers() //загружаем все из data в таблицу на окне
    }
}
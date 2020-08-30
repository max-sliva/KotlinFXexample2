import javafx.animation.KeyFrame
import javafx.animation.KeyValue
import javafx.animation.Timeline
import javafx.application.Application
import javafx.beans.InvalidationListener
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.control.CheckBox
import javafx.scene.effect.Lighting
import javafx.scene.layout.BorderPane
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox
import javafx.scene.paint.Color
import javafx.scene.shape.Circle
import javafx.scene.text.Text
import javafx.stage.Stage
import javafx.util.Duration


class Main3 : Application() {
    var timeline: Timeline? = null

    private fun init(primaryStage: Stage) {
        val root = BorderPane()
        primaryStage.isResizable = true
        primaryStage.scene = Scene(root, 400.0, 300.0)
        val circle = Circle(250.0, 250.0, 20.0, Color.web("1c89f4")) //создаем круг
        circle.effect = Lighting() //добавляем эффект
        timeline = Timeline() // создаем временную шкалу для анимации
        //задаем кол-во повторов анимации, Timeline.INDEFINITE для бесконечной анимации
        timeline!!.cycleCount = 2
        timeline!!.isAutoReverse = true //устанавливаем возможность обратной анимации
        //для работы с анимацией используют следующие методы созданного объекта:
        //timeline.play() - запуск анимации
        //timeline.pause() - пауза анимации
        //timeline.stop() - остановка анимации
        //timeline.playFromStart() - проигрывание с самого начала

        // добавляем ключевые кадры анимации
        // каждый кадр должен характеризоваться временем и ключевым значением
        // ключевое значение – тот параметр объекта, который будет меняться в процессе анимации
        // в нашем случае это параметр круга, отвечающий за перемещение положение по оси ОХ,
        // т.е. circle.translateXProperty()
        timeline!!.keyFrames.addAll(
            KeyFrame(
                Duration.ZERO,  //первый ключевой кадр длится ноль секунд
                //и содержит ключевой параметр с начальным значением
                KeyValue(circle.translateXProperty(), 0)
            ),
            KeyFrame(
                Duration(1000.0),  //второй ключевой кадр длится секунду
                // и переносит круг на 205 точек вправо
                KeyValue(circle.translateXProperty(), 205)
            )
        )
        root.center = circle //помещаем круг в центр окна
        root.bottom = createNavigation() //помещаем в низ окна панель управления анимацией
    }

    override fun stop() { //сюда записываем действия при остановке программы
        timeline!!.stop() //останавливаем анимацию
    }

    private fun createNavigation(): VBox? { //метод для создания панели управления анимацией
        val buttonStart = Button("Start")
        buttonStart.setOnAction{
                timeline!!.play()
        }

        val buttonStop = Button("Stop")
        buttonStop.setOnAction{
                timeline!!.stop()
        }

        val buttonPlayFromStart = Button("Play From Start")
        buttonPlayFromStart.setOnAction{
                timeline!!.playFromStart()
        }

        val buttonPause = Button("Pause")
        buttonPause.setOnAction{
                timeline!!.pause()
            }
        //текст для показа текущего времени анимации
        val currentRateText = Text("Current time: 0 ms")
        //добавляем слушатель к свойству времени анимации
        timeline!!.currentTimeProperty().addListener(InvalidationListener {
            val time = timeline!!.currentTime.toMillis().toInt() //текущее время анимации
            currentRateText.setText("Current time: $time ms")
        })
        //чекбокс для параметра обратной анимации
        val checkBoxAutoReverse = CheckBox("Auto Reverse")
        checkBoxAutoReverse.isSelected = true
        checkBoxAutoReverse.selectedProperty().addListener(InvalidationListener {
            timeline!!.isAutoReverse = checkBoxAutoReverse.isSelected
        })
        //добавляем кнопки на HBox
        var hBox1 = HBox(10.0)
        hBox1.padding = Insets(0.0, 0.0, 0.0, 5.0)
        hBox1.children.addAll(buttonStart, buttonPause, buttonStop, buttonPlayFromStart)
        hBox1.alignment = Pos.CENTER
        val hBox2 = HBox(10.0) //для времени анимации и чекбокса
        hBox2.padding = Insets(0.0, 0.0, 0.0, 35.0)
        hBox2.children.addAll(checkBoxAutoReverse, currentRateText)
        hBox2.alignment = Pos.CENTER
        val vBox = VBox(10.0)
        vBox.children.addAll(hBox1, hBox2) //добавляем все в VBox
        return vBox
    }

    override fun start(primaryStage: Stage?) {
        init(primaryStage!!)
        primaryStage.show()
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            launch(Main3::class.java)
        }
    }
}
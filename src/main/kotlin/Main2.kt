import javafx.application.Application
import javafx.embed.swing.SwingFXUtils
import javafx.scene.Scene
import javafx.scene.canvas.Canvas
import javafx.scene.canvas.GraphicsContext
import javafx.scene.control.Button
import javafx.scene.control.ColorPicker
import javafx.scene.image.WritableImage
import javafx.scene.input.MouseEvent
import javafx.scene.layout.BorderPane
import javafx.scene.layout.HBox
import javafx.scene.layout.Priority
import javafx.scene.layout.Region
import javafx.scene.paint.Color
import javafx.stage.FileChooser
import javafx.stage.Stage
import java.awt.image.RenderedImage
import java.io.File
import java.io.IOException
import javax.imageio.ImageIO


class Main2 : Application() {
    val CANVAS_WIDTH = 400 //задаем начальные значения для канваса
    val CANVAS_HEIGHT = 400
    var colorPicker : ColorPicker? = null// объект для выбора цвета рисования

    private fun initDraw(gc: GraphicsContext) {
        colorPicker = ColorPicker() //создаем объект для выбора цвета рисования
        colorPicker!!.value = Color.BLACK //задаем ему начальное значение цвета
        val canvasWidth = gc.canvas.width //локальные значения размеров канваса
        val canvasHeight = gc.canvas.height
        gc.stroke = Color.BLACK //задаем цвет рисования
        gc.strokeRect(0.0, 0.0, canvasWidth, canvasHeight) //рисуем прямоугольник по границам канваса
        gc.fill = colorPicker!!.value //задаем цвет заполнения
        gc.stroke = colorPicker!!.value //и рисования
        gc.lineWidth = 1.0 //задаем толщину линий
    }

    override fun start(primaryStage: Stage?) {
        //создаем канвас
        val canvas = Canvas(CANVAS_WIDTH.toDouble(), CANVAS_HEIGHT.toDouble())
        //получаем его графический контекст
        val graphicsContext = canvas.graphicsContext2D
        initDraw(graphicsContext) //вызываем созданный метод для инициализации графики
//вешаем лиснеры на канвас для обработки событий мыши
        canvas.addEventHandler(MouseEvent.MOUSE_PRESSED) { event -> //обрабатываем нажатие мыши
            graphicsContext.beginPath() //начинаем кривую
            //передвигаем курсор на позицию, где была нажата клавиша мыши
            graphicsContext.moveTo(event.x, event.y)
            //устанавливаем цвет рисования из объекта colorPicker
            graphicsContext.stroke = colorPicker!!.value
            graphicsContext.stroke() //рисуем созданную кривую
        }

        canvas.addEventHandler(MouseEvent.MOUSE_DRAGGED) { event ->
                graphicsContext.lineTo(event.x, event.y)//добавляем к кривой линию до текущих координат мыши
                //устанавливаем цвет рисования из объекта colorPicker
                graphicsContext.stroke = colorPicker!!.value
                graphicsContext.stroke() //рисуем созданную кривую
        }

        canvas.addEventHandler(MouseEvent.MOUSE_RELEASED) { event ->
            //обрабатываем отпускание клавиши мыши (для окончания рисования)
                graphicsContext.lineTo(event.x, event.y)
                graphicsContext.stroke = colorPicker!!.value
                graphicsContext.stroke()
            }

        val root = BorderPane() //создаем панель
        val buttonSave = Button("Save") //кнопка для сохранения рисунка

        buttonSave.setOnAction{    //обработчик нажатия
                val fileChooser = FileChooser() //создаем диалог для работы с файлами
                //создаем фильтр расширений
                val extFilter = FileChooser.ExtensionFilter("png files (*.png)", "*.png")
                fileChooser.extensionFilters.add(extFilter) //применяем его к диалогу

                var file = fileChooser.showSaveDialog(primaryStage)//показываем диалог сохранения файла
                if (file != null) { //если был выбран файл для сохранения
                    //если в имени файла нет расширения .png, то добавляем это расширение к имени файла
                    if (!file.path.contains(".png")) file = File(file.path + ".png")
                    try {
                        //создаем объект класса WritableImage, в который будем сохранять содержимое канваса
                        val writableImage = WritableImage(CANVAS_WIDTH, CANVAS_HEIGHT)
                        //записываем содержимое канваса в writableImage
                        canvas.snapshot(null, writableImage)
                        //создаем объект класса RenderedImage из объекта writableImage
                        val renderedImage: RenderedImage = SwingFXUtils.fromFXImage(writableImage, null)
                        //и записываем его в созданный ранее файл
                        ImageIO.write(renderedImage, "png", file)
                    } catch (ex: IOException) {
                        println("Error: $ex")
                    }
                }
            }

        val hBox = HBox() //горизонтальный бокс для выбора цвета рисования и кнопки сохранения
        val spacer = Region() //спецобъект для пружины между кнопками
        HBox.setHgrow(spacer, Priority.ALWAYS) //задаем ему возможность расширения
        //добавляем все созданные ранее объекты в бокс
        hBox.children.addAll(colorPicker, spacer, buttonSave)
        root.top = hBox //вставляем бокс в верхнюю часть окна
        root.center = canvas //вставляем канвас в центр окна
        val scene = Scene(root, 400.0, 425.0)
        primaryStage!!.title = "SuperPaint"
        primaryStage!!.scene = scene
        primaryStage!!.show()
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            launch(Main2::class.java)
        }
    }
}
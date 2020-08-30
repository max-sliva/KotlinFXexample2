class SimpleRunnable(private val s: String) : Runnable {
    override fun run() {
        while (Thread.currentThread().state == Thread.State.RUNNABLE) {
            println(s)
            try {
                Thread.sleep(500)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        }
    }
}
import ru.innopolis.university.course.s18_473_functional_programming_and_scala_language.assignment1.rganeev._
import org.scalatra._
import javax.servlet.ServletContext

class ScalatraBootstrap extends LifeCycle {
    override def init(context: ServletContext) {
        context.mount(new MessageController, "/messages/*")
        context.mount(new StaticController, "/*")
    }
}

import ru.innopolis.university.course_s18_473._
import org.scalatra._
import javax.servlet.ServletContext

import ru.innopolis.university.course_s18_473.controller.{FeedController, MessageController, SessionController, StaticController}
import ru.innopolis.university.course_s18_473.data.{MessageStore, Repository, User, UserStore}

class ScalatraBootstrap extends LifeCycle {
    override def init(context: ServletContext) {
        val messageStore = MessageStore()
        val userStore = UserStore()
        userStore += (1 -> User(1, "rafis", "1a8c342b4753fe45d2d03c8f24477d722a77c3f1", "Rafis Ganeev", false))
        userStore += (2 -> User(2, "julio", "1a8c342b4753fe45d2d03c8f24477d722a77c3f1", "Julio Reis", false))
        userStore += (3 -> User(3, "deepdrumpf", "1a8c342b4753fe45d2d03c8f24477d722a77c3f1", "DeepDrumpf", true))
        val repository = Repository(messageStore, userStore)

        context.mount(new MessageController(repository), "/messages/*")
        context.mount(new SessionController(repository), "/sessions/*")
        context.mount(new FeedController(repository), "/feed/*")
        context.mount(new StaticController(), "/*")
    }
}

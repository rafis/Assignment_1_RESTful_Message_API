package ru.innopolis.university.course.s18_473_functional_programming_and_scala_language.assignment1.rganeev

import org.scalatra._

class StaticController extends ScalatraServlet {

    get("/") {
        views.html.hello()
    }

}

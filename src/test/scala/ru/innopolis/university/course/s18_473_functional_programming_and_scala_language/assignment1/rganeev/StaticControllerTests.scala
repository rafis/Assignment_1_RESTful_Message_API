package ru.innopolis.university.course.s18_473_functional_programming_and_scala_language.assignment1.rganeev

import org.scalatra.test.scalatest._

class StaticControllerTests extends ScalatraFunSuite {

    addServlet(classOf[StaticController], "/*")

    test("GET / on StaticController should return status 200") {
        get("/") {
            status should equal (200)
            body should include ("Welcome to Scalatra")
        }
    }

}

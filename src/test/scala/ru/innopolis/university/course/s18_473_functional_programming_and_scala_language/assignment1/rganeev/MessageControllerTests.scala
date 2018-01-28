package ru.innopolis.university.course.s18_473_functional_programming_and_scala_language.assignment1.rganeev

import org.scalatra.test.scalatest._
import org.json4s._
import org.json4s.jackson.JsonMethods._

class MessageControllerTests extends ScalatraFunSuite {

    addServlet(classOf[MessageController], "/messages/*")

    def postJson[A](uri: String, body: JValue, headers: Map[String, String] = Map.empty)(f: => A): A =
        post(uri, compact(render(body)).getBytes("utf-8"), Map("Content-Type" -> "application/json; charset=UTF-8") ++ headers)(f)

    def putJson[A](uri: String, body: JValue, headers: Map[String, String] = Map.empty)(f: => A): A =
        put(uri, compact(render(body)).getBytes("utf-8"), Map("Content-Type" -> "application/json; charset=UTF-8") ++ headers)(f)

    test("POST /messages/ on MessageController should create a new message") {
        postJson("/messages/", JObject(List("id" -> JInt(1), "text" -> JString("Test text")))) {
            status should equal (200)
        }
    }

    test("GET /message/ on MessageController should return list of messages including recently created message") {
        get("/messages/") {
            status should equal (200)
            header("Content-Type") should startWith ("application/json;")
            val json = parse(body)
            json(0) \ "id" should equal(JInt(1))
            json(0) \ "text" should equal(JString("Test text"))
        }
    }

    test("GET /messages/1 on MessageController should return the message with id=1") {
        get("/messages/1") {
            status should equal (200)
            header("Content-Type") should startWith ("application/json;")
            val json = parse(body)
            json \ "id" should equal(JInt(1))
            json \ "text" should equal(JString("Test text"))
        }
    }

    test("PUT /messages/1 on MessageController should update the text of the message") {
        putJson("/messages/1", JObject(List("text" -> JString("Some new text")))) {
            status should equal (200)
        }
    }

    test("DELETE /messages/1 on MessageController should delete the message") {
        delete("/messages/1") {
            status should equal (204)
        }
    }

}

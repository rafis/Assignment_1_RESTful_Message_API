package ru.innopolis.university.course.s18_473_functional_programming_and_scala_language.assignment1.rganeev

import scala.collection.mutable.HashMap
import org.scalatra._
import org.scalatra.json._
import org.json4s.{DefaultFormats, Formats}

case class Message(id: Int, text: String)
case class MessageUpdate(text: String)

class MessageController extends ScalatraServlet with JacksonJsonSupport {

    // In-memory storage for messages
    protected val messages: HashMap[Int, Message] = HashMap.empty[Int, Message]

    // Sets up automatic case class to JSON output serialization, required by
    // the JValueResult trait.
    protected implicit lazy val jsonFormats: Formats = DefaultFormats.withBigDecimal

    // Before every action runs, set the content type to be in JSON format.
    before() {
        contentType = formats("json")
    }

    /**
     * Get all messages.
     * GET /messages
     */
    get("/") {
        messages.values
    }

    /**
     * Get the message by ID.
     * GET /messages/:id
     */
    get("/:id") {
        val id: Int = params.getAs[Int]("id").getOrElse(halt(BadRequest("Please provide an message ID")))
        messages(id)
    }

    /**
     * Create a new message.
     * POST /messages
     */
    post("/") {
        val message: Message = parsedBody.extract[Message]
        if (messages.isDefinedAt(message.id)) {
            halt(BadRequest(s"Message with id=${message.id} already exists"))
        }
        messages += (message.id -> message)
        message
    }

    /**
     * Update the message specified by ID.
     * PUT /messages/:id
     */
    put("/:id") {
        val id: Int = params.getAs[Int]("id").getOrElse(halt(BadRequest("Please provide an message ID")))
        if ( ! messages.isDefinedAt(id)) {
            throw new Exception(s"Message with id=${id} does not exists")
        }
        val message = messages(id)
        val updatedMessage = message.copy(text = parsedBody.extract[MessageUpdate].text)
        messages += (id -> updatedMessage)
        updatedMessage
    }

    /**
     * Delete the message specified by ID.
     * DELETE /messages/:id
     */
    delete("/:id") {
        val id: Int = params.getAs[Int]("id").getOrElse(halt(BadRequest("Please provide an message ID")))
        if ( ! messages.isDefinedAt(id)) {
            throw new Exception(s"Message with id=${id} does not exists")
        }
        messages -= id
        NoContent()
    }

}

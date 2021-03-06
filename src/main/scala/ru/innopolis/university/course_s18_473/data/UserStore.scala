package ru.innopolis.university.course_s18_473.data

import scala.collection.mutable.HashMap

case class User(val id: Int, val username: String, val password: String, val name: String, val isBot: Boolean = false, var subscriptions: Set[User] = Set.empty[User])

/**
 * In-memory storage for users.
 */
case class UserStore() {

    protected val users: HashMap[Int, User] = HashMap.empty[Int, User]

    def list: Iterable[User] = users.values

    def isDefinedAt(id: Int): Boolean = users.isDefinedAt(id)

    def findById(id: Int): Option[User] = users.get(id)

    def findByUsername(username: String): Option[User] = users.find(_._2.username == username).map(_._2)

    def subscribe(user: User, subscribeToUser: User) = {
        user.subscriptions += user
    }

    def +=(addUser: Tuple2[Int, User]) = users += addUser

    def -=(deleteUser: Int) = users -= deleteUser

}

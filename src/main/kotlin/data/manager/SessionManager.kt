package data.manager

import data.Movie
import data.ScreeningRoom
import data.Session
import parseStringToDateTime
import parseStringToLocalDateTime
import java.time.LocalDateTime

class SessionManager {
    private var _sessions: MutableList<Session> = mutableListOf()
    var sessions: MutableList<Session>
        get() {
            return _sessions
        }
        set(value) {
            _sessions = value
        }

    fun print() {
        if (sessions.size == 0) {
            println("data.Session list is empty")
            return
        }
        println(
            "data.Session list:\n" +
                    sessions.joinToString(";\n")
        )

    }

    fun showSeats(id: String): Boolean {
        val indOfID = sessions.indexOfFirst { it.ID == id }
        if (indOfID == -1) {
            println("It's impossible to show seats: ID not found")
            return false
        }

        println(
            "Seat scheme\n" +
                    "[occ.]000 -- occupied seat\n" +
                    "{FREE}000 -- free seat"
        )

        val occupied = sessions[indOfID].occupiedSeats

        var counter = 0
        for (i in sessions[indOfID].room.seatsID) {

            if (counter % 5 == 0) println()

            if (occupied.contains(i))
                print("[occ.]${i.toString().padStart(3, '0')}  ")
            else
                print("{FREE}${i.toString().padStart(3, '0')}  ")
            counter++
        }
        println()



        return true
    }

    fun deleteSessionByID(id: String): Boolean {
        val indOfID = sessions.indexOfFirst { it.ID == id }

        if (indOfID == -1) {
            println("It's impossible to delete session: ID not found")
            return false
        }

        _sessions.removeAt(indOfID)
        return true
    }

    fun addSessionByData(id: String, room: ScreeningRoom, movie: Movie, adDuration: Int, startTime: String): Boolean {
        if (sessions.any { it.ID == id }) {
            println("\nIt's impossible to add session with these params: id isn't unique")
            return false
        }

        if (isTimeOverlappingInList(startTime, movie.duration + adDuration, room)) {
            println("\nIt's impossible to add session with these params: session time overlaps with other session")
            return false
        }
        val session = Session(id, room, movie, adDuration, startTime)
        _sessions.add(session)
        return true
    }

    private fun isTimeOverLapping(session: Session, otherStartTime: String, otherDuration: Int): Boolean {

        val otherStartTimeAsLTD = parseStringToLocalDateTime(otherStartTime)
        val sessionStartTimeAsLTD = parseStringToLocalDateTime(session.startTime)

        val endTime: LocalDateTime = otherStartTimeAsLTD.plusMinutes(otherDuration.toLong())
        val sessionEndTime: LocalDateTime = sessionStartTimeAsLTD.plusMinutes(session.totalDuration.toLong())

        //println("\n ${session.ID}  ${session.startTime}  $sessionEndTime and $otherStartTime, $endTime")

        return !(endTime.isBefore((sessionStartTimeAsLTD)) || sessionEndTime.isBefore(otherStartTimeAsLTD))
    }

    private fun isTimeOverlappingInList(startTime: String, duration: Int, room: ScreeningRoom): Boolean {

        for (session in _sessions) {
            if (room == session.room && isTimeOverLapping(session, startTime, duration)) return true
        }

        return false
    }

    fun changeStartTime(input: String, id: String): Boolean {
        val dateTime = parseStringToDateTime(input)
        if (dateTime == null) {
            println("Unable to change datetime: dateTime parsed wrong")
        }

        val indOfID = sessions.indexOfFirst { it.ID == id }

        if (indOfID == -1) {
            println("Unable to change datetime: ID not found")
            return false
        }

        if (isTimeOverlappingInList(input, _sessions[indOfID].totalDuration, _sessions[indOfID].room)) {
            println("New time overlaps with other")
            return false
        }


        _sessions[indOfID].startTime = input
        println("startTime for $id was changed")
        return true
    }

    fun changeID(input: String, id: String): Boolean {
        val indOfID = sessions.indexOfFirst { it.ID == id }

        if (indOfID == -1) {
            println("Unable to change ID: ID not found")
            return false
        }

        if (sessions.any { it.ID == input }) {
            println("Unable to change ID: ID is not unique")
            return false
        }

        println("ID of ${sessions[indOfID].ID} successfully changed to $input")
        sessions[indOfID].ID = input;

        return true
    }

}

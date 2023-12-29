package data.manager

import data.Session
import data.Ticket
import parseStringToLocalDateTime
import java.time.LocalDateTime

class TicketManager {
     var tickets: MutableList<Ticket> = mutableListOf()
        get() = field
        set(value) { field = value}

    fun print() {
        if(tickets.size == 0) {
            println("data.Ticket list is empty")
            return
        }
        println("data.Session list:\n" +
                tickets.joinToString(";\n"))

    }

    fun sellTicket(session: Session, seatNumber: Int): Boolean {

        if(parseStringToLocalDateTime(session.startTime).isBefore(LocalDateTime.now()))  {
            println("data.Session $session already began.")
            return false
        }

        if (!session.room.seatsID.contains(seatNumber)) {
            println("$seatNumber is incorrect number seat.")
            return false
        }

        return if (!seatIsOccupied(session, seatNumber)) {

            session.occupiedSeats.add(seatNumber)
            val ticket = Ticket(session, seatNumber)
            tickets.add(ticket)
            println("data.Ticket $ticket created successfully.")
            true

        } else {
            println("Seat $seatNumber is already occupied.")
            false
        }
    }

    private fun seatIsOccupied(session: Session, seatNumber: Int): Boolean {
        return session.occupiedSeats.contains(seatNumber)
    }

    fun returnTicket(session: Session, seatNumber: Int): Boolean {

        if (parseStringToLocalDateTime(session.startTime).isBefore(LocalDateTime.now()))        {
            println("data.Session ${session.ID} already began.")
            return false
        }

        /*val ticketInd = tickets.indexOfFirst {it.session == session && it.seatNumber == seatNumber}

        if (ticketInd == -1) {
            println("data.Ticket with these parameter doesn't exist.")
            return false
        }*/



        val ticket = Ticket(session, seatNumber)

        session.occupiedSeats.remove(seatNumber)
        val success = tickets.remove(ticket)
        if (success) {
            println("$ticket ticket successfully returned.")
        } else {
            println("Unable to return ticket $ticket.\ndata.Ticket is not in list")
        }
        return success
    }
    // Другие методы для управления билетами
}

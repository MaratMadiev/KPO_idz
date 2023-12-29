package data

import kotlinx.serialization.Serializable

@Serializable
data class Ticket(private val _session: Session, private val _seatNumber: Int){
    val session: Session
        get(){return _session}

    val seatNumber: Int
        get(){return _seatNumber}


    override fun toString(): String {
        return "[session ID: ${session.ID} | seatnumber $seatNumber]"
    }
}
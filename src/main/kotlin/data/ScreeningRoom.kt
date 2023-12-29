package data

import kotlinx.serialization.Serializable

@Serializable
class ScreeningRoom(private var _seatCount: Int = 20, private var _number: Int, private var _name: String) {
    var seatCount: Int
        get() = _seatCount
        set(value) {
            _seatCount = if (value > 0) value else 0
        }

    var number: Int
        get() = _number
        set(value) {
            _number = if (value > 0) value else 0
        }

    var name: String
        get() = _name
        set(value) {
            _name =  value
        }

    val seatsID: List<Int>
        get() {
            return (0..<_seatCount).toMutableList()
        }

    override fun equals(other: Any?): Boolean {
        return _number == (other as? ScreeningRoom)!!.number
    }
}
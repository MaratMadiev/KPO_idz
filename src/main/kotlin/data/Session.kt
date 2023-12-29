package data
import cutDesc
import kotlinx.serialization.Serializable

@Serializable
data class Session(private var _ID: String, var room: ScreeningRoom, private var _movie: Movie, private var _adDuration: Int, private var _startTime: String) {

    val totalDuration: Int
        get(){
            return _movie.duration + _adDuration
        }

    var ID: String
        get(){
            return _ID
        }
        set(value) {
            _ID = value
        }

    var movie: Movie
        get(){
            return _movie
        }
        set(value) {
            _movie = value
        }

    var adDuration: Int
        get() {
            return _adDuration
        }
        set(value) {
            _adDuration = value
        }

    var startTime: String
        get(){
            return _startTime
        }
        set(value) {
            _startTime = value
        }

    private var _occupiedSeatIDS: MutableList<Int> = mutableListOf()
    var occupiedSeats: MutableList<Int>
        get(){
            return _occupiedSeatIDS
        }
        set(value) {
            _occupiedSeatIDS = value
        }


    override fun toString(): String {
        return "ID: ${cutDesc(ID, 10)} | data.Movie name: ${cutDesc(_movie.title, 14)} | " +
                "Room name: ${cutDesc(room.name, 14)} | Start time: ${_startTime} | Total duration: ${totalDuration} minutes"
    }
}

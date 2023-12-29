package data

import cutDesc
import kotlinx.serialization.Serializable

@Serializable
data class Movie(private var _id: String = "", private var _title: String = "", private var _description: String = "", private var _duration: Int){
    var title: String
        get() = _title
        set(value) {
            _title = value
        }

    var id: String
        get() = _id
        set(value) {
            _id = value
        }

    var description: String
        get() = _description
        set(value) {
            _description = value
        }


    var duration: Int
        get() = _duration
        set(value){
            _duration = if (value > 0) value else 0
        }

    override fun toString(): String {
        return "id: ${cutDesc(id, 10)} | name: ${cutDesc(title, 20)} | description: ${cutDesc(description, 28)} | duration: $duration minutes)"
    }

}
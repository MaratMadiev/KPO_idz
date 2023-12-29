package data.manager

import data.Movie

class MovieManager {
    var movies: MutableList<Movie> = mutableListOf()
        get() = field
        set(value) {field = value}

    fun addMovie(movie: Movie) {
        movies.add(movie)
    }

    fun addMovieByParams(id: String, name: String, desc: String, duration: Int): Boolean {
        val isIdInList = movies.any { it.id == id }
        if (isIdInList) {
            return false
        }

        addMovie(Movie(id, name, desc, duration))
        return true
    }

    fun removeMovieByListIndex(index: Int): Boolean {
        if (index < 0 || index > movies.size - 1) return false
        movies.removeAt(index)
        return true
    }

    fun removeMovieByID(id: String): Boolean {
        val index = movies.indexOfFirst { it.id == id }

        if (index == -1) {
            return false
        }

        movies.removeAt(index)

        return true
    }

    fun print() {
        if(movies.size == 0) {
            println("data.Movie list is empty")
            return
        }
        println("\nList of movies:")
        movies.forEachIndexed { index, element ->
            println("Index in list: $index | $element")

        }

    }

    fun changeID(input: String, id: String): Boolean {
        val indOfID = movies.indexOfFirst { it.id == id }

        if (indOfID == -1) {
            println("Unable to change ID: ID not found")
            return false
        }

        if (movies.any { it.id == input }) {
            println("Unable to change ID: ID is not unique")
            return false
        }

        println("ID of ${movies[indOfID].id} successfully changed to $input")
        movies[indOfID].id = input;

        return true
    }

    fun changeNameDesc(newName: String, newDesc: String, id: String): Boolean {
        val indOfID = movies.indexOfFirst { it.id == id }

        if (indOfID == -1) {
            println("Unable to change ID: ID not found")
            return false
        }

        val movie = movies[indOfID];

        if (newName != "") {
            movie.title = newName
        }

        if (newDesc != "") {
            movie.description = newDesc
        }

        println("ID of ${movies[indOfID].id} successfully changed title and description")

        return true
    }

}

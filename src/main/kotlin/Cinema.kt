
import data.Movie
import data.ScreeningRoom
import data.Session
import data.Ticket
import data.manager.MovieManager
import data.manager.SessionManager
import data.manager.TicketManager
import userdata.manager.Account
import kotlin.system.exitProcess

class Cinema() {
    val rootPath = System.getProperty("user.dir")
    val dataRoot = "data"
    val movieListPath = "$rootPath/$dataRoot/movies.json"
    val ticketListPath = "$rootPath/$dataRoot/tickets.json"
    val sessionListPath = "$rootPath/$dataRoot/sessions.json"

    var account = Account("undefined")

    fun run() {

        val movies = dataSerializer.readList<Movie>(movieListPath)
        val sessions = dataSerializer.readList<Session>(sessionListPath)
        val tickets = dataSerializer.readList<Ticket>(ticketListPath)



        if (movies == null)
            println("An error occurred while loading list of movies.\n")
        else
            println("List of movies loaded successfully. \n")

        if (sessions == null)
            println("An error occurred while loading list of sessions.\n")
        else
            println("List of sessions loaded successfully. \n")

        if (tickets == null)
            println("An error occurred while loading list of tickets.\n")
        else
            println("List of tickets loaded successfully. \n")

        movieManager.movies = movies ?: mutableListOf()
        sessionManager.sessions = sessions ?: mutableListOf()
        ticketManager.tickets = tickets ?: mutableListOf()

        println("Welcome to cinema controller!")

        while (true) {
            val input = askForCommand(
                "\nEnter the command:\n" +
                        "mvlist) Print movie list\n" +
                        "mvadd) Add movie to list\n" +
                        "mvrmv) Delete movie from list by index\n" +
                        "mvrmvid) Delete movie from list by ID\n" +
                        "mvedit) Edit movie\n" +

                        "\nseslist) Print session list\n" +
                        "sesadd) Add new session\n" +
                        "sesdel) Delete session by ID\n" +
                        "sesshowseats) show session seats state\n\n" +
                        "sesedit) Edit session\n" +

                        "ticklist) Show ticket list\n" +
                        "ticksell) Sell ticket \n" +
                        "tickreturn) Return ticket\n\n" +
                        "save) Save all\n\n" +

                        "exit) Exit program\n", listOf(
                    "mvlist", "mvadd", "mvrmv", "exit",
                    "seslist", "sesadd", "sesdel", "sesshowseats", "ticklist", "ticksell",
                    "tickreturn", "save", "sesedit", "mvedit"
                )
            )

            commandLogic(input)

        }

    }

    private fun commandLogic(input: String) {
        when (input) {
            "mvlist" -> movieManager.print()
            "mvadd" -> addMovie()
            "mvrmv" -> delMovieByIndex()
            "mvrmvid" -> delMovieByID()
            "save" -> {
                dataSerializer.writeList(movieListPath, movieManager.movies)
                dataSerializer.writeList(sessionListPath, sessionManager.sessions)
                dataSerializer.writeList<Ticket>(ticketListPath, ticketManager.tickets)
            }

            "seslist" -> sessionManager.print()
            "sesadd" -> addSession()
            "sesdel" -> delSession()
            "sesshowseats" -> showSeats()

            "mvedit" -> movieEditLogic()
            "sesedit" -> sessionEditLogic()

            "ticklist" -> ticketManager.print()
            "ticksell" -> sellTicket()
            "tickreturn" -> returnTicket()
            "exit" -> exitProcess(0)
        }
    }

    private fun sessionEditLogic() {
        val input = askForCommand("Which field you want to change?\n" +
                "id) ID\n" +
                "starttime) Start time ", listOf("id", "startTime") )


        when (input) {
            "id" -> changeSessionID()
            "starttime" -> changeSessionStartTime()
        }
    }
    private fun changeSessionStartTime() {
        val input = askString("enter new dateTime")
        val id = askString("enter id of session")
        sessionManager.changeStartTime(input, id)
    }


    private fun changeSessionID() {
        val input = askString("enter new ID")
        val id = askString("enter old id of session")
        sessionManager.changeID(input, id)
    }

    private fun movieEditLogic() {
        val input = askForCommand("Which field you want to change?\n" +
                "id) ID\n" +
                "namedesc) Name and description ", listOf("id", "namedesc") )

        when (input) {
            "id" -> changeMovieID()
            "namedesc" -> changeMovieNameDesc()
        }
    }

    private fun changeMovieNameDesc() {
        val newName = askString("enter new name (or left it empty to save previous variant)")
        val newDesc = askString("enter new name (or left it empty to save previous variant)")
        val id = askString("Enter id of movie")
        movieManager.changeNameDesc(newName, newDesc, id)
    }

    private fun changeMovieID() {
        val input = askString("Enter new ID")
        val id = askString("Enter old id of movie")
        movieManager.changeID(input, id)
    }

    private fun returnTicket() {
        val sesID = askString("Enter session ID")
        val int = askInt("Enter customer's number")

        val session = sessionManager.sessions.firstOrNull() { it.ID == sesID }
        if (session == null) {
            println("Couldn't find $sesID session")
            return
        }

        ticketManager.returnTicket(session, int)

    }

    private fun sellTicket() {
        val sesID = askString("Enter session ID")
        val int = askInt("Enter customer's number")
        val session = sessionManager.sessions.firstOrNull() { it.ID == sesID }
        if (session == null) {
            println("Couldn't find $sesID session")
            return
        }

        ticketManager.sellTicket(session, int)
    }

    private fun showSeats() {
        val ind = askString("Enter ID of session")
        sessionManager.showSeats(ind)
    }

    private fun delSession() {
        val ind = askString("Enter ID of session")
        if (!askYN("Are you sure you want to delete the session?")) return

        if (sessionManager.deleteSessionByID(ind)) println("Deleted session with '$ind' IDse")
        else println("Incorrect ID '$ind'")
    }

    private fun addSession() {
        val ID = askString("Enter session ID")

        val movieID = askString("Enter movie ID")
        val movIndex = movieManager.movies.indexOfFirst { it.id == movieID }

        if (movIndex == -1) {
            println("Unknown ID.")
            return
        }

        val dur = askInt("Enter adverts duration")
        val start = askString("Enter start time in [DD:MM:YYYY:HH:MM] format")
        val dateTime = parseStringToDateTime(start) // need to check datetime correct format

        if (dateTime == null) {
            println("Incorrect date.")
            return
        }

        if (sessionManager.addSessionByData(ID, room, movieManager.movies[movIndex], dur, start))
            println("\nSession was successfully added")
        else
            println("\nSession wasn't added")
    }

    private fun delMovieByIndex() {
        val ind = askInt("Enter index of movie in list")

        if (!askYN("Are you sure you want to delete the movie?")) return

        if (movieManager.removeMovieByListIndex(ind)) println("Deleted movie at '$ind'")
        else println("Incorrect index '$ind'")
    }

    private fun delMovieByID() {
        val ind = askString("Enter ID of movie")
        if (!askYN("Are you sure you want to delete the movie?")) return

        if (movieManager.removeMovieByID(ind)) println("Deleted movie at '$ind'")
        else println("Incorrect ID '$ind'")
    }

    private fun addMovie() {
        val id = askString("Enter movie id")
        val name = askString("Enter movie name")
        val desc = askString("Enter movie description")
        val dur = askInt("Enter movie duration")

        if (!movieManager.addMovieByParams(id, name, desc, dur)) println("data.Movie wasn't added.")
        else println("data.Movie was added successfully.")
    }

    companion object {
        private val room: ScreeningRoom = ScreeningRoom(20, 1, "Зал №1")
        private val movieManager = MovieManager()
        private val sessionManager = SessionManager()
        private val ticketManager = TicketManager()

        private val dataSerializer = DataSerializer()
    }
}


//huh

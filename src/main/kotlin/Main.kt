import userdata.AccountBehaviour

fun main(args: Array<String>) {
    var cinema = Cinema();
    var accBehaviour = AccountBehaviour()

    cinema.account = accBehaviour.run()


    cinema.run()
}

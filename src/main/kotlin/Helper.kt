import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun parseStringToDateTime(dateTimeString: String): LocalDateTime? {
    val formatter = DateTimeFormatter.ofPattern("dd:MM:yyyy:HH:mm")

    return try {
        LocalDateTime.parse(dateTimeString, formatter)
    } catch (e: Exception) {
        null
    }
}

fun askInt(message: String): Int {
    while (true) {
        try {
            print("$message: ")
            return readln().toInt()
        } catch (e: NumberFormatException) {
            println("Please enter correct integer number.")
        }
    }
}

fun askString(message: String): String {
    print("$message: ")
    return readLine() ?: ""
}

fun askYN(message: String): Boolean {
    while (true) {
        print("$message (y/n): ")
        when (readlnOrNull()?.lowercase()) {
            "y" -> return true
            "n" -> return false
            else -> println("Please  enter 'y' or 'n'.")
        }
    }
}

fun askForCommand(message: String, commands: List<String>): String {
    while (true) {
        print("$message: ")
        val userInput = readlnOrNull()


        if (userInput in commands) {
            return userInput ?: ""
        } else {
            println("Wrong command. \n Please enter one of these: ${commands.joinToString(", ")}")
        }
    }
}

fun cutDesc(description: String, chars: Int): String {
    return if (description.length <= chars) {
        // Если длина строки меньше или равна chars, вернуть строку как есть
        description
    } else {
        // Если длина строки больше chars, обрезать до chars - 3 символа и добавить троеточие
        description.substring(0, chars - 3) + "..."
    }
}

fun parseLocalDateTimeToString(date: LocalDateTime): String {
    val formatter = DateTimeFormatter.ofPattern("dd:MM:yyyy:HH:mm")
    return date.format(formatter)
}

fun parseStringToLocalDateTime(stringDate: String): LocalDateTime {
    val formatter = DateTimeFormatter.ofPattern("dd:MM:yyyy:HH:mm")
    return LocalDateTime.parse(stringDate, formatter)
}

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.nio.file.Files
import kotlin.io.path.Path

class DataSerializer {
    inline fun <reified T>readList(path: String): MutableList<T>? {

        val filePath = Path(path)
        val toRet: MutableList<T>
        try {
            val string = Files.readString(filePath)
            toRet = Json.decodeFromString<MutableList<T>>(string)
        } catch (e: Exception){
            println("Error while reading file: ${e.message}")
            return null
        }

        return toRet
    }

    inline fun <reified T>writeList(path: String, movieList: MutableList<T>): Boolean {
        val str = Json.encodeToString(movieList)

        try{
            Files.writeString(Path(path), str)
        } catch (e: Exception) {
            println("Error while writing file: ${e.message}")
            return false
        }
        println("File was written successfully")
        return true
    }
}
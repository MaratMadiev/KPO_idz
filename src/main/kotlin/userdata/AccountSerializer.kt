package userdata

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import userdata.manager.Account
import java.nio.file.Files
import kotlin.io.path.Path

class AccountSerializer() {

    fun readPasswordMap(path: String): MutableMap<String, String>? {
        val filePath = Path(path)
        val toRet: MutableMap<String, String>
        try {
            val string = Files.readString(filePath)
            toRet = Json.decodeFromString<MutableMap<String, String>>(string)
        } catch (e: Exception){
            println("Error while reading file: ${e.message}")
            return null
        }

        return toRet
    }

    fun writePasswordMap(path: String, movieList: MutableMap<String, String>): Boolean {
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

    fun readAccountList(path: String): MutableList<Account>? {
        val filePath = Path(path)
        val toRet: MutableList<Account>
        try {
            val string = Files.readString(filePath)
            toRet = Json.decodeFromString<MutableList<Account>>(string)
        } catch (e: Exception){
            println("Error while reading file: ${e.message}")
            return null
        }

        return toRet
    }

    fun writeAccountList(path: String, movieList: MutableList<Account>): Boolean {
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
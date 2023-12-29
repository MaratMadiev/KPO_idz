package userdata.manager

import kotlinx.serialization.Serializable
import org.mindrot.jbcrypt.BCrypt
import userdata.AccountSerializer

class PasswordManager() {
    val rootPath = System.getProperty("user.dir")
    val dataRoot = "userdata"
    val passListPath = "$rootPath/$dataRoot/users.json"
    val passMapPath = "$rootPath/$dataRoot/data.json"

    private var accountPasswords = mutableMapOf<String, String>()
    var accountList = mutableListOf<Account>()

    fun addPassword(account: Account, password: String) {
        if(accountList.contains(account)) {
            println("This login already exists")
            return
        }

        val hashedPassword = hashPassword(password)
        accountPasswords[account.username] = hashedPassword

        accountList.add(account)
    }

    fun removePassword(account: Account) {
        accountPasswords.remove(account.username)

        accountList.remove(account)
    }

    fun checkPassword(account: Account, inputPassword: String): Boolean {
        val storedHash = accountPasswords[account.username]
        return storedHash != null && checkPassword(inputPassword, storedHash)
    }

    // Метод, шифрующий пароль
    private fun hashPassword(password: String): String {
        val salt = BCrypt.gensalt()
        return BCrypt.hashpw(password, salt)
    }

    // Приватный метод для проверки пароля с использованием BCrypt
    private fun checkPassword(inputPassword: String, storedHash: String): Boolean {
        return BCrypt.checkpw(inputPassword, storedHash)
    }

    fun serialize() {
        accountSerializer.writePasswordMap(passMapPath, accountPasswords)
        accountSerializer.writeAccountList(passListPath, accountList)
    }

    fun deserialize() {
        accountPasswords = accountSerializer.readPasswordMap(passMapPath) ?: mutableMapOf<String, String>()
        accountList = accountSerializer.readAccountList(passListPath) ?: mutableListOf<Account>()
    }




    companion object {
        var accountSerializer = AccountSerializer()
    }
}


@Serializable
data class Account(val username: String)


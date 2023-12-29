package userdata

import askString
import askYN
import userdata.manager.Account
import userdata.manager.PasswordManager

class AccountBehaviour() {
    var passwordManager = PasswordManager()


    fun run(): Account {
        passwordManager.deserialize()

        var login: String

        while (true){
            val loginOrRegister = askYN("Do you want to login (Y) or create new account (N)?")
            if(loginOrRegister) {
                login = askString("Enter login")
                val password = askString("Enter password")
                val account = passwordManager.accountList.firstOrNull { it.username == login }
                if (account == null) {
                    println("Incorrect login")
                    continue
                }

                val isPasswordCorect = passwordManager.checkPassword(account, password)

                if (isPasswordCorect) {
                    println("Correct password")
                    break
                } else {
                    println("Incorrect password")
                }
            } else {
                val newLogin = askString("Enter new login")
                val newPassword = askString("Enter new password")

                val acc = Account(newLogin)
                passwordManager.addPassword(acc, newPassword)
                println("Account was created")
            }
        }

        val account = passwordManager.accountList.firstOrNull { it.username == login }?: Account("undefined")

        passwordManager.serialize()

        return account
    }
}
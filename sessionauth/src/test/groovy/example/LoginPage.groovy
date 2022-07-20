package example

import geb.Page
import geb.module.PasswordInput
import geb.module.TextInput

class LoginPage extends Page {

    static url = '/login'

    static at = { title == 'Login' }

    static content = {
        usernameInput { $('input', name: 'username').module(TextInput) }
        passwordInput { $('input', name: 'password').module(PasswordInput) }
        loginButton { $('input', type: 'submit') }
    }

    void setUsername(String username) {
        usernameInput.setText(username)
    }

    void setPassword(String password) {
        passwordInput.setText(password)
    }

    void login(String username, String password) {
        setUsername(username)
        setPassword(password)
        loginButton.click()
    }
}

package example

import geb.Page

import io.micronaut.core.annotation.Nullable

class HomePage extends Page {

    static url = "/"

    static at = { title == 'Home' }

    static content = {
        usernameElement(required: false) { $('span', id: 'username') }
        logoutButton(required: false) { $('input', type: 'submit', value: 'Logout') }
    }

    @Nullable
    String getUsername() {
        if (usernameElement.empty) {
            return null
        }
        usernameElement.text()
    }

    void logout() {
        logoutButton.click()
    }
}

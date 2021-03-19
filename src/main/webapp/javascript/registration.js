const $ = require('jquery')

const checkRegistrationInfo = (name, address, ticketType, ticketCount, eventId) => {
    if (name === "") {
        return false
    }
    if (address === "") {
        return false
    }
    if (ticketType === "") {
        return false
    }
    if (ticketCount === "" || ticketCount < 1) {
        return false
    }
    if (eventId === "") {
        return false
    }

    return true
}

const registerBtnClicked = () => {
    $('#registrationForm').attr('action', '/register').submit()
}

$(() => {
    $('#purchase').on('click', () => {
        registerBtnClicked()
    })
})

module.exports = { registerBtnClicked, checkRegistrationInfo }
function RegistrationInfo() {}

RegistrationInfo.prototype.checkRegistrationInfo = function(name, address, ticketType, ticketCount, eventId) {
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
const $ = require('jquery');

const checkAll = () => {
    var checked = $('#all').prop('checked');
    $('input[name="mailAddress"]').prop('checked', checked);
}

$(() => {
    $('#all').on('click', () => {
        checkAll()
    })
})

module.exports = checkAll;
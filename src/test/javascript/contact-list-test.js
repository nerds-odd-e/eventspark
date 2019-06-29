describe('check all', function(){

    var testContainer;
    var contactList;

    beforeEach(function() {
        contactList = new ContactList();
        testContainer = $('<div></div>');

        $('<input type="checkbox" id="all" />' +
        '<input type="checkbox" name="mailAddress" id="user1gmail.com" />' +
        '<input type="checkbox" name="mailAddress" id="user2gmail.com" />').appendTo(testContainer);

        testContainer.appendTo('body');
    })

    afterEach(function() {
        testContainer.remove();
    })

    it('given not all check box then click all checkbox', function() {
        $('#all').prop('checked', true);
        contactList.checkAll();
        expect($('input[name="mailAddress"]:checked').length).toEqual(2);
    })

    it('given all check box then click all checkbox', function() {
        $('#user1gmail.com').prop('checked', true);
        $('#user2gmail.com').prop('checked', true);
        contactList.checkAll();
        expect($('input[name="mailAddress"]:checked').length).toEqual(0);
    })


})
describe('check registration info', function() {

    var registrationInfo

    beforeEach(function() {
        registrationInfo = new RegistrationInfo()
    })

    afterEach(function() {
    })

    it('given 全ての項目が正しい then trueが返る', function() {
        expect(registrationInfo.checkRegistrationInfo("aaa", "aaa@example.com", "1day", "1", "event")).toBeTruthy()
    })

    it('given 名前が空白 then falseが返る', function() {
        expect(registrationInfo.checkRegistrationInfo("", "aaa@example.com", "1day", "1", "event")).toBeFalsy()
    })

    it('given メールアドレスが空白 then falseが返る', function() {
        expect(registrationInfo.checkRegistrationInfo("aaa", "", "1day", "1", "event")).toBeFalsy()
    })

    it('given チケット種別が空白 then falseが返る', function() {
        expect(registrationInfo.checkRegistrationInfo("aaa", "aaa@example.com", "", "1", "event")).toBeFalsy()
    })

    it('given チケット枚数が空白 then falseが返る', function() {
        expect(registrationInfo.checkRegistrationInfo("aaa", "aaa@example.com", "1day", "", "event")).toBeFalsy()
    })

    it('given チケット枚数が0 then falseが返る', function() {
        expect(registrationInfo.checkRegistrationInfo("aaa", "aaa@example.com", "1day", "0", "event")).toBeFalsy()
    })

    it('given イベントIDが空白 then falseが返る', function() {
        expect(registrationInfo.checkRegistrationInfo("aaa", "aaa@example.com", "1day", "1", "")).toBeFalsy()
    })

})
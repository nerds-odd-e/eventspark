Feature: My First Feature

  @focus
  Scenario: register to event
    Given 参加者が参加ページにいる
    And 名前が入力されている
    And メールアドレスが入力されている
    And チケット種別が入力されている
    And チケット枚数が入力されている
    When 購入ボタンを押す
    Then 完了ページを表示する
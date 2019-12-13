Feature: Event Register

  Background:
    Given ゴスペルワークショップのイベント名のデータが1件あること
    And ゴスペルワークショップの参加登録ページが表示されている

#  case for
#  success case
  @developing
  Scenario: 参加登録
    Given 以下が入力されている
    |名前|xxx|
    |苗字|xxx|
    |会社名|xxx|
    |メールアドレス|xxx@xxx|
    |チケット種別|1|
    |枚数|10|
#    |name|address|ticketType|ticketCount|eventId|
#    |xxx|xxx@xxx |1         |1          |1      |
    When 購入ボタンを押す
    Then 支払メールを送信する
      | from               | to              | subject  | body     |
      | eventspark@gmx.com | user1@gmail.com | Hi user1 | Hi user1 |
    And 参加登録完了画面を表示する

#  field case
  @developing
  Scenario: 名前が入力されていない
    Given 以下が入力されている
      |名前||
      |苗字|xxx|
      |会社名|xxx|
      |メールアドレス|xxx@xxx|
      |チケット種別|1|
      |枚数|10|
    When 購入ボタンを押す
    Then 参加者登録画面エラーエリアに"名前が未入力です。"と表示される
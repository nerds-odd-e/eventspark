Feature: Event Register

  Background:
    Given ゴスペルワークショップのイベント名のデータが1件あること
    And "ゴスペルワークショップ"の参加登録ページが表示されている

#  case for
#  success case
  Scenario: ゴスペルワークショップへ参加登録ができる
    Given 以下が入力されている
    |名前|xxx|
    |苗字|xxx|
    |会社名|xxx|
    |メールアドレス|xxx@xxx|
    |チケット(チケットID)|1|
    When 購入ボタンを押す
    Then "ゴスペルワークショップ"のイベント詳細ページが表示される

#  field case
  @developing
  Scenario: 名前が入力されていない
    Given 以下が入力されている
      |名前||
      |苗字|xxx|
      |会社名|xxx|
      |メールアドレス|xxx@xxx|
      |チケット種別|1|
    When 購入ボタンを押す
    Then 参加者登録画面エラーエリアに"名前が未入力です。"と表示される
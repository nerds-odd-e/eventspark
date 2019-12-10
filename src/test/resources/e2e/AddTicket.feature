Feature: Add Ticket
  Background:
    Given チケット追加画面を表示している

@developing
# success case
  Scenario: add a ticket
    When チケット名"ゴスペル"を入力する
    And 金額"1000円"を入力する
    And 枚数「100枚」入力する
    And 一人当たりの上限数を「５枚」と入力する
    And 「登録」ボタンをクリックする
    Then イベント詳細ページに戻る
    And 入力したチケット情報が表示されている

@developing
# Failure case
  Scenario: 全ての項目を空で登録ボタンを押下
    When チケット名に「」を入力する
    And 金額に「」を入力する
    And 枚数に「」を入力する
    And 一人当たりの上限数に「」を入力する
    And 「登録」ボタンをクリックする
    Then 画面遷移しない
    And 画面上に「チケット名を入力してください」を表示する
    And 画面上に「金額を入力してください」を表示する
    And 画面上に「枚数を入力してください」を表示する
    And 画面上に「一人当たりの上限数を入力してください」を表示する




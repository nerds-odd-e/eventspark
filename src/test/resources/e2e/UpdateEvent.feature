Feature: Update Event

  @developing
  Scenario: イベント情報の更新
    Given イベント名がゴスペルワークショップのイベントのデータが1件DBにあること
    When イベント更新ページに変更内容を入力して確定ボタンを押す
    Then 変更した内容がイベント詳細ページに表示されていること
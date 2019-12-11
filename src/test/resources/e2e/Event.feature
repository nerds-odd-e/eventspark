Feature: Event

  @developing
  Scenario: ゆうこさんがゴスペルワークショップイベントをEventSparkに追加する
    Given ゆうこさんが存在する
    When イベント追加ページにゴスペルワークショップの情報を入力する
    And イベント追加ページを表示する
    Then 入力したゴスペルワークショップの情報がDBに登録される
    And 入力したゴスペルワークショップの情報が詳細ページに表示される

  Scenario: show event detail
    Given ゴスペルワークショップのイベント名のデータが1件DBにあること
    When "ゴスペルワークショップ"のイベント詳細ページを表示する
    Then "ゴスペルワークショップ"のイベントの内容とチケットの内容のイベント詳細を表示する。

  Scenario: show event detail for admin
    Given ゴスペルワークショップのイベント名のデータが1件DBにあること
    When "ゴスペルワークショップ"の管理者用イベント詳細ページを表示する
    Then "ゴスペルワークショップ"のイベントの内容とチケットの内容の管理者用イベント詳細ページを表示する。

  @developing
  Scenario: イベント情報の更新
    Given イベント名がゴスペルワークショップのイベントのデータが1件DBにあること
    When イベント更新ページに変更内容を入力して確定ボタンを押す
    Then 変更した内容がイベント詳細ページに表示されていること
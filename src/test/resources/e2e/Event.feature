Feature: Event

  @developing
  Scenario: ゴスペルワークショップイベントをEventSparkに追加する
    Given イベント追加ページを表示する
    When イベント追加ページに以下の情報を入力する
    |イベント名|ゴスペルワークショップ|
    |場所  |東京フォーラム              |
    |イベント情報|アーティスト：カークフランクリン¥n 演目：未定|
    |サマリー  |ゴスペルワークショップです           |
    |イベント開始日時|2020年6月11日 9:00|
    |イベント終了日時|2020年6月11日 17:00|
    Then "ゴスペルワークショップ"のイベントの内容とチケットの内容のオーナー用イベント詳細ページを表示する。

  Scenario: show event detail
    Given ゴスペルワークショップのイベント名のデータが1件DBにあること
    When "ゴスペルワークショップ"のイベント詳細ページを表示する
    Then "ゴスペルワークショップ"のイベントの内容とチケットの内容のイベント詳細を表示する。

  Scenario: show event detail for owner
    Given ゴスペルワークショップのイベント名のデータが1件DBにあること
    When "ゴスペルワークショップ"のオーナー用イベント詳細ページを表示する
    Then "ゴスペルワークショップ"のイベントの内容とチケットの内容のオーナー用イベント詳細ページを表示する。

  @developing
  Scenario: イベント情報の更新
    Given イベント名がゴスペルワークショップのイベントのデータが1件DBにあること
    When イベント更新ページに変更内容を入力して確定ボタンを押す
    Then 変更した内容がイベント詳細ページに表示されていること

  @developing
  Scenario: オーナー用イベント詳細ページからイベントのチケット追加ページに移動する
    Given ゴスペルワークショップのイベント名のデータが1件DBにあること
    And "ゴスペルワークショップ"のオーナー用イベント詳細ページを表示する
    When オーナー用イベント詳細ページのチケット追加ボタンを押す
    Then チケット追加ページが表示されていること

  Scenario: 公開中のイベントの一覧表示
    Given イベントオーナーが複数イベントを登録すると
    When 公開中のイベント一覧ページをみると
    Then 一覧に複数イベントが見れる

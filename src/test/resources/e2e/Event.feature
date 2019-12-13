Feature: Event

  Scenario Outline: ゴスペルワークショップイベントをEventSparkに追加して詳細が見れる
    Given イベント追加ページを表示する
    And イベントのデータが１件も存在しないこと
    When イベント追加ページに以下の情報を入力する
    |イベント名|ゴスペルワークショップ|
    |オーナー |ゆうこ        |
    |場所  |東京フォーラム              |
    |イベント情報|アーティスト：カークフランクリン¥n 演目：未定|
    |サマリー  |ゴスペルワークショップです           |
    |イベント開始日時|2020-06-11 09:00|
    |イベント終了日時|2020-06-11 17:00|
    |画像URL   |https://3.bp.blogspot.com/-cwPnmxNx-Ps/V6iHw4pHPgI/AAAAAAAA89I/3EUmSFZqX4oeBzDwZcIVwF0A1cyv0DsagCLcB/s800/gassyou_gospel_black.png|
    And <user_type>が"ゴスペルワークショップ"の詳細ページを見る
    Then "ゴスペルワークショップ"のイベントの内容とチケット<button>ボタンが表示される

    Examples:
      |user_type|button|
      |owner   |追加|
      |user    |購入|

  Scenario: オーナー用イベント詳細ページからイベントのチケット追加ページに移動する
    Given ゴスペルワークショップのイベント名のデータが1件あること
    And "ゴスペルワークショップ"のオーナー用イベント詳細ページを表示する
    When オーナー用イベント詳細ページのチケット追加ボタンを押す
    Then チケット追加ページが表示されていること

  Scenario: 公開中のイベントの一覧表示
    Given イベントオーナーが複数イベントを登録すると
    When 公開中のイベント一覧ページをみると
    Then 一覧に複数イベントが見れる

  Scenario: 公開中のイベントの一覧からイベント詳細ページを表示する
    Given イベントオーナーが複数イベントを登録すると
    When 公開中のイベント一覧ページをみると
    And イベントタイトルのリンクをクリックすると
    Then クリックしたイベントの詳細ページが表示される

  @developing
  Scenario: イベント情報の更新
    Given ゴスペルワークショップのイベント名のデータが1件あること
    And 編集ページを開いていること
    When イベント更新ページに変更内容を入力して確定ボタンを押す
      |イベント名|ゴスペルワークショップ|
      |オーナー |ゆうこ        |
      |場所  |幕張メッセ              |
      |イベント情報|アーティスト：カークフランクリン¥n 演目：未定|
      |サマリー  |ゴスペルワークショップです           |
      |イベント開始日時|2020-06-11 09:00|
      |イベント終了日時|2020-06-11 17:00|
      |画像URL   |https://3.bp.blogspot.com/-cwPnmxNx-Ps/V6iHw4pHPgI/AAAAAAAA89I/3EUmSFZqX4oeBzDwZcIVwF0A1cyv0DsagCLcB/s800/gassyou_gospel_black.png|
    Then "ゴスペルワークショップ"のイベントの内容とチケット追加ボタンが表示される
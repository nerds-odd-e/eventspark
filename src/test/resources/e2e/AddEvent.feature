Feature: Add Event

    @developing
    Scenario: ゆうこさんがゴスペルワークショップイベントをEventSparkに追加する
        Given ゆうこさんが存在する
        When イベント追加ページにゴスペルワークショップの情報を入力する
        Then 入力したゴスペルワークショップの情報がDBに登録される
        And 入力したゴスペルワークショップの情報が詳細ページに表示される

Feature: Preview Event

  Scenario: show preview event
    Given ゴスペルワークショップのイベント名のデータが1件DBにあること
    When イベントプレビューページを表示する
    Then ゴスペルワークショプのイベントのプレビュー内容とチケットの内容を表示する。
Feature: Event Detail

  Scenario: show event detail
    Given ゴスペルワークショップのイベント名のデータが1件DBにあること
    When イベント詳細ページを表示する
    Then ゴスペルワークショップのイベントの内容とチケットの内容を表示する。
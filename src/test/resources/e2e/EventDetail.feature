Feature: Event Detail

  Scenario: show event detail
    Given ゴスペルワークショップのイベント名のデータが１件DBにあること
    When イベント詳細ページを表示する
    Then ゴスペルワークショプのイベントの内容とチケットの内容を表示する。
package com.odde.mailsender.data;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Data
@Builder
public class Event {

    /**
     * ID
     */
    @Id
    private String id;

    /**
     * イベント名
     */
    private String name;

    /**
     * 開催場所
     */
    private String location;

    /**
     * イベント作成者
     */
    private String owner;

    /**
     * 登録日付
     */
    private LocalDateTime createDateTime;

    /**
     * 更新日付
     */
    private LocalDateTime updateDateTime;

    /**
     * イベントサマリー
     */
    private String summary;

    /**
     * イベント開始時刻
     */
    private LocalDateTime startDateTime;

    /**
     * イベント終了時刻
     */
    private LocalDateTime endDateTime;

    /**
     * イベント公開時刻
     */
    private LocalDateTime publishedDateTime;

    /**
     * イベント内容の詳細テキスト
     */
    private String detail;

    private String imagePath;

    private String detailUrl;

}

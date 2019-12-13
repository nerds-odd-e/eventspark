package com.odde.mailsender.data;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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

    public String getEventUrl() { return "http://localhost:8080" + "/event/" + this.getName();}

    public List<Long> countUnsoldTickets (List<Ticket> ticketList, List<RegistrationInfo> registrationInfoList) {
        return ticketList.stream().map(ticket -> ticket.countUnsoldTicket(registrationInfoList)).collect(Collectors.toList());
    }

}

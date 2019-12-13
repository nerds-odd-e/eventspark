package com.odde.mailsender.data;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

    public String getEventUrl() throws UnsupportedEncodingException {
        return "http://localhost:8080" + "/event/" + URLEncoder.encode(this.name, StandardCharsets.UTF_8.name()).replace("+", "%20");
    }

    public List<Long> countUnsoldTickets (List<Ticket> ticketList, List<RegistrationInfo> registrationInfoList) {
        return ticketList.stream().map(ticket -> ticket.countUnsoldTicket(registrationInfoList)).collect(Collectors.toList());
    }

    public Long countAllUnsoldTickets (List<Ticket> ticketList, List<RegistrationInfo> registrationInfoList) {
        return countUnsoldTickets(ticketList, registrationInfoList).stream().mapToLong(ticket -> ticket.longValue()).sum();
    }

    public String startDateTimeToString() {
        if (startDateTime == null) {
            return "";
        }
        return startDateTime.format(DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm"));
    }

    public String endDateTimeToString() {
        if (endDateTime == null) {
            return "";
        }
        return endDateTime.format(DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm"));
    }

}

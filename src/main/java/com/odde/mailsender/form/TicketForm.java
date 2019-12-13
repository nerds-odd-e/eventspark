package com.odde.mailsender.form;

import com.odde.mailsender.data.Ticket;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class TicketForm {

    @NotEmpty(message = "チケット名を入力してください")
    private String ticketName;
    @NotNull(message = "金額を入力してください")
    private Long ticketPrice;
    @NotNull(message = "枚数を入力してください")
    private Long ticketTotal;
    @NotNull(message = "一人当たりの上限数を入力してください")
    private Integer ticketLimit;
    @NotEmpty
    private String eventId;

    public TicketForm() {
    }

    public TicketForm(
            String ticketName,
            Long ticketPrice,
            Long ticketTotal,
            Integer ticketLimit,
            String eventId) {
        this.ticketName = ticketName;
        this.ticketPrice = ticketPrice;
        this.ticketTotal = ticketTotal;
        this.ticketLimit = ticketLimit;
        this.eventId = eventId;
    }

    public String getTicketName() {
        return ticketName;
    }

    public void setTicketName(String ticketName) {
        this.ticketName = ticketName;
    }

    public Long getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(Long ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    public Long getTicketTotal() {
        return ticketTotal;
    }

    public void setTicketTotal(Long ticketTotal) {
        this.ticketTotal = ticketTotal;
    }

    public Integer getTicketLimit() {
        return ticketLimit;
    }

    public void setTicketLimit(Integer ticketLimit) {
        this.ticketLimit = ticketLimit;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public Ticket createTicket() {
        return Ticket.builder()
                .ticketName(ticketName)
                .ticketPrice(ticketPrice)
                .ticketTotal(ticketTotal)
                .ticketLimit(ticketLimit)
                .eventId(eventId)
                .build();
    }
}


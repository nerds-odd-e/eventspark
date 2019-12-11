package com.odde.mailsender.form;

import com.odde.mailsender.data.Ticket;

public class TicketForm {
    private String ticketName;
    private Long ticketPrice;
    private Long ticketTotal;
    private Integer ticketLimit;
    private String eventId;

    public TicketForm() {};

    public TicketForm(
        String ticketName,
        Long ticketPrice,
        Long ticketTotal,
        Integer ticketLimit,
        String eventId){
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

    public long getTicketTotal() {
        return ticketTotal;
    }

    public void setTicketTotal(long ticketTotal) {
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
}

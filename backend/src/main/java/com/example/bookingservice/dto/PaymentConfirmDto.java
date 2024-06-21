package com.example.bookingservice.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PaymentConfirmDto {
    private int bookingId;
    private int paymentId;
}

package com.pro2111.beans;

import com.pro2111.entities.Bill;
import com.pro2111.entities.CartDetail;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import java.util.List;

@Data
public class BuyRequest {
    @NotNull
    private Bill bill;
    @NotEmpty
    private List<CartDetail> cartDetails;
}

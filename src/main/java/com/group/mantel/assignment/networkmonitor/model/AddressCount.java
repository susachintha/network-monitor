package com.group.mantel.assignment.networkmonitor.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AddressCount {

    private String address;

    private Long count;
}

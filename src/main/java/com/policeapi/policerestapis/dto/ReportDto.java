package com.policeapi.policerestapis.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.crypto.Data;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReportDto {
    private long id;
    private String title;
    private String description;
    private String location;
    private String type;
    private Data createdAt;
    private long userId;
    private String imageUrls;
}

package com.policeapi.policerestapis.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Date;

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
    private Date createdAt;
    private long userId;
    private String imageUrl;
    private String status;
}

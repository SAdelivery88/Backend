package group1.sa_delivery.dto;

import lombok.Data;

@Data
public class SearchRequest {
    private String key;
    private Integer page = 1;
}

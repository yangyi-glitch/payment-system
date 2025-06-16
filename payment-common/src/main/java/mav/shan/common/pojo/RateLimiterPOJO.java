package mav.shan.common.pojo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RateLimiterPOJO {
    private LocalDateTime time;
    private int count;
    private int lock;
}

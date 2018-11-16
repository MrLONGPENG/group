package com.lveqia.cloud.common.objeck.to;

import lombok.Data;

@Data
public class UptimeTo {

    private Integer startTime;
    private Integer stopTime;
    private Integer noonStartTime;
    private Integer noonStopTime;

    public boolean isNoonTime(long seconds) {
        return seconds > noonStartTime && seconds < noonStopTime;
    }

    public boolean isUsingTime(long seconds) {
        return seconds > startTime || seconds < stopTime;
    }
}

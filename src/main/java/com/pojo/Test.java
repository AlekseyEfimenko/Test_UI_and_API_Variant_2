package com.pojo;

import lombok.Data;
import java.util.Objects;

@Data
public class Test {
    private String duration;
    private String method;
    private String name;
    private String startTime;
    private String endTime;
    private String status;

    @Override
    public String toString() {
        return String.format("Test: %nduration: %s%nmethod: %s%nname: %s%nstartTime: %s%nendTime: %s%nstatus: %s%n",
                duration, method, name, startTime, endTime, status);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Test user = (Test) o;
        return Objects.equals(duration, user.duration) && Objects.equals(method, user.method)
                && Objects.equals(name, user.name) && Objects.equals(startTime, user.startTime)
                && Objects.equals(endTime, user.endTime) && Objects.equals(status, user.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(duration, method, name, startTime, endTime, status);
    }
}

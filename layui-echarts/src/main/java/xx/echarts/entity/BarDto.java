package xx.echarts.entity;

import lombok.Data;

@Data
public class BarDto {
    private String name;

    private Integer value;

    public BarDto() {
    }

    public BarDto(String name, Integer value) {
        this.name = name;
        this.value = value;
    }
}

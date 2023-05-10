package xx.echarts.entity;

import lombok.Data;

@Data
public class LineDto {
    private String name;

    private Integer value;

    public LineDto() {
    }

    public LineDto(String name, Integer value) {
        this.name = name;
        this.value = value;
    }
}

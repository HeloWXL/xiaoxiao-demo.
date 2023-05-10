package xx.echarts.entity;

import lombok.Data;

@Data
public class PieDto {

    private String name;

    private Integer value;

    public PieDto() {
    }

    public PieDto(String name, Integer value) {
        this.name = name;
        this.value = value;
    }
}

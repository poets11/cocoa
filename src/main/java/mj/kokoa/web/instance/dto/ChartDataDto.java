package mj.kokoa.web.instance.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by poets11 on 2016. 11. 5..
 */
@Data
public class ChartDataDto {
    private List<String> labelList;
    private List<DataDto> dataList;

    @Data
    public static class DataDto {
        private String label;
        private List<Double> data = new ArrayList<Double>();

        public void appendValue(Double value) {
            data.add(value);
        }
    }
}

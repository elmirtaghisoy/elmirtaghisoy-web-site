package az.et.ws.component.job;

import java.util.Arrays;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemReader;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class FirstItemReader implements ItemReader<Integer> {

    List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
    int i = 0;

    @Override
    public Integer read() {
        log.info("Inside Item Reader");
        Integer item;
        if (i < list.size()) {
            item = list.get(i);
            i++;
            return item;
        }
        i = 0;
        return null;
    }

}

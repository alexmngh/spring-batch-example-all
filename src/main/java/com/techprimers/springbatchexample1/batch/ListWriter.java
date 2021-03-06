package com.techprimers.springbatchexample1.batch;

import com.techprimers.springbatchexample1.model.Marksheet;
import com.techprimers.springbatchexample1.model.User;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ListWriter implements ItemWriter<Marksheet> {

  @Override
  public void write(List<? extends Marksheet> marksheets) throws Exception{
    log.info("ItemWriter: write");
    for (Marksheet mark: marksheets) {
      log.info("ItemWriter: getStdId " + mark.getStdId());
      log.info("ItemWriter: getTotalSubMark " + mark.getTotalSubMark());
    }
  }
}

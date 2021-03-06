package com.techprimers.springbatchexample1.batch;

import com.techprimers.springbatchexample1.model.Emaill;
import com.techprimers.springbatchexample1.model.ListEmaill;
import com.techprimers.springbatchexample1.model.Marksheet;
import com.techprimers.springbatchexample1.model.User;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ListObjectWriter implements ItemWriter<Emaill> {

  @Override
  public void write(List<? extends Emaill> marksheets) {
    log.info("ItemWriter: write");
    for (Emaill mark: marksheets) {
      log.info("ItemWriter: getStdId " + mark.getNumber());
      log.info("ItemWriter: getStdId " + mark.getNumberChanel());
      log.info("ItemWriter: getStdId " + mark.getName());
      log.info("ItemWriter: getStdId " + mark.getText());
    }
  }
}

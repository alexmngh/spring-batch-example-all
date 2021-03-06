package com.techprimers.springbatchexample1.batch;

import com.techprimers.springbatchexample1.model.EmailChanel;
import com.techprimers.springbatchexample1.model.Emaill;
import com.techprimers.springbatchexample1.model.Marksheet;
import com.techprimers.springbatchexample1.model.Student;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ListListProcessor implements ItemProcessor<Emaill, EmailChanel> {

  @Override
  public EmailChanel process(Emaill emaill) throws Exception {

    String number =
        String.valueOf(emaill.getNumber()) + "_" + String.valueOf(emaill.getNumberChanel());
    String name = emaill.getName() + "__" + emaill.getText();

    EmailChanel emailChanel = new EmailChanel(number, name);

    log.info("ListItemProcessor: process");
    return emailChanel;
  }
}

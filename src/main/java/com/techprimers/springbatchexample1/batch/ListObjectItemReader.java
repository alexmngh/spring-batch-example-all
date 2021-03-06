package com.techprimers.springbatchexample1.batch;

import com.techprimers.springbatchexample1.model.Chanell;
import com.techprimers.springbatchexample1.model.Emaill;
import com.techprimers.springbatchexample1.model.ListEmaill;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ListObjectItemReader implements ItemReader<ListEmaill> {

  @Autowired
  ListEmaill listEmaill;

  @Override
  public ListEmaill read() {

    List<Emaill> emaills = new ArrayList<>();
    int number = 0;

    for (int j = 0; j < 5; j++) {
      for (int i = 0; i < 5; i++) {
        number += i;
        Emaill email = new Emaill(number, j,"em_" + String.valueOf(j + number),
            "message_" + String.valueOf(j + number + 10*i +100));
        emaills.add(email);
      }
    }
    listEmaill.setEmaillList(emaills);
    return listEmaill;

  }

}

package com.techprimers.springbatchexample1.batch;

import com.techprimers.springbatchexample1.model.Emaill;
import com.techprimers.springbatchexample1.model.Chanell;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemReader;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ListListItemReader implements ItemReader<Emaill> {

  private List<Emaill> emaills;
  private List<Chanell> chanells;

  private Integer numberChanel;
  private Integer maxNumberChanel;
  private Integer numberEmail;
  private Integer maxNumberEmail;

  @BeforeStep
  public void open(){
    emaills = new ArrayList<>();
    chanells = new ArrayList<>();
    int number = 0;

    for (int i = 0; i < 5; i++) {
      Chanell chanell = new Chanell("ch_" + String.valueOf(number), i);
      chanells.add(chanell);
    }

    for (int j = 0; j < 5; j++) {
      for (int i = 0; i < 5; i++) {
        number += i;
        Emaill email = new Emaill(number, j,"em_" + String.valueOf(j + number),
            "message_" + String.valueOf(j + number + 10*i +100));
        emaills.add(email);
      }
    }
    numberChanel = 0;
    maxNumberChanel = chanells.size();
    numberEmail = 0;
    maxNumberEmail = emaills.size();

  }

  @Override
  public Emaill read() {
    if (numberEmail >= maxNumberEmail) {
      numberChanel++;
      numberEmail = 0;
    }
    if (numberChanel >= maxNumberChanel) {
      return null;
    }
    Emaill email = emaills.get(numberEmail);
    numberEmail++;
    return email;
  }

}

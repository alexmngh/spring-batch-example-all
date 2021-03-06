package com.techprimers.springbatchexample1.model;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class ListEmaill {
  private List<Emaill> emaillList = new ArrayList<>();

  public List<Emaill> getEmaillList() {
    return emaillList;
  }

  public void setEmaillList(List<Emaill> emaillList) {
    this.emaillList = emaillList;
  }
}

package com.techprimers.springbatchexample1.model;

public class Chanell {

  private String name;
  private Integer number;

  public Chanell(String name, Integer number) {
    this.name = name;
    this.number = number;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Integer getNumber() {
    return number;
  }

  public void setNumber(Integer number) {
    this.number = number;
  }
}

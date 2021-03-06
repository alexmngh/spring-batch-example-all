package com.techprimers.springbatchexample1.model;

public class Emaill {
  private Integer number;
  private Integer numberChanel;
  private String name;
  private String text;

  public Emaill(Integer number, Integer numberChanel, String name, String text) {
    this.number = number;
    this.numberChanel = numberChanel;
    this.name = name;
    this.text = text;
  }

  public Integer getNumber() {
    return number;
  }

  public void setNumber(Integer number) {
    this.number = number;
  }

  public Integer getNumberChanel() {
    return numberChanel;
  }

  public void setNumberChanel(Integer numberChanel) {
    this.numberChanel = numberChanel;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }
}

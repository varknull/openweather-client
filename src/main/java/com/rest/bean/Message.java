package com.rest.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;


@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class Message {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;

  private String name;

  @OneToOne(cascade = CascadeType.ALL)
  @ElementCollection(targetClass = Temperature.class)
  private Temperature temp;

  @OneToOne(cascade = CascadeType.ALL)
  @ElementCollection(targetClass = MsgDesc.class)
  private MsgDesc desc;

  public Message() {
  }


  public Message(String name, Temperature temp, MsgDesc desc) {
    this.name = name;
    this.temp = temp;
    this.desc = desc;
  }

  @Override
  public String toString() {
    return "Message{" + "name='" + name + '\'' + ", temp=" + temp + '}';
  }

  @JsonProperty("name")
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @JsonProperty("main")
  public Temperature getTemp() {
    return temp;
  }

  public void setTemp(Temperature temperature) {
    this.temp = temperature;
  }


  public MsgDesc getDesc() {
    return desc;
  }


  public void setDesc(MsgDesc desc) {
    this.desc = desc;
  }
}

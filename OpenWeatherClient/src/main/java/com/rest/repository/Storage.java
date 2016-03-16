package com.rest.repository;

import com.rest.bean.Message;
import com.rest.bean.MsgDesc;
import com.rest.bean.Temperature;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Storage {

  private static final Logger log = LoggerFactory.getLogger(Storage.class);

  @Autowired
  private MessageRepository messageRepository;

  public Storage() {
  }

  public boolean write(String city, double temp) {

    messageRepository.save(new Message(city, new Temperature(temp), new MsgDesc("")));
    return true;
  }

  public void fetch() {
    for (Message msg : messageRepository.findAll()) {
      log.info(msg.toString());
    }
  }
}

package com.rest.repository;

import com.rest.bean.Message;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends CrudRepository<Message, String> {

  public List<Message> findByName(String name);
}

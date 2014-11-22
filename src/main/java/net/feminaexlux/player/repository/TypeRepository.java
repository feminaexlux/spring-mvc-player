package net.feminaexlux.player.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.io.Serializable;

public interface TypeRepository<TYPE, ID extends Serializable> extends JpaRepository<TYPE, ID> {

}

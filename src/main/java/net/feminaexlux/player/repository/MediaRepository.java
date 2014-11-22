package net.feminaexlux.player.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.io.Serializable;

public interface MediaRepository<MEDIA, ID extends Serializable> extends JpaRepository<MEDIA, ID> {

}

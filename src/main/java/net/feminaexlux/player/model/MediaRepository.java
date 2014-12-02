package net.feminaexlux.player.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

@Repository
public interface MediaRepository<MEDIA, ID extends Serializable> extends JpaRepository<MEDIA, ID> {

}

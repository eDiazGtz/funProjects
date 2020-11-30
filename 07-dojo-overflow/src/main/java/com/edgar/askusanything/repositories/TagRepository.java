package com.edgar.askusanything.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.edgar.askusanything.models.Tag;

@Repository
public interface TagRepository extends CrudRepository<Tag, Long> {
	List<Tag> findAll();

	Optional<Tag> findByTag(String tag);
	
	Tag findByTagEquals(String tag);
}

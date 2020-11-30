package com.edgar.askusanything.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edgar.askusanything.models.Tag;
import com.edgar.askusanything.repositories.TagRepository;

@Service
public class TagService {
	@Autowired
	private TagRepository tRepo;

	public List<Tag> getAllTags() {
		List<Tag> allTags = this.tRepo.findAll();
		return allTags;
	}

	public Tag createTag(Tag newTag) {
		Tag tag = this.tRepo.save(newTag);
		return tag;
	}

	public Tag findByString(String tag) {
		return this.tRepo.findByTagEquals(tag);
	}

	public Tag findOrCreate(String tag) {
		Tag newTag = this.findByString(tag.toLowerCase().trim());
		if (newTag == null) {
			newTag = new Tag();
			newTag.setTag(tag);
			this.tRepo.save(newTag);
		}
		return newTag;
	}

	public void uniqueTag(List<Tag> allTags, Tag newTag) {
//		for (Tag tag : allTags) {
//			if (tag.getTag() == newTag.getTag()) {
//				return;
//			}
//		}
		allTags.add(newTag);
	}
}

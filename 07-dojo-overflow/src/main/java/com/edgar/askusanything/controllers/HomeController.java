package com.edgar.askusanything.controllers;

import java.util.ArrayList;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.edgar.askusanything.models.Answer;
import com.edgar.askusanything.models.Question;
import com.edgar.askusanything.models.Tag;
import com.edgar.askusanything.services.AnswerService;
import com.edgar.askusanything.services.QuestionService;
import com.edgar.askusanything.services.TagService;

@Controller
@RequestMapping("/questions")
public class HomeController {
	@Autowired
	private AnswerService aService;
	@Autowired
	private QuestionService qService;
	@Autowired
	private TagService tService;
	
	// Dashboard View
	@RequestMapping("")
	public String dashbaord(Model viewModel) {
		viewModel.addAttribute("question", this.qService.getAllQuestions());
		return "dashboard.jsp";
	}

	// New Question
	@RequestMapping("/new")
	public String newQuestion(@ModelAttribute("questions") Question question) {
		return "newQuestion.jsp";
	}

	// New Question Logic
	@PostMapping("/new")
	public String newQuestion(@Valid @ModelAttribute("questions") Question question, BindingResult result, @RequestParam("tag") String tag, RedirectAttributes redirectAttr) {
		ArrayList<String> errors = new ArrayList<String>();
		if (tag.equals("")) {
			errors.add("Tags cannot be empty!");
		}
		if(errors.size() > 0) {
			for(String e: errors) {
				redirectAttr.addFlashAttribute("errors", e);
			}
			return "redirect:/questions/new";
		}
		if (result.hasErrors()) {
			return "newQuestion.jsp";
		} else {
			String[] tagArray = tag.split(", ");
			int tagCount = tagArray.length > 3? 3:tagArray.length;
			
			ArrayList<Tag> tagList = new ArrayList<Tag>();

			for(int i=0; i<tagCount; i++) {
				Tag newTag = this.tService.findOrCreate(tagArray[i]);
				tService.uniqueTag(tagList, newTag);
				tService.createTag(newTag);
			}
			question.setTags(tagList);
			qService.createQuestion(question);
			
			
			return "redirect:/questions";
		}
	}
	
	
	// View Question
	@RequestMapping("/{id}")
	public String viewQuestion(@ModelAttribute("ans") Answer ans, @PathVariable("id") Long id, Model viewModel) {
		viewModel.addAttribute("question", this.qService.getOneQuestion(id));
		return "viewQuestion.jsp";
	}

	// Add Answer to Question
	@PostMapping("/answers")
	public String addAnswer(@Valid @ModelAttribute("ans") Answer ans, BindingResult result, Model viewModel) {		
		if (result.hasErrors()) {
			return "viewQuestion.jsp";
		} else {
			Answer newAnswer = this.aService.createAnswer(ans);
			return "redirect:/questions/" + newAnswer.getQuestion().getId();
		}
	}

}

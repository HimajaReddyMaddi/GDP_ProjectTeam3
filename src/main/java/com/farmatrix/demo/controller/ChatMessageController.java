package com.example.demo.controller;

import java.util.List;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.model.ChatMessage;
import com.example.demo.service.ChatMessageService;

@Controller
public class ChatMessageController {

	@Autowired
	private ChatMessageService service;
	
	@RequestMapping("/addchatmessage")
	public String addChatMessage(@RequestParam String receiver,Model model)
	{
		ChatMessage chatMessage=new ChatMessage();
		chatMessage.setReceiver(receiver);
		
		model.addAttribute("chatmessage",chatMessage);
		return "addchatmessage";
	}

	@RequestMapping(value = "/savechatmessage",method = RequestMethod.POST)
	public String saveChatMessage(HttpServletRequest request, Model model,@ModelAttribute("chatmessage") ChatMessage chatMessage)
	{
		chatMessage.setDatetime(new Date().toString());
		chatMessage.setSender((String)request.getSession().getAttribute("username"));
		
		service.save(chatMessage);
		return "redirect:/home";
	}
	
	@RequestMapping("/viewinbox")
	public String viewInbox(Model model,HttpServletRequest request)
	{
		List<ChatMessage> chatMessages=service.findAllInbox((String)request.getSession().getAttribute("username"));
		model.addAttribute("chatmessages",chatMessages);
		return "viewinbox";
	}
	
	@RequestMapping("/deletechatmessage")
	public String deleteChatMessage(@RequestParam int id,Model model,HttpServletRequest request)
	{
		service.delete(id);
		
		List<ChatMessage> chatMessages=service.findAllInbox((String)request.getSession().getAttribute("username"));
		model.addAttribute("chatmessages",chatMessages);
		return "viewinbox";
	}
}



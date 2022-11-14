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
		
		List<ChatMessage> chatMessages = service.findAllInbox((String) request.getSession().getAttribute("username")); // model.addAttribute("chatmessages",chatMessages);
		System.out.println("In user " + request.getSession().getAttribute("username"));
		List<ChatMessage> chatMessages1 = service.findAllInbox("admin@admin.com");
		List<String> message = new ArrayList<>();
		System.out.println("In 1at method -------------");
		for (ChatMessage bean : chatMessages) {
			System.out.println("In 1at method for1-------------");
			message.add(bean.getMessage());
			System.out.println(message);
		}
		// to fetch the messages from admin
		List<String> message1 = new ArrayList<>();
		for (ChatMessage bean : chatMessages1) {
				message1.add(bean.getMessage());

		}

		
		  List<String> sender1 = new ArrayList<>(); 
		  for (ChatMessage bean :chatMessages) { 
			  System.out.println("In 1at method for3 if-------------");
			  sender1.add(bean.getSender()); 
			  System.out.println(sender1);
			  }
		  List<String> messageFinal = new ArrayList<>();
		
		  for (int i = 0, j = 0; i < message.size() && j < message1.size(); i++, j++) {
		  System.out.println("In for ");
		  if(request.getSession().getAttribute("username").equals("admin@admin.com") ) {
		  messageFinal.add(sender1.get(j) + " : " + message.get(j));
		  System.out.println("In if " + request.getSession().getAttribute("username"));
		  } else
		  { 
			  System.out.println("In 1at method for else-------------");
			  messageFinal.add(request.getSession().getAttribute("username") + " : " +message1.get(j)); // 
				 messageFinal.add("admin : " +message.get(i));
		  }
		  }
		model.addAttribute("messageFinal", messageFinal);
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



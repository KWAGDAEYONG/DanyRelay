package com.example.relay.controller;

import com.example.relay.model.Work;
import com.example.relay.repository.WorkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/work")
public class WorkController {

    @Autowired
    WorkRepository workRepository;

    @GetMapping("/list")
    public String list(Model model){
        List<Work> workList = workRepository.findAll();
        model.addAttribute("list",workList);
        return "/work/list";
    }

    @GetMapping("/{id}")
    public String getWork(@PathVariable Long id, Model model){
        Work work = workRepository.findOne(id);
        model.addAttribute("work",work);
        return "/work/detail";
    }
}

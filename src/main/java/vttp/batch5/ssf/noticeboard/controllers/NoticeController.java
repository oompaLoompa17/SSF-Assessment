package vttp.batch5.ssf.noticeboard.controllers;

import java.util.Random;

// Use this class to write your request handlers

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;
import vttp.batch5.ssf.noticeboard.models.Notice;
import vttp.batch5.ssf.noticeboard.services.NoticeService;

@Controller
@RequestMapping
public class NoticeController {

    @Autowired
    private NoticeService nSvc;

    @GetMapping("/")
    public String landingPage(Model model){
        model.addAttribute("notice", new Notice());
        return "notice";
    }

    @PostMapping("/notice")
    public String reviewNotice(@Valid @ModelAttribute Notice submission, BindingResult br, Model model){       
        if (br.hasErrors())
            return "notice";

        String iDorError = nSvc.postToNoticeServer(submission);

        String[] splitValue = iDorError.split(":");
    
        if (splitValue[0].equalsIgnoreCase("message")){
            model.addAttribute("error", splitValue[1]);
            return "view-3";
        }

        model.addAttribute("response", splitValue[1]);
        return "view-2";
    }

    // @GetMapping("/status")
    // public ModelAndView getHealth() {
    //     ModelAndView mav = new ModelAndView();

    //     try {
    //         checkHealth();

    //         mav.setViewName("healthy");
    //         mav.setStatus(HttpStatusCode.valueOf(200));
    //     } catch (Exception e) {
    //         mav.setViewName("unhealthy");
    //         mav.setStatus(HttpStatusCode.valueOf(503));
    //     }
    //     return mav;
    // }

    // private void checkHealth() throws Exception {
    //     // get random number between 0 and 10
    //     Random random = new Random();
    //     // if random number is between 0 and 5
    //     // throw an exception
    //     if (random.nextInt(10) < 5) {
    //         throw new Exception("Random number is <5");
    //     }
    //     // means there is an exception/error (simulating exception)

    //     // else do nothing
    // }

}

package com.example.demo;

import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

@Controller
public class TastyNastyController {

    @Autowired
    private FoodRepository dishes;

    @Autowired
    private TastyRepository tastyVotes;

    @Autowired
    private NastyRepository nastyVotes;

    @Autowired
    private TastyNastyService foodService;

    @Autowired
    private CloudinaryConfig cloudc;

    @RequestMapping("/")
    public String showIndex(Model model){
        ArrayList<Food> dishList = (ArrayList)dishes.findAll();
        for (Food dish:dishList){
            dish.setLast5minutes(foodService.last5MinuteResult(dish.getId()));
        }
        model.addAttribute("dishes", dishList);
        return "listfood";
    }

    @RequestMapping("/list")
    public String showList(Model model){
        ArrayList<Food> dishList = (ArrayList)dishes.findAll();
        for (Food dish:dishList) {
            dish.setLast5minutes(foodService.last5MinuteResult(dish.getId()));
        }
        model.addAttribute("dishes", dishList);
        return "listfood";
    }

    @RequestMapping("/tastyvote")
    public String addTastyVote(HttpServletRequest request){
        long foodID = new Long(request.getParameter("id"));
        Tasty tastyNastyVote = new Tasty();
        tastyNastyVote.setTheDish(dishes.findById(foodID).get());
        tastyNastyVote.setVotedAt();
        tastyVotes.save(tastyNastyVote);
        return "redirect:/";
    }

    @RequestMapping("/nastyvote")
    public String addNastyVote(HttpServletRequest request){
        long foodID = new Long(request.getParameter("id"));
        Nasty nastyVote = new Nasty();
        nastyVote.setTheDish(dishes.findById(foodID).get());
        nastyVote.setVotedAt();
        nastyVotes.save(nastyVote);
        return "redirect:/";
    }

    @RequestMapping("/showvotes")
    public  @ResponseBody String pizzaVotes(){
        Food pizza = dishes.findById(new Long(1)).get();
        String tastyNasty = pizza.getTastyVotes().size()>=pizza.getNastyVotes().size()?"tasty":"nasty";
        return pizza.getDescription() + " has " + pizza.getTastyVotes().size() + " tasty votes and " + pizza.getNastyVotes().size() + " nasty votes. The balance tilts in favor of: " + tastyNasty;
    }

    @GetMapping("/dish")
    public String addDish(Model model){
        model.addAttribute("theDish", new Food());
        return "addfood";
    }

    @PostMapping("/savefood")
    public String saveFood(@Valid @ModelAttribute("theDish") Food toSave, BindingResult result, Model model, MultipartHttpServletRequest request) throws IOException {
        MultipartFile f = request.getFile("file");
        if( f.isEmpty() && toSave.getImageurl().isEmpty()){
            return "addfood";
        }
        if(toSave.getImageurl().isEmpty()){
            Map uploadResult = cloudc.upload(f.getBytes(), ObjectUtils.asMap("resourcetype", "auto"));
            String uploadURL = (String)uploadResult.get("url");
            String uploadedName = (String)uploadResult.get("public_id");
            String transformedImage = cloudc.createURL(uploadedName);
            System.out.println(transformedImage);
            System.out.println("Uploaded: " + uploadURL);
            System.out.println("Name: " + uploadedName);
            toSave.setImageurl(transformedImage);
        }
        if(result.hasErrors())
        {
            return "addfood";
        }
        dishes.save(toSave);
        return "redirect:/";
    }

}

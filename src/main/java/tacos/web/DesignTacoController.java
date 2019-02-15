package tacos.web;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import tacos.Ingredient;
import tacos.Taco;
import tacos.data.IngredientRepository;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j //automatically generate an SLF4J (Simple Logging Facade for Java)
@Controller
@RequestMapping("/design") // this controller will handle requests whose path begins with /design
@SessionAttributes("order")
public class DesignTacoController {

    private final IngredientRepository ingredientRepository;

    @Autowired
    public DesignTacoController(IngredientRepository ingredientRepository){
        this.ingredientRepository = ingredientRepository;
    }

    @GetMapping //specifies that when an HTTP GET request is received for /design, showDesignForm() will be called to handle the request
    public String showDesignForm(Model model){
        List<Ingredient> ingredients = Arrays.asList(
                new Ingredient("FLTO","Flour Tortilla", Ingredient.Type.WRAP),
                new Ingredient("COTO","Corn Tortilla", Ingredient.Type.WRAP),
                new Ingredient("CRBF","Ground Beef", Ingredient.Type.PROTEIN),
                new Ingredient("CARN","Carnitas", Ingredient.Type.PROTEIN),
                new Ingredient("TMTO","Diced Tomatoes ", Ingredient.Type.VEGGIES),
                new Ingredient("LETC","Lettuce", Ingredient.Type.VEGGIES),
                new Ingredient("CHED","Cheddar", Ingredient.Type.CHEESE),
                new Ingredient("JACK","Monterrey Jack", Ingredient.Type.CHEESE),
                new Ingredient("SLSA","Salsa", Ingredient.Type.SAUCE),
                new Ingredient("SRCR","Sour Cream", Ingredient.Type.SAUCE)
                );

        Ingredient.Type[] types = Ingredient.Type.values();
        for(Ingredient.Type type : types){
            model.addAttribute(type.toString().toLowerCase(),
                    filterByType(ingredients,type));
        }
        model.addAttribute("design",new Taco());
        return "design";
    }

    @PostMapping
    public String processDesign(@Valid Taco design, Errors errors, Model model){
        if (errors.hasErrors()){
            return showDesignForm(model);
        }

        //TODO
        log.info("Processing design: " + design);
        return "redirect:/orders/current";
    }


    private List<Ingredient> filterByType(List<Ingredient>ingredients, Ingredient.Type type) {
        return ingredients.stream().filter(x -> x.getType().equals(type)).collect(Collectors.toList());
    }
}

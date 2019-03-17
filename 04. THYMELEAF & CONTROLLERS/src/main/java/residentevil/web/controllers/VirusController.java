package residentevil.web.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import residentevil.domain.model.binding.VirusAddBindingModel;
import residentevil.domain.model.service.VirusServiceModel;
import residentevil.domain.model.view.CapitalListViewModel;
import residentevil.service.CapitalService;
import residentevil.service.VirusService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/viruses")
public class VirusController extends BaseController {

    private final CapitalService capitalService;
    private final VirusService virusService;
    private final ModelMapper modelMapper;

    @Autowired
    public VirusController(CapitalService capitalService, VirusService virusService, ModelMapper modelMapper) {
        this.capitalService = capitalService;
        this.virusService = virusService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/add")
    public ModelAndView add(
            @ModelAttribute(name = "bindingModel") VirusAddBindingModel bindingModel,
            ModelAndView modelAndView){

        modelAndView.addObject("bindingModel", bindingModel);

        List<CapitalListViewModel> capitals = this.capitalService.findAllCapitals()
                .stream()
                .map(c -> this.modelMapper.map(c, CapitalListViewModel.class))
                .collect(Collectors.toList());

        modelAndView.addObject("capitals", capitals);
        return super.view("add-virus", modelAndView);
    }

    @PostMapping("/add")
    public ModelAndView addConfirm(
            @Valid
            @ModelAttribute(name = "bindingModel") VirusAddBindingModel bindingModel,
            BindingResult bindingResult,
            ModelAndView modelAndView){

        if(bindingResult.hasErrors()) {
            modelAndView.addObject("bindingModel", bindingModel);
            return super.view("add-virus", modelAndView);
        }

        this.virusService.addVirus(this.modelMapper.map(bindingModel, VirusServiceModel.class));

        return super.redirect("/");
    }

    //Alternative way to bind dates from form...not working for now
//    @InitBinder
//    private void initBinder(WebDataBinder binder){
//        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
//        simpleDateFormat.setLenient(false);
//        binder.registerCustomEditor(LocalDate.class, new CustomDateEditor(simpleDateFormat, true));
//    }
}

package realestate.web.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import realestate.common.Constants;
import realestate.domain.models.view.OfferViewModel;
import realestate.service.OfferService;
import realestate.util.HtmlReader;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class HomeController {

    private static final String INDEX_HTML_FILE_PATH = Constants.PATH_TO_FILES + "index.html";

    private final OfferService offerService;
    private final HtmlReader reader;
    private final ModelMapper modelMapper;

    @Autowired
    public HomeController(OfferService offerService, HtmlReader htmlReader, ModelMapper modelMapper) {
        this.offerService = offerService;
        this.reader = htmlReader;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/")
    @ResponseBody
    public String index() throws IOException {
        return this.prepareHtml();
    }

    private String prepareHtml() throws IOException {
        List<OfferViewModel> offers = this.offerService.findAllOffers()
                .stream()
                .map(o -> this.modelMapper.map(o, OfferViewModel.class))
                .collect(Collectors.toList());

        StringBuilder offersHtml = new StringBuilder();

        if (offers.size() == 0) {
            offersHtml
                    .append("<div class=\"apartment\">")
                    .append("There aren't any offers!")
                    .append("</div>");

            return this.reader
                    .readHtmlFile(INDEX_HTML_FILE_PATH)
                    .replace("{{offers}}", offersHtml.toString().trim());
        }

        for (OfferViewModel offer : offers) {
            offersHtml
                    .append("<div class=\"apartment\">")
                    .append(String.format("<p>Rent: %.2f</p>", offer.getApartmentRent()))
                    .append(String.format("<p>Type: %s</p>", offer.getApartmentType()))
                    .append(String.format("<p>Commission: %.2f</p>", offer.getAgencyCommission()))
                    .append("</div>")
                    .append(System.lineSeparator());
        }

        return this.reader
                .readHtmlFile(INDEX_HTML_FILE_PATH)
                .replace("{{offers}}", offersHtml.toString().trim());
    }
}

package io.pivotal.microservices.services.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.logging.Logger;

/**
 * Client controller, fetches Product info from the microservice via
 * {@link WebProductsService}.
 */
@Controller
public class WebProductsController {
    @Autowired
    protected WebProductsService productsService;

    protected Logger logger = Logger.getLogger(WebProductsController.class.getName());

    public WebProductsController(WebProductsService productsService) {
        this.productsService = productsService;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.setAllowedFields("productNumber", "searchText");
    }

//    @RequestMapping("/index")
//    public String goHome() {
//        return "index";
//    }

    @RequestMapping("/menu")
    public String displayMenu(Model model){
        model.addAttribute(new Account());
        return "menu";
    }

    @RequestMapping("/products")
    public String displayProductMenu(Model model) {
        List<Product> products = productsService.allProducts();
        model.addAttribute("products",products);
        return "products";
    }

    @RequestMapping("/products/deleteProduct/{productNumber}")
    public String deleteProduct(@PathVariable("productNumber") String productNumber) {
        productsService.deleteProduct(productNumber);
        return "redirect:/menu";
    }

    @RequestMapping("/products/{productNumber}")
    public String byNumber(Model model, @PathVariable("productNumber") String productNumber) {

        logger.info("web-service byNumber() invoked: " + productNumber);

        Product product = productsService.findByNumber(productNumber);

        if (product == null) { // no such product
            model.addAttribute("number", productNumber);
            return "product";
        }

        logger.info("web-service byNumber() found: " + product);
        model.addAttribute("product", product);
        return "product";
    }

    @RequestMapping("/products/name/{text}")
    public String nameSearch(Model model, @PathVariable("text") String name) {
        logger.info("web-service byName() invoked: " + name);

        List<Product> products = productsService.byNameContains(name);
        logger.info("web-service byName() found: " + products);
        model.addAttribute("search", name);
        if (products != null)
            model.addAttribute("products", products);
        return "products";
    }

    @RequestMapping(value = "/products/search", method = RequestMethod.GET)
    public String searchForm(Model model) {
        model.addAttribute("searchCriteria", new SearchCriteria());
        return "productSearch";
    }

    @RequestMapping(value = "/products/dosearch")
    public String doSearch(Model model, SearchCriteria criteria, BindingResult result) {
        logger.info("web-service search() invoked: " + criteria);

        criteria.validateProduct(result);

        if (result.hasErrors())
            return "productSearch";

        String productNumber = criteria.getProductNumber();
        if (StringUtils.hasText(productNumber)) {
            return byNumber(model, productNumber);
        } else {
            String searchText = criteria.getSearchText();
            return nameSearch(model, searchText);
        }
    }
}


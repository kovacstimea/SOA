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
 * Client controller, fetches Order info from the microservice via
 * {@link WebOrdersService}.
 */
@Controller
public class WebOrdersController {
    @Autowired
    protected WebOrdersService ordersService;

    @Autowired
    protected WebAuthenticationService authenticationService;

    protected Logger logger = Logger.getLogger(WebOrdersController.class.getName());

    public WebOrdersController(WebOrdersService ordersService, WebAuthenticationService authenticationService) {
        this.ordersService = ordersService;
        this.authenticationService = authenticationService;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.setAllowedFields("orderNumber", "searchText");
    }

    @RequestMapping("/index")
    public String goHome() {
        if (authenticationService.getLogged() == true)
            return "index";
        else
            return "login";
    }

    @RequestMapping("/orders")
    public String displayOrderMenu(Model model) {
        List<Order> orders = ordersService.allOrders();
        model.addAttribute("orders",orders);
        return "orders";
    }

    @RequestMapping("/orders/deleteOrder/{orderNumber}")
    public String deleteOrder(@PathVariable("orderNumber") String orderNumber) {
        ordersService.deleteOrder(orderNumber);
        return "redirect:/index";
    }

    @RequestMapping("/orders/{orderNumber}")
    public String byNumber(Model model, @PathVariable("orderNumber") String orderNumber) {

        logger.info("web-service byNumber() invoked: " + orderNumber);

        Order order = ordersService.findByNumber(orderNumber);

        if (order == null) { // no such order
            model.addAttribute("number", orderNumber);
            return "order";
        }

        logger.info("web-service byNumber() found: " + order);
        model.addAttribute("order", order);
        return "order";
    }

    @RequestMapping("/orders/client/{text}")
    public String nameSearch(Model model, @PathVariable("text") String name) {
        logger.info("web-service byName() invoked: " + name);

        List<Order> orders = ordersService.byNameContains(name);
        logger.info("web-service byName() found: " + orders);
        model.addAttribute("search", name);
        if (orders != null)
            model.addAttribute("orders", orders);
        return "orders";
    }

    @RequestMapping(value = "/orders/search", method = RequestMethod.GET)
    public String searchForm(Model model) {
        model.addAttribute("searchCriteria", new SearchCriteria());
        return "orderSearch";
    }

    @RequestMapping(value = "/orders/dosearch")
    public String doSearch(Model model, SearchCriteria criteria, BindingResult result) {
        logger.info("web-service search() invoked: " + criteria);

        criteria.validateOrder(result);

        if (result.hasErrors())
            return "orderSearch";

        String orderNumber = criteria.getOrderNumber();
        if (StringUtils.hasText(orderNumber)) {
            return byNumber(model, orderNumber);
        } else {
            String searchText = criteria.getSearchText();
            return nameSearch(model, searchText);
        }
    }
}


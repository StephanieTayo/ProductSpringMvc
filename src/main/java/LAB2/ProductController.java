package LAB2;

import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;


@Controller
@Validated
public class ProductController {
    @RequestMapping("/add")
    public ModelAndView add( HttpSession session, @ModelAttribute("product") Product product) {
        Map<String, Object> params = new HashMap<>();
        if (product != null) {
            Map<String, Product> productList = (Map<String, Product>) session.getAttribute("productList");
            if (productList == null) {
                productList = new HashMap<String, Product>();
                session.setAttribute("productList", productList);
            }
            productList.put(product.getProductNumber(), product);
            params.put("productList", productList.values());
        }
        return new ModelAndView("products", params);
    }

    @GetMapping("/products")
    public ModelAndView init(HttpSession session) {
        Map<String, Product> productList = (Map<String, Product>) session.getAttribute("productList");
        if (productList == null) {
            productList = new HashMap<String, Product>();
            session.setAttribute("productList", productList);
        }
        Map<String, Object> params = new HashMap<>();
        params.put("productList", productList.values());
        return new ModelAndView("products", params);
    }

    @PostMapping("/addproduct")
    public ModelAndView addproduct(HttpSession session) {
        Map<String, Object> params = new HashMap<>();
        params.put("product", new Product());
        return new ModelAndView("addproduct", params);
    }

    @PostMapping("/removeproduct")
    public ModelAndView removeproduct(@RequestParam("productNumber") String productNumber, HttpSession session) {
        Map<String, Object> params = new HashMap<>();
        if (productNumber != null) {
            Map<String, Product> productList = (Map<String, Product>) session.getAttribute("productList");
            if (productList == null) {
                productList = new HashMap<String, Product>();
                session.setAttribute("productList", productList);
            }
            productList.remove(productNumber);
            params.put("productList", productList.values());
        }
        return new ModelAndView("products", params);
    }
}
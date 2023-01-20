package controller;

import model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

public class ProductsController {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @GetMapping("/products")
    public String index(Model model) {

        String sql = "SELECT * FROM Products";
        List<Products> products = jdbcTemplate.query(sql,
                BeanPropertyRowMapper.newInstance(Products.class));

        model.addAttribute("products", products);
        return "products/index";
    }

    @GetMapping("/products/get/{id}")
    public String get(@PathVariable int id, Model model) {

        String sql = "SELECT * FROM Products WHERE Product_ID = " + id;
        List<Products> products = jdbcTemplate.query(sql,
                BeanPropertyRowMapper.newInstance(Products.class));

        model.addAttribute("item", products.get(0));
        return "products/details";
    }

    @GetMapping("/products/add")
    public String add(Model model)
    {
        Products product = new Products();
        model.addAttribute("product", product);

        return "products/add";
    }

    @RequestMapping(value = "/products/add", method = RequestMethod.POST)
    public ModelAndView add(@ModelAttribute("product") Products product, Model model)
    {
        model.addAttribute("item", product);
        String sql = "INSERT INTO Products (Product_Type, Calories_Count) VALUES(?, ?);";

        jdbcTemplate.update(sql, product.getProductType(), product.getCaloriesCount());

        return new ModelAndView("redirect:/products");
    }

    @GetMapping("/products/edit/{id}")
    public String edit(@PathVariable int id, Model model)
    {
        String sql = "SELECT * FROM Products WHERE Product_ID = " + id;
        List<Products> products = jdbcTemplate.query(sql,
                BeanPropertyRowMapper.newInstance(Products.class));

        model.addAttribute("product", products.get(0));

        return "products/edit";
    }

    @RequestMapping(value = "/products/edit", method = RequestMethod.POST)
    public ModelAndView edit(@ModelAttribute("product") Products product, Model model)
    {
        model.addAttribute("item", product);
        String sql = "UPDATE Products  SET Product_Type = ?, Calories_Count = ? WHERE Product_ID = ?;";

        jdbcTemplate.update(sql, product.getProductType(), product.getCaloriesCount(), product.getProductId());

        return new ModelAndView("redirect:/products");
    }

    @GetMapping("/products/delete/{id}")
    public ModelAndView delete(@PathVariable int id, Model model)
    {
        String sql = "SELECT * FROM products WHERE Product_ID = " + id;
        List<Products> products = jdbcTemplate.query(sql,
                BeanPropertyRowMapper.newInstance(Products.class));

        if(products.size() > 0)
        {
            String sqlDelete = "DELETE FROM Products WHERE Product_ID = ?";
            jdbcTemplate.update(sqlDelete, id);

            return new ModelAndView("redirect:/products");
        } else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "entity not found"
            );
        }
    }
}
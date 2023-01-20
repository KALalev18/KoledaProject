package controller;

import ch.qos.logback.core.model.Model;
import model.Calories;
import model.Menus;
import model.Products;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;

import java.awt.*;
import java.util.List;

public class CaloriesController {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @GetMapping("/calculations/list")
    public String list(Model model) {

        String sql = "SELECT * FROM Calculations";
        List<Calories> calculations = jdbcTemplate.query(sql,
                BeanPropertyRowMapper.newInstance(Calories.class));

        //model.addAttribute("calculations", calculations);
        return "calculations/list";
    }

    @GetMapping("/calculations/get/{id}")
    public String get(@PathVariable int id, Model model) {

        String sql = "SELECT * FROM CalculationsCalories WHERE Calculation_ID = " + id;
        List<Calories> calculations = jdbcTemplate.query(sql,
                BeanPropertyRowMapper.newInstance(Calories.class));
        String sql2 = "SELECT * FROM Menus WHERE Menu_ID = " + id;
        List<Menus> menus = jdbcTemplate.query(sql2,
                BeanPropertyRowMapper.newInstance(Menus.class));

        String sql3 = "SELECT Products.Product_ID, Products.Product_Type, Products.Calories_Count FROM Products INNER JOIN MenusProducts ON MenusProducts.Product_ID = Products.Product_ID WHERE Menu_ID = " + id;
        List<Products> products = jdbcTemplate.query(sql3,
                BeanPropertyRowMapper.newInstance(Products.class));
        menus.get(0).setProductsObj(products);

        //model.addAttribute("products", products);
        //model.addAttribute("totalCalories",  menus.get(0).getMenuCalories());
        //model.addAttribute("item", calculations.get(0));
        return "calculations/details";
    }

    @GetMapping("/calculations") //the add page will act as an index!
    public String add(Model model)
    {
        Calories calculation = new Calories();
        String sql = "SELECT * FROM Menus";
        List<Menus> menus = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Menus.class));

        //model.addAttribute("allMenus", menus);
        //model.addAttribute("calculation", calculation);

        return "calculations/index";
    }
    @RequestMapping(value = "/calculations/add", method = RequestMethod.POST)
    public ModelAndView add(@ModelAttribute("calculation") Calories calculation, Model model)
    {
        //model.addAttribute("item", calculation);
        String sql = "INSERT INTO Calculations (Height, Weight, Age, CaloriesToTake, ExtraTakenCalories, MenuId) VALUES(?, ?, ?, ?, ?, ?);";

        int menuId = calculation.getMenuId();

        String sql2 = "SELECT * FROM Menus WHERE Menu_ID = " + menuId;
        List<Menus> menus = jdbcTemplate.query(sql2,
                BeanPropertyRowMapper.newInstance(Menus.class));

        String sql3 = "SELECT Products.Product_ID, Products.Product_Type, Products.Calories_Count FROM Products INNER JOIN MenusProducts ON MenusProducts.Product_ID = Products.Product_ID WHERE Menu_ID = " + menuId;
        List<Products> products = jdbcTemplate.query(sql3,
                BeanPropertyRowMapper.newInstance(Products.class));
        menus.get(0).setProductsObj(products);

        Integer totalCalories = menus.get(0).getMenuCalories();

        Integer requiredCalories = (int) (calculation.getWeight()*26.4);

        Integer extra = (requiredCalories >= totalCalories ? 0 : (totalCalories - requiredCalories));

        jdbcTemplate.update(sql, calculation.getHeight(), calculation.getWeight(), calculation.getAge(), totalCalories, extra, menuId);

        return new ModelAndView("redirect:/calculations/list");
    }

    @GetMapping("/calculations/delete/{id}")
    public ModelAndView delete(@PathVariable int id, Model model)
    {
        String sql = "SELECT * FROM Calculations WHERE Calculation_ID = " + id;
        List<Calories> calculations = jdbcTemplate.query(sql,
                BeanPropertyRowMapper.newInstance(Calories.class));

        if(calculations.size() > 0)
        {
            String sqlDelete = "DELETE FROM Calculations WHERE Calculation_ID = ?";
            jdbcTemplate.update(sqlDelete, id);

            return new ModelAndView("redirect:/calculations/list");
        } else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "entity not found"
            );
        }
    }
}

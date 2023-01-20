package controller;

import model.Menus;
import model.Products;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MenusController {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    @GetMapping("/menus")
    public String index(Model model) {

        String sql = "SELECT * FROM Menus";
        List<Menus> menus = jdbcTemplate.query(sql,
                BeanPropertyRowMapper.newInstance(Menus.class));
        for (Menus menu : menus) {
            String sql2 = "SELECT Products.Product_ID, Products.Product_Type, Products.Calories_Count FROM Products INNER JOIN MenusProducts ON MenusProducts.Product_ID = Products.Product_ID WHERE Menu_ID = " + menu.getMenuId();
            List<Products> products = jdbcTemplate.query(sql2,
                    BeanPropertyRowMapper.newInstance(Products.class));
            menu.setProductsObj(products);
        }
        model.addAttribute("menus", menus);
        return "menus/index";
    }

    @GetMapping("/menus/get/{id}")
    public String get(@PathVariable int id, Model model) {

        String sql = "SELECT * FROM Menus WHERE Menu_ID = " + id;
        List<Menus> menus = jdbcTemplate.query(sql,
                BeanPropertyRowMapper.newInstance(Menus.class));

        String sql2 = "SELECT Products.Product_ID, Products.Product_Type, Products.Calories_Count FROM Products INNER JOIN MenusProducts ON MenusProducts.Product_ID = Products.Product_ID WHERE Menu_ID = " + id;
        List<Products> products = jdbcTemplate.query(sql2,
                BeanPropertyRowMapper.newInstance(Products.class));
        model.addAttribute("products", products);
        model.addAttribute("item", menus.get(0));
        return "menus/details";
    }

    @GetMapping("/menus/add")
    public String add(Model model)
    {
        Menus menu = new Menus();
        String sql = "SELECT * FROM Products";
        List<Products> products = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Products.class));

        model.addAttribute("allProducts", products);

        model.addAttribute("menu", menu);

        return "menus/add";
    }

    @RequestMapping(value = "/menus/add", method = RequestMethod.POST)
    public ModelAndView add(@ModelAttribute("menu") Menus menu, Model model)
    {
        model.addAttribute("item", menu);
        String sql = "INSERT INTO Menus (Menu_Title) VALUES(:Menu_Title);";

        GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();
        Map<String, Object> params = new HashMap<>();
        params.put("Menu_Title", menu.getMenuTitle());

        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(params), generatedKeyHolder);
        Integer menuId = generatedKeyHolder.getKey().intValue();

        for (Integer productId : menu.getProducts()) {
            String sql3 = "INSERT INTO MenusProducts (Menu_ID, Product_ID) VALUES(?, ?);";

            jdbcTemplate.update(sql3, menuId, productId);
        }

        return new ModelAndView("redirect:/menus");
    }

    @GetMapping("/menus/edit/{id}")
    public String edit(@PathVariable int id, Model model)
    {
        String sql = "SELECT * FROM Menus WHERE Menu_ID = " + id;
        List<Menus> menus = jdbcTemplate.query(sql,
                BeanPropertyRowMapper.newInstance(Menus.class));
        String sql2 = "SELECT Products.Product_ID FROM Products INNER JOIN MenusProducts ON MenusProducts.Product_ID = Products.Product_ID WHERE Menu_ID = " + id;
        List<Integer> menuProducts = jdbcTemplate.queryForList(sql2,Integer.class);
        menus.get(0).setProducts(menuProducts);

        String sql3 = "SELECT * FROM Products";
        List<Products> products = jdbcTemplate.query(sql3, BeanPropertyRowMapper.newInstance(Products.class));

        model.addAttribute("allProducts", products);
        model.addAttribute("menu", menus.get(0));

        return "menus/edit";
    }

    @RequestMapping(value = "/menus/edit", method = RequestMethod.POST)
    public ModelAndView edit(@ModelAttribute("menu") Menus menu, Model model)
    {
        model.addAttribute("item", menu);
        String sql = "UPDATE Menus  SET Menu_Title = ? WHERE Menu_ID = ?;";

        jdbcTemplate.update(sql, menu.getMenuTitle(), menu.getMenuId());

        String sql2 = "DELETE FROM MenusProducts WHERE Menu_ID = ?";
        jdbcTemplate.update(sql2, menu.getMenuId());

        for (Integer productId : menu.getProducts()) {
            String sql3 = "INSERT INTO MenusProducts (Menu_ID, Product_ID) VALUES(?, ?);";

            jdbcTemplate.update(sql3, menu.getMenuId(), productId);
        }

        return new ModelAndView("redirect:/menus");
    }

    @GetMapping("/menus/delete/{id}")
    public ModelAndView delete(@PathVariable int id, Model model)
    {
        String sql = "SELECT * FROM Menus WHERE Menu_ID = " + id;
        List<Menus> menus = jdbcTemplate.query(sql,
                BeanPropertyRowMapper.newInstance(Menus.class));

        if(menus.size() > 0)
        {
            String sqlDelete = "DELETE FROM Menus WHERE Menu_ID = ?";
            jdbcTemplate.update(sqlDelete, id);

            return new ModelAndView("redirect:/menus");
        } else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "entity not found"
            );
        }
    }
}
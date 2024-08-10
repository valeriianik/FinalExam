package org.example.finalexam.web;

import lombok.AllArgsConstructor;
import org.example.finalexam.entities.Salesman;
import org.example.finalexam.repositories.SalesmanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

//Name: Valeriia Nikitina
//ID: 300374609
//GitHub link:

@Controller
@AllArgsConstructor
public class SalesmanController {

    @Autowired
    private final SalesmanRepository salesmanRepository;

    @GetMapping(path = "/index")
    public String sales(Model model) {
        List<Salesman> salesmanList = salesmanRepository.findAll();
        model.addAttribute("salesmanList", salesmanList);
        model.addAttribute("salesSummary", calculateSalesSummary(salesmanList));
        model.addAttribute("salesman", new Salesman());
        return "sales";
    }

    @PostMapping("/saveSale")
    public String saveSale(@ModelAttribute Salesman salesman) {
        salesmanRepository.save(salesman);
        return "redirect:/index";
    }

    @GetMapping("/editSale/{id}")
    public String editSale(@PathVariable("id") Long id, Model model) {
        Salesman salesman = salesmanRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid transaction ID: " + id));
        model.addAttribute("salesman", salesman);
        return "editSale";
    }


    @GetMapping("/deleteSale/{id}")
    public String deleteSale(@PathVariable("id") Long id) {
        salesmanRepository.deleteById(id);
        return "redirect:/index";
    }

    private Map<String, Map<String, Double>> calculateSalesSummary(List<Salesman> salesmanList) {
        Map<String, Map<String, Double>> summary = new HashMap<>();

        for (Salesman sale : salesmanList) {
            String name = sale.getName();
            String item = sale.getItem();
            double amount = sale.getAmount();

            summary.putIfAbsent(name, new HashMap<>());
            Map<String, Double> itemSales = summary.get(name);

            itemSales.put(item, itemSales.getOrDefault(item, 0.0) + amount);
        }

        return summary;
    }
}

package org.example.finalexam.web;

import org.example.finalexam.entities.Salesman;
import org.example.finalexam.repositories.SalesmanRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class SalesmanControllerTest {

    @Mock
    private SalesmanRepository salesmanRepository;

    @Mock
    private Model model;

    @InjectMocks
    private SalesmanController salesmanController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void sales() {
        List<Salesman> salesmanList = new ArrayList<>();
        salesmanList.add(new Salesman(1L, 500.0, new Date(), "Refrigerator", "Valeriia Nikitina"));
        when(salesmanRepository.findAll()).thenReturn(salesmanList);

        String viewName = salesmanController.sales(model);

        verify(model, times(1)).addAttribute(eq("salesmanList"), eq(salesmanList));
        verify(model, times(1)).addAttribute(eq("salesSummary"), anyMap());
        verify(model, times(1)).addAttribute(eq("salesman"), any(Salesman.class));
        assertEquals("sales", viewName);
    }

    @Test
    void saveSale() {
        Salesman salesman = new Salesman(1L, 100.0, new Date(), "Refrigerator", "John Doe");

        String viewName = salesmanController.saveSale(salesman);

        verify(salesmanRepository, times(1)).save(salesman);
        assertEquals("redirect:/index", viewName);
    }

    @Test
    void deleteSale() {
        Long salesmanId = 1L;

        String viewName = salesmanController.deleteSale(salesmanId);

        verify(salesmanRepository, times(1)).deleteById(salesmanId);
        assertEquals("redirect:/index", viewName);
    }
}

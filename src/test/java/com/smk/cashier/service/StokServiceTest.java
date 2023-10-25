package com.smk.cashier.service;

import com.smk.cashier.dao.StokDao;
import com.smk.cashier.model.Stok;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class StokServiceTest {

    @Test
    @Order(2)
    void getStokList() {
        List<Stok> barangList = StokService.getInstance().getStokList();
        assertEquals(barangList.size(),3);
    }

    @Test
    @Order(3)
    void findByKode() {
        List<Stok> resultList = StokService.getInstance().findByKode("LP002");
        assertEquals(resultList.size(),1);
    }

    @Test
    @Order(1)
    void addStok() {
        Stok laptop = new Stok();
        laptop.setId(1);
        laptop.setKodeBarang("LP001");
        laptop.setStokBarang(3);
        StokService.getInstance().addStok(laptop);

        Stok mouse = new Stok();
        mouse.setId(2);
        mouse.setKodeBarang("M0001");
        mouse.setStokBarang(4);
        StokService.getInstance().addStok(mouse);

        Stok laptopGaming = new Stok();
        laptop.setId(3);
        laptop.setKodeBarang("LP002");
        laptop.setStokBarang(6);
        StokService.getInstance().addStok(laptopGaming);

    }

    @Test
    @Order(4)
    void saveStokDatabase() {
        StokDao stokDao = new StokDao();
        Stok laptop = new Stok();
        laptop.setKodeBarang("LP001");
        laptop.setStokBarang(20);
        laptop.setDateCreated(new Date());
        laptop.setLastModified(new Date());
        stokDao.save(laptop);

        Stok mouse = new Stok();
        mouse.setKodeBarang("M0001");
        mouse.setStokBarang(100);
        mouse.setDateCreated(new Date());
        mouse.setLastModified(new Date());
        stokDao.save(mouse);

        Stok laptopGaming = new Stok();
        laptopGaming.setKodeBarang("LP001");
        laptopGaming.setStokBarang(15);
        laptopGaming.setDateCreated(new Date());
        laptopGaming.setLastModified(new Date());
        stokDao.save(laptopGaming);
    }

    @Test
    @Order(5)
    void getStokDatabase(){
        StokDao stokDao = new StokDao();
        Optional<Stok> stok1 = stokDao.get(1);
        stok1.ifPresent(new Consumer<Stok>() {
            @Override
            public void accept(Stok stok) {
                assertEquals(stok.getKodeBarang(), "LP001");
                assertEquals(stok.getStokBarang(), 20);
            }
        });

        Optional<Stok> stok2 = stokDao.get(2);
        stok2.ifPresent(new Consumer<Stok>() {
            @Override
            public void accept(Stok stok) {
                assertEquals(stok.getKodeBarang(), "M0001");
                assertEquals(stok.getStokBarang(), 100);
            }
        });

        Optional<Stok> stok3 = stokDao.get(3);
        stok3.ifPresent(new Consumer<Stok>() {
            @Override
            public void accept(Stok stok) {
                assertEquals(stok.getKodeBarang(), "LP002");
                assertEquals(stok.getStokBarang(), 15);
            }
        });
    }
}
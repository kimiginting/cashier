package com.smk.cashier.service;

import com.smk.cashier.model.Stok;

import java.io.*;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

public class StokService {
    FileReader StokServiceReader;
    FileWriter StokServiceWriter;

    List<Stok> stokList = new LinkedList<>();
    private static StokService StokService = null;
    private StokService() {
        try {
            StokServiceWriter = new FileWriter("stok.txt");
            StokServiceReader = new FileReader("stok.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static synchronized StokService getInstance(){
        if(StokService == null){
            StokService = new StokService();
        }
        return StokService;
    }

    private void readFile(){
        BufferedReader bufferedReader = new BufferedReader(StokServiceReader);
        List<String> stringList = bufferedReader.lines().toList();
        stokList = new LinkedList<>();
        for (String string: stringList){
            stokList.add(parsingLineToStok(string));
        }
    }

    private void writeFile(){
        try {
            StokServiceWriter = new FileWriter("stok.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
        BufferedWriter bufferedWriter = new BufferedWriter(StokServiceWriter);
        for (int i = 0; i < stokList.size(); i++) {
            Stok stok = stokList.get(i);
            StringBuilder sb = new StringBuilder();
            sb.append(stok.getId());
            sb.append("|");
            sb.append(stok.getKodeBarang());
            sb.append("|");
            sb.append(stok.getStokBarang());
            sb.append("|");
            try {
                bufferedWriter.write(sb.toString());
                if(i < stokList.size() - 1){
                    bufferedWriter.newLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Stok parsingLineToStok(String string){
        StringTokenizer st = new StringTokenizer(string, "|");
        int id = 0;
        Stok stok = new Stok();
        while (st.hasMoreElements()){
            if (id == 0){
                stok.setId(Integer.parseInt(st.nextToken()));
            } else if (id == 1){
                stok.setKodeBarang(st.nextToken());
            } else if (id == 2) {
                stok.setStokBarang(Integer.parseInt(st.nextToken()));
            }
            id ++;
        }
        return stok;
    }

    public List<Stok> getStokList(){
        readFile();
        return stokList;
    }

    public void addStok(Stok barang){
        stokList.add(barang);
        writeFile();
    }
    public List<Stok>
    findByKode(String name){
        List<Stok>
                resultList = stokList.stream().filter(stok -> stok.getKodeBarang().equals(name)).toList();
        return resultList;
    }
}

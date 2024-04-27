package com.ratnakar.project.SpringBatchApp.writer;

import com.ratnakar.project.SpringBatchApp.model.StudentCsv;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
/*
public class FirstItemWriter implements ItemWriter<Long> {
    @Override
    public void write(List<? extends Long> items) throws Exception {
        System.out.println("Inside Item Writer");
        items.stream().forEach(System.out::println);
    }

}
*/

public class FirstItemWriter implements ItemWriter<StudentCsv> {
    @Override
    public void write(List<? extends StudentCsv> items) throws Exception {
        System.out.println("Inside Item Writer");
        items.stream().forEach(System.out::println);
    }

}

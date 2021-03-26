package com.techprimers.springbatchexample1.config;

import com.techprimers.springbatchexample1.batch.ListListProcessor;
import com.techprimers.springbatchexample1.batch.ListObjectWriter;
import com.techprimers.springbatchexample1.model.EmailChanel;
import com.techprimers.springbatchexample1.model.Emaill;
import com.techprimers.springbatchexample1.service.EmailCreateService;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Это пример с ютуб канала SpringDeveloper. Создаем одни Job с одним Step
 */
@Configuration
@EnableBatchProcessing
public class SpringDeveloperBatchConfig {

  private Random random;
  private JobBuilderFactory jobBuilderFactory;
  private StepBuilderFactory stepBuilderFactory;

//  @Autowired
//  EmailCreateService emailCreateService;

  public SpringDeveloperBatchConfig(JobBuilderFactory jobBuilderFactory,
                                    StepBuilderFactory stepBuilderFactory) {
    this.random = new Random();
    this.jobBuilderFactory = jobBuilderFactory;
    this.stepBuilderFactory = stepBuilderFactory;
  }

  @Bean
  public Job jobDeveloper() {
    return jobBuilderFactory.get("jobDeveloper")
        .start(step())
        .incrementer(new RunIdIncrementer())
        .build();
  }

  @Bean
  public Step step() {
    return stepBuilderFactory.get("step")
        .<Integer, Integer>chunk(3)
        .reader(itemReaderDeveloper())
        .writer(itemWriterDeveloper())
        .build();
  }


  @Bean
  @StepScope
  public ListItemReader<Integer> itemReaderDeveloper() {
    List<Integer> items = new LinkedList<>();
    for (int i = 0; i < random.nextInt(100); i++) {
      items.add(i);
    }
    System.out.println("items.size = " + items.size());
    return new ListItemReader<>(items);
  }

  @Bean
  public ItemWriter<Integer> itemWriterDeveloper() {
    return items -> {
      for (Integer item: items) {
        int nextInt = random.nextInt(1000);
        Thread.sleep(nextInt);
        if (nextInt % 57 == 0) {
          throw new Exception("Boom!");
        }
        System.out.println("item = " + item);
      }
    };
  }

}

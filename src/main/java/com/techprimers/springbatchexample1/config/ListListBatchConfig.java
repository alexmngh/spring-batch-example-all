package com.techprimers.springbatchexample1.config;

import com.techprimers.springbatchexample1.batch.ListItemProcessor;
import com.techprimers.springbatchexample1.batch.ListListItemReader;
import com.techprimers.springbatchexample1.batch.ListListProcessor;
import com.techprimers.springbatchexample1.batch.ListWriter;
import com.techprimers.springbatchexample1.batch.StudentItemProcessor;
import com.techprimers.springbatchexample1.model.EmailChanel;
import com.techprimers.springbatchexample1.model.Emaill;
import com.techprimers.springbatchexample1.model.Marksheet;
import com.techprimers.springbatchexample1.model.Student;
import com.techprimers.springbatchexample1.model.User;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.file.transform.PassThroughLineAggregator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;

@Configuration
@EnableBatchProcessing
public class ListListBatchConfig {


  @Bean
  public Job jobListListBath(JobBuilderFactory jobBuilderFactory,
                         StepBuilderFactory stepBuilderFactory,
                         ItemReader<Student> itemReaderListBath,
                         ItemProcessor<Student, Marksheet> processorListBath,
                         ItemWriter<Marksheet> itemWriterListListBath
  ) {

    Step step = stepBuilderFactory.get("List-load")
        .<Student, Marksheet>chunk(3)
        .reader(itemReaderListBath)
        .processor(processorListBath)
        .writer(itemWriterListListBath)
        .build();


    return jobBuilderFactory.get("L-Load")
        .incrementer(new RunIdIncrementer())
        .start(step)
        .build();
  }


  @Bean
  public ItemReader<Emaill> itemReaderListList() {

    return new ListListItemReader();
  }

  @Bean
  public ItemProcessor<Emaill, EmailChanel> processorListList() {
    return new ListListProcessor();
  }

  @Bean
  public ItemWriter<Marksheet> itemWriterListListBath() {
    return new ListWriter();
  }

}

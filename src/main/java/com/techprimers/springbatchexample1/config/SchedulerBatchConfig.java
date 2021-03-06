package com.techprimers.springbatchexample1.config;

import com.techprimers.springbatchexample1.batch.StudentItemProcessor;
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
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableBatchProcessing
@EnableScheduling
public class SchedulerBatchConfig {


  @Bean
  public Job jobList(JobBuilderFactory jobBuilderFactory,
                     StepBuilderFactory stepBuilderFactory,
                     ItemReader<Student> itemReaderList,
                     ItemProcessor<Student, Marksheet> processorList,
                     ItemWriter<Marksheet> itemWriterList
  ) {

    Step step = stepBuilderFactory.get("M-file-load")
        .<Student, Marksheet>chunk(100)
        .reader(itemReaderList)
        .processor(processorList)
        .writer(itemWriterList)
        .build();


    return jobBuilderFactory.get("M-Load")
        .incrementer(new RunIdIncrementer())
        .start(step)
        .build();
  }


  @Bean
  public ItemReader<Student> itemReaderList() {
    FlatFileItemReader<Student> reader = new FlatFileItemReader<Student>();
    reader.setResource(new FileSystemResource("src/main/resources/student.csv"));
    reader.setLineMapper(new DefaultLineMapper<Student>() {{
      setLineTokenizer(new DelimitedLineTokenizer() {{
        setNames(new String[] {"stdId", "subMarkOne", "subMarkTwo" });
      }});
      setFieldSetMapper(new BeanWrapperFieldSetMapper<Student>() {{
        setTargetType(Student.class);
      }});
    }});
    return reader;
  }

  @Bean
  public ItemWriter<Marksheet> itemWriterList() {

    FlatFileItemWriter<Marksheet> flatFileItemWriter = new FlatFileItemWriter<>();
    flatFileItemWriter.setResource(new FileSystemResource("src/main/resources/users.out"));

    DelimitedLineAggregator<Marksheet> delLineAgg = new DelimitedLineAggregator<Marksheet>();
    delLineAgg.setDelimiter(",");

    BeanWrapperFieldExtractor<Marksheet> fieldExtractor = new BeanWrapperFieldExtractor<Marksheet>();
    fieldExtractor.setNames(new String[] {"stdId", "totalSubMark"});
    delLineAgg.setFieldExtractor(fieldExtractor);

    flatFileItemWriter.setLineAggregator(delLineAgg);
    return flatFileItemWriter;
  }

  @Bean
  public ItemProcessor<Student, Marksheet> processorList() {
    return new StudentItemProcessor();
  }


}

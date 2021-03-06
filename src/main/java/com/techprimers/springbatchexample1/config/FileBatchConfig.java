package com.techprimers.springbatchexample1.config;

import com.techprimers.springbatchexample1.batch.StudentItemProcessor;
import com.techprimers.springbatchexample1.model.Marksheet;
import com.techprimers.springbatchexample1.model.Student;
import com.techprimers.springbatchexample1.model.User;
import java.util.Date;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
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
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;

@Configuration
@EnableBatchProcessing
public class FileBatchConfig {
  @Bean
  public Job jobFile(JobBuilderFactory jobBuilderFactory,
                 StepBuilderFactory stepBuilderFactory,
                 ItemReader<Student> reader3,
                 ItemProcessor<Student, Marksheet> processor3,
                 ItemWriter<Marksheet> itemWriter2
  ) {

    Step step = stepBuilderFactory.get("M-file-load")
        .<Student, Marksheet>chunk(100)
        .reader(reader3)
        .processor(processor3)
        .writer(itemWriter2)
        .build();


    return jobBuilderFactory.get("M-Load")
        .incrementer(new RunIdIncrementer())
        .start(step)
        .build();
  }

  /**
   * Это одна из стандартных реализаций ItemReader
   * @return
   */
  @Bean
  public FlatFileItemReader<User> itemReader2() {
    System.out.println("itemReader: Start");

    FlatFileItemReader<User> flatFileItemReader = new FlatFileItemReader<>();
    flatFileItemReader.setResource(new FileSystemResource("src/main/resources/users.csv"));
    flatFileItemReader.setName("CSV-Reader");
    flatFileItemReader.setLinesToSkip(1);
    flatFileItemReader.setLineMapper(lineMapperFile());
    return flatFileItemReader;
  }

  @Bean
  public ItemReader<Student> reader3() {
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
  public LineMapper<User> lineMapperFile() {

    DefaultLineMapper<User> defaultLineMapper = new DefaultLineMapper<>();
    DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();

    lineTokenizer.setDelimiter(",");
    lineTokenizer.setStrict(false);
    lineTokenizer.setNames("stdId", "totalSubMark");

    BeanWrapperFieldSetMapper<User> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
    fieldSetMapper.setTargetType(User.class);

    defaultLineMapper.setLineTokenizer(lineTokenizer);
    defaultLineMapper.setFieldSetMapper(fieldSetMapper);

    return defaultLineMapper;
  }

  @Bean
  public ItemWriter<Marksheet> itemWriter2() {

    FlatFileItemWriter<Marksheet> flatFileItemWriter = new FlatFileItemWriter<>();
    flatFileItemWriter.setResource(new FileSystemResource("src/main/resources/users.out"));

    DelimitedLineAggregator<Marksheet> delLineAgg = new DelimitedLineAggregator<Marksheet>();
    delLineAgg.setDelimiter(",");

    BeanWrapperFieldExtractor<Marksheet> fieldExtractor = new BeanWrapperFieldExtractor<Marksheet>();
    fieldExtractor.setNames(new String[] {"stdId", "totalSubMark"});
    delLineAgg.setFieldExtractor(fieldExtractor);

    flatFileItemWriter.setLineAggregator(delLineAgg);
    return flatFileItemWriter;



//    return  new FlatFileItemWriterBuilder<User>()
//        .name("itemWriter")
//        .resource(new FileSystemResource("target/test-outputs/output.txt"))
//        .lineAggregator(new PassThroughLineAggregator<>())
//        .build();
  }

  @Bean
  public ItemProcessor<Student, Marksheet> processor3() {
    return new StudentItemProcessor();
  }


//  @Bean
//  public ItemProcessor<User, User> processor2() {
//    String deptCode = user.getDept();
//    String dept = DEPT_NAMES.get(deptCode);
//    user.setDept(dept);
//    user.setTime(new Date());
//    System.out.println(String.format("Converted from [%s] to [%s]", deptCode, dept));
//    return user;
//
////    return new PersonItemProcessor();
//  }

}

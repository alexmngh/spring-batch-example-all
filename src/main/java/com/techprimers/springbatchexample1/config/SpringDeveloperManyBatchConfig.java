package com.techprimers.springbatchexample1.config;

import com.techprimers.springbatchexample1.service.EmailCreateService;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.listener.StepExecutionListenerSupport;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Это пример с ютуб канала SpringDeveloper. Создаем одни Job с несколькими Step.
 * Не запускается
 */
@Configuration
@EnableBatchProcessing
public class SpringDeveloperManyBatchConfig {
  private Random random;
  private JobBuilderFactory jobBuilderFactory;
  private StepBuilderFactory stepBuilderFactory;

  @Autowired
  EmailCreateService emailCreateService;

  public SpringDeveloperManyBatchConfig(JobBuilderFactory jobBuilderFactory,
                                    StepBuilderFactory stepBuilderFactory) {
    this.random = new Random();
    this.jobBuilderFactory = jobBuilderFactory;
    this.stepBuilderFactory = stepBuilderFactory;
  }

  @Bean
  public Job jobDeveloperMany() {
    return jobBuilderFactory.get("jobDeveloperMany")
        .start(step1())
        .next(step2())
        .incrementer(new RunIdIncrementer())
        .build();
  }

  @Bean
  public Step step1() {
    return stepBuilderFactory.get("step1")
        .<Integer, Integer>chunk(3)
        .reader(itemReaderDeveloper())
        .writer(itemWriterDeveloper())
        .listener(new StepExecutionListenerSupport() {
          @Override
          public ExitStatus afterStep(StepExecution stepExecution) {
            stepExecution.getJobExecution().getExecutionContext()
                .putInt("test_key", stepExecution.getReadCount());
            return super.afterStep(stepExecution);
          }
        })
        .build();
  }

  @Bean
  public Step step2() {
    return stepBuilderFactory.get("step2")
        .tasklet(new Tasklet() {
          @Override
          public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext)
              throws Exception {
            int readCount = chunkContext.getStepContext().getStepExecution()
            .getJobExecution().getExecutionContext().getInt("readCount");
            System.out.println("readCount = " + readCount);
            return RepeatStatus.FINISHED;
          }
        })
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

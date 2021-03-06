package com.techprimers.springbatchexample1.batch;

import com.techprimers.springbatchexample1.model.Marksheet;
import com.techprimers.springbatchexample1.model.Student;
import com.techprimers.springbatchexample1.model.User;
import java.util.Date;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;

@Slf4j
public class ListItemProcessor implements ItemProcessor<Student, Marksheet> {

  @Override
  public Marksheet process(Student student) throws Exception {

    int totalMark = student.getSubMarkOne() + student.getSubMarkTwo();
    Marksheet marksheet = new Marksheet(student.getStdId(), totalMark);

//    System.out.println(String.format("Converted from [%s] to [%s]", deptCode, dept));
    log.info("ListItemProcessor: process");
    return marksheet;
  }
}

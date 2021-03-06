package com.techprimers.springbatchexample1.batch;

import com.techprimers.springbatchexample1.model.Marksheet;
import com.techprimers.springbatchexample1.model.Student;
import org.springframework.batch.item.ItemProcessor;

public class StudentItemProcessor implements ItemProcessor<Student, Marksheet> {

  @Override
  public Marksheet process(final Student student) throws Exception {
    int totalMark = student.getSubMarkOne()+student.getSubMarkTwo();
    System.out.println("student id:"+student.getStdId() +" and Total mark:"+ totalMark);
    Marksheet marksheet = new Marksheet(student.getStdId(), totalMark);
    return marksheet;
  }

}

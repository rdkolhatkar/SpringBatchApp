package com.ratnakar.project.SpringBatchApp.configuration;

import com.ratnakar.project.SpringBatchApp.listener.FirstJobListener;
import com.ratnakar.project.SpringBatchApp.service.SecondTasklet;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class SampleJob {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    @Autowired
    private StepBuilderFactory stepBuilderFactory;
    @Autowired
    private SecondTasklet secondTasklet;
    @Autowired
    private FirstJobListener firstJobListener;

    /*
    @Bean
    public Job firstJob(){
        return jobBuilderFactory.get("First Job")
                .start(firstStep())
                .next(secondStep())
                .build();
    }
    */
    @Bean
    public Job firstJob(){
        return jobBuilderFactory.get("First Job")
                .incrementer(new RunIdIncrementer())
                .start(firstStep())
                .next(secondStep())
                .listener(firstJobListener)
                .build();
    }

    private Step firstStep(){
       return stepBuilderFactory.get("First Step")
                .tasklet(firstTask())
                .build();
    }

    private Tasklet firstTask(){
        return new Tasklet() {
            @Override
            public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
                System.out.println("This is First Tasklet Step");
                return RepeatStatus.FINISHED;
            }
        };
    }
     /*
    private Step secondStep(){
        return stepBuilderFactory.get("Second Step")
                .tasklet(secondTask())
                .build();
    }
    private Tasklet secondTask(){
        return new Tasklet() {
            @Override
            public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
                System.out.println("This is Second Tasklet Step");
                return RepeatStatus.FINISHED;
            }
        };
    }
     */
     private Step secondStep(){
         return stepBuilderFactory.get("Second Step")
                 .tasklet(secondTasklet)
                 .build();
     }


}

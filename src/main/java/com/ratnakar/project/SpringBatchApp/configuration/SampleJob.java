package com.ratnakar.project.SpringBatchApp.configuration;

import com.ratnakar.project.SpringBatchApp.listener.FirstJobListener;
import com.ratnakar.project.SpringBatchApp.listener.FirstStepListener;
import com.ratnakar.project.SpringBatchApp.model.StudentCsv;
import com.ratnakar.project.SpringBatchApp.model.StudentJson;
import com.ratnakar.project.SpringBatchApp.processor.FirstItemProcessor;
import com.ratnakar.project.SpringBatchApp.reader.FirstItemReader;
import com.ratnakar.project.SpringBatchApp.service.SecondTasklet;
import com.ratnakar.project.SpringBatchApp.writer.FirstItemWriter;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.json.JacksonJsonObjectReader;
import org.springframework.batch.item.json.JsonItemReader;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

import java.io.File;


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
    @Autowired
    private FirstStepListener firstStepListener;
    @Autowired
    private FirstItemReader firstItemReader;
    @Autowired
    private FirstItemProcessor firstItemProcessor;
    @Autowired
    private FirstItemWriter firstItemWriter;

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
               .listener(firstStepListener)
                .tasklet(firstTask())
                .build();
    }

    private Tasklet firstTask(){
        return new Tasklet() {
            @Override
            public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
                System.out.println("This is First Tasklet Step");
                System.out.println("Sec = "+chunkContext.getStepContext().getStepExecutionContext());
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

     /*
     @Bean
     public Job secondJob(){
         return jobBuilderFactory.get("Second Job")
                     .incrementer(new RunIdIncrementer())
                 .start(firstChunkStep())
                 .next(secondStep())
                 .build();

     }
      */

    @Bean
    public Job secondJob(){
        return jobBuilderFactory.get("Second Job")
                .incrementer(new RunIdIncrementer())
                .start(firstChunkStep())
                .next(secondStep())
                .build();

    }

     /*
     private Step firstChunkStep(){
         return stepBuilderFactory.get("First Chunk Step")
                 .<Integer, Long>chunk(3)
                 .reader(firstItemReader)
                 .processor(firstItemProcessor)
                 .writer(firstItemWriter)
                 .build();
     }
     */
    /*
    private Step firstChunkStep(){
        return stepBuilderFactory.get("First Chunk Step")
                .<StudentCsv, StudentCsv>chunk(3)
                .reader(flatFileItemReader())
               // .processor(firstItemProcessor)
                .writer(firstItemWriter)
                .build();
    }
    */
    /*
     private Step firstChunkStep(){
         return stepBuilderFactory.get("First Chunk Step")
                 .<StudentCsv, StudentCsv>chunk(3)
                 .reader(flatFileItemReader(null))
                 // .processor(firstItemProcessor)
                 .writer(firstItemWriter)
                 .build();
     }
     */

    private Step firstChunkStep(){
        return stepBuilderFactory.get("First Chunk Step")
                .<StudentJson, StudentJson>chunk(3)
                .reader(jsonItemReader(null))
                // .processor(firstItemProcessor)
                .writer(firstItemWriter)
                .build();
    }

    /*
     public FlatFileItemReader<StudentCsv> flatFileItemReader(){
         FlatFileItemReader<StudentCsv> flatFileItemReader = new FlatFileItemReader<StudentCsv>();

         flatFileItemReader.setResource(new FileSystemResource(new File("E:\\SpringProject\\SpringBatchApplication\\SpringBatchApp\\InputFiles\\students.csv")));
         flatFileItemReader.setLineMapper(new DefaultLineMapper<StudentCsv>(){
             {
                 setLineTokenizer(new DelimitedLineTokenizer(){
                     {
                         setNames("ID", "First Name", "Last Name", "Email");
                     }
                 });
             setFieldSetMapper(new BeanWrapperFieldSetMapper<StudentCsv>(){
                 {
                     setTargetType(StudentCsv.class);
                 }
             });
             }
         });

        flatFileItemReader.setLinesToSkip(1);
         return flatFileItemReader;
     }
     */

    /*
    public FlatFileItemReader<StudentCsv> flatFileItemReader(){
        FlatFileItemReader<StudentCsv> flatFileItemReader = new FlatFileItemReader<StudentCsv>();

        flatFileItemReader.setResource(new FileSystemResource(new File("E:\\SpringProject\\SpringBatchApplication\\SpringBatchApp\\InputFiles\\StudentList.csv")));
        flatFileItemReader.setLineMapper(new DefaultLineMapper<StudentCsv>(){
            {
                setLineTokenizer(new DelimitedLineTokenizer("|"){
                    {
                        setNames("ID", "First Name", "Last Name", "Email");
                    }
                });
                setFieldSetMapper(new BeanWrapperFieldSetMapper<StudentCsv>(){
                    {
                        setTargetType(StudentCsv.class);
                    }
                });
            }
        });

        flatFileItemReader.setLinesToSkip(1);
        return flatFileItemReader;
    }
    */
    /*
    public FlatFileItemReader<StudentCsv> flatFileItemReader(){
        FlatFileItemReader<StudentCsv> flatFileItemReader = new FlatFileItemReader<StudentCsv>();

        flatFileItemReader.setResource(new FileSystemResource(new File("E:\\SpringProject\\SpringBatchApplication\\SpringBatchApp\\InputFiles\\StudentList.csv")));
        flatFileItemReader.setLineMapper(new DefaultLineMapper<StudentCsv>(){
            {
                setLineTokenizer(new DelimitedLineTokenizer(){
                    {
                        setNames("ID", "First Name", "Last Name", "Email");
                        setDelimiter("|");
                    }
                });
                setFieldSetMapper(new BeanWrapperFieldSetMapper<StudentCsv>(){
                    {
                        setTargetType(StudentCsv.class);
                    }
                });
            }
        });

        flatFileItemReader.setLinesToSkip(1);
        return flatFileItemReader;
    }
    */

    /*
    @StepScope
    @Bean
    public FlatFileItemReader<StudentCsv> flatFileItemReader(@Value("#{jobParameters['inputFile']}") FileSystemResource fileSystemResource){
        FlatFileItemReader<StudentCsv> flatFileItemReader = new FlatFileItemReader<StudentCsv>();

        flatFileItemReader.setResource(fileSystemResource);
        flatFileItemReader.setLineMapper(new DefaultLineMapper<StudentCsv>(){
            {
                setLineTokenizer(new DelimitedLineTokenizer(){
                    {
                        setNames("ID", "First Name", "Last Name", "Email");
                        setDelimiter("|");
                    }
                });
                setFieldSetMapper(new BeanWrapperFieldSetMapper<StudentCsv>(){
                    {
                        setTargetType(StudentCsv.class);
                    }
                });
            }
        });

        flatFileItemReader.setLinesToSkip(1);
        return flatFileItemReader;
    }
    */

    @StepScope
    @Bean
    public JsonItemReader<StudentJson> jsonItemReader(@Value("#{jobParameters['inputFile']}") FileSystemResource fileSystemResource) {
        JsonItemReader<StudentJson> jsonItemReader = new JsonItemReader<StudentJson>();
        jsonItemReader.setResource(fileSystemResource);
        jsonItemReader.setJsonObjectReader(new JacksonJsonObjectReader<>(StudentJson.class));
        jsonItemReader.setMaxItemCount(8); //Reading till 8th value skipping values after number 8
        jsonItemReader.setCurrentItemCount(2); //Reading after skipping first 2 values

    return jsonItemReader;
    }




}

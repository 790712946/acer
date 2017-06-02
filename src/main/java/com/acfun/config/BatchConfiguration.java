package com.acfun.config;

import com.acfun.domain.Person;
import com.acfun.listener.JobCompletionNotificationListener;
import com.acfun.processor.PersonItemProcessor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.batch.core.step.builder.FlowStepBuilder;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import javax.sql.DataSource;

/**
 * Created by jianghong on 2017/3/19.
 */
@Configuration
@EnableBatchProcessing//开启批处理模式
public class BatchConfiguration {
    /**
     * 任务创建工厂
     */
    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    /**
     * 任务步骤工厂
     */
    @Autowired
    private StepBuilderFactory stepBuilderFactory;
    /**
     * 数据源
     */
    @Autowired
    private DataSource dataSource;

    // tag::readerwriterprocessor[]

    /**
     * 从指定的文件读取文件类容，并保存起来放到FlatFileItemReader中
     * 框架支持多种数据读入方式，包括数据库，本地文件，消息流等等，具体可以看ItemStream的实现
     * @return
     */
    @Bean
    public FlatFileItemReader<Person> reader() {
        //构造一个FlatFileItemReader用来读取和暂存文件数据
        FlatFileItemReader<Person> reader = new FlatFileItemReader<Person>();
        //指定文件读取的路径
        reader.setResource(new ClassPathResource("sample-data.csv"));
        //按行读取数据
        reader.setLineMapper(new DefaultLineMapper<Person>() {{
            //指定字符拆分方式，默认为以逗号拆分，可以自定义规则
            setLineTokenizer(new DelimitedLineTokenizer() {{
                //拆分后对应的实体属性
                setNames(new String[]{"firstName", "lastName"});
            }});
            //指定读取到的数据以Person为实体接收（一行一个实体）
            setFieldSetMapper(new BeanWrapperFieldSetMapper<Person>() {{
                setTargetType(Person.class);
            }});
        }});
        return reader;
    }
    //注册为bean
    @Bean
    public PersonItemProcessor processor() {
        return new PersonItemProcessor();
    }

    /**
     * 以JDBC的方式向数据库保存数据，ItemWriter的实现类都可以，包括ampq方式，mybatis，jpa等等。
     * @return
     */
    @Bean
    public JdbcBatchItemWriter<Person> writer() {
        JdbcBatchItemWriter<Person> writer = new JdbcBatchItemWriter<>();
        writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>());
        writer.setSql("INSERT INTO people (first_name, last_name) VALUES (:firstName, :lastName)");
        writer.setDataSource(dataSource);
        return writer;
    }

    // end::readerwriterprocessor[]
    // tag::jobstep[]

    /**
     * 构建一个JOB，并且配置一个监听器监听这个job
     * 通过jobBuilderFactory工厂构建
     * @param listener
     * @return
     */
    @Bean
    public Job importUserJob(JobCompletionNotificationListener listener) {
        //通过方法名来创建一个JobBuilder（虽然可以任务名和bean 名字可以不一致，但是最好取一样的名字）
        Job job= jobBuilderFactory.get("importUserJob")
                //添加一个工作参数增量ID，这里使用了RunIdIncrementer，使用自增ID，可以在日志中看到{run.id=3}，每次任务执行则ID+1
                .incrementer(new RunIdIncrementer())
                //给任务添加一个监听器
                .listener(listener)
                //设置任务执行的步骤（具体内容），可以添加多个步骤
                .flow(step1())
//                .next(step2())//通过调用.next调用下一个步骤
                //构建JOB结束
                .end()
                //构建JOB
                .build();
        return job;
    }

    @Bean
    public Step step1() {
        //通过方法名来构建一个StepBuilder（虽然可以步骤名和bean 名字可以不一致，但是最好取一样的名字）
        Step step=stepBuilderFactory.get("step1")
                //设置缓冲区大小，每次处理多少数据。
                .<Person, Person>chunk(10)
                //设置数据读入方式
                .reader(reader())
                //设置数据读入后的处理方式
                .processor(processor())
                //把数据按照我们的方式写入
                .writer(writer())
                //步骤构建完成
                .build();
        return step;
    }
}

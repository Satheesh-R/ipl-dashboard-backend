package com.practice.ipldashboard.config;

import javax.sql.DataSource;

import com.practice.ipldashboard.constant.BatchProcessingConstants;
import com.practice.ipldashboard.data.JobCompletionNotificationListener;
import com.practice.ipldashboard.data.MatchDataProcessor;
import com.practice.ipldashboard.model.MatchData;
import com.practice.ipldashboard.model.MatchInput;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    public FlatFileItemReader<MatchInput> reader() {
        return new FlatFileItemReaderBuilder<MatchInput>().name("MatchItemReader")
                .resource(new ClassPathResource("match_data.csv")).delimited()
                .names(BatchProcessingConstants.FIELD_NAMES)
                .fieldSetMapper(new BeanWrapperFieldSetMapper<MatchInput>() {
                    {
                        setTargetType(MatchInput.class);
                    }
                }).build();
    }

    @Bean
    public MatchDataProcessor matchDataProcessor() {
        return new MatchDataProcessor();
    }

    @Bean
    public JdbcBatchItemWriter<MatchData> writer(DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<MatchData>()
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
                .sql("INSERT INTO match_data (id, city, date, player_of_match, venue,neutral_venue, team1, team2, toss_winner, toss_decision, match_winner, result, result_margin, umpire1, umpire2) "
                        + " VALUES (:id, :city, :date, :playerOfMatch, :venue, :neutralVenue, :team1, :team2, :tossWinner, :tossDecision, :matchWinner, :result, :resultMargin, :umpire1, :umpire2)")
                .dataSource(dataSource).build();
    }

    @Bean
    public Job importUserJob(JobCompletionNotificationListener listener,Step step1){
        return jobBuilderFactory.get("importUserJob")
               .incrementer(new RunIdIncrementer())
               .listener(listener)
               .flow(step1)
               .end()
               .build();
    }

    @Bean
    public Step step1(JdbcBatchItemWriter<MatchData> writer){
        return stepBuilderFactory.get("step1")
               .<MatchInput,MatchData> chunk(10)
               .reader(reader())
               .processor(matchDataProcessor())
               .writer(writer)
               .build();
    }
}
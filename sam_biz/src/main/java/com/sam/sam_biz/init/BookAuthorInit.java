package com.sam.sam_biz.init;

import com.google.common.collect.Lists;
import com.sam.sam_biz.db.entity.Author;
import com.sam.sam_biz.db.entity.Book;
import com.sam.sam_biz.db.repository.AuthorRepository;
import com.sam.sam_biz.db.repository.BookRepository;
import com.sam.sap_commons.redis.RedisCacheHelper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Slf4j
@Component
public class BookAuthorInit implements ApplicationRunner {

    @Resource
    private BookRepository bookRepository;
    @Resource
    private AuthorRepository authorRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        List<Book> bookList = Lists.newArrayList();
        List<Author> authorList = Lists.newArrayList();
        for (int i = 0; i < 10; i++) {
            Author author = Author.builder()
                    .authorId(RedisCacheHelper.newKey("author"))
                    .name(RedisCacheHelper.newKey("name"))
                    .nationality("PRC")
                    .birthday("19860918")
                    .build();
            authorList.add(author);
        }
        authorList.forEach(author -> {
            int counter = ThreadLocalRandom.current().nextInt(3, 20);
            log.info("author ({}) has {} books", author.getName(), counter);
            for (int i = 0; i < counter; i++) {
                Book book = Book.builder()
                        .authorId(author.getAuthorId())
                        .bookId(RedisCacheHelper.newKey("book"))
                        .title(RedisCacheHelper.newKey("title"))
                        .desc(RedisCacheHelper.newKey("desc"))
                        .isbn(RedisCacheHelper.newKey("isbn"))
                        .price(ThreadLocalRandom.current().nextDouble(100,10000))
                        .pageCount(ThreadLocalRandom.current().nextInt(100, 200))
                        .publishYear(String.valueOf(ThreadLocalRandom.current().nextInt(1949,1999)))
                        .build();
                bookList.add(book);
            }

        });
//        this.authorRepository.saveAll(authorList);
//        this.bookRepository.saveAll(bookList);
    }
}

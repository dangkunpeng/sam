package com.sam.sam_biz.db.entity;

import com.sam.sam_biz.db.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "book")
public class Book extends BaseEntity {
    @Id
    private String bookId;
    /**
     * 书名
     */
    @Column
    private String title;
    /**
     * 作者
     */
    @Column
    private String authorId;
    /**
     * ISBN号
     */
    @Column
    private String isbn;
    /**
     * 出版年份
     */
    @Column
    private String publishYear;
    /**
     * 价格
     */
    @Column
    private double price;
    /**
     * 页数
     */
    @Column
    private int pageCount;

    @Column
    private String desc;
}

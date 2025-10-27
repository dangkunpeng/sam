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

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "author")
public class Author extends BaseEntity {
    @Id
    private String authorId;
    /**
     * 作者姓名
     */
    @Column
    private String name;
    /**
     * 国籍
     */
    @Column
    private String nationality;
    /**
     * 出生年份
     */
    @Column
    private String birthday;
    /**
     * 作者简介
     */
    @Column
    private String biography;
    /**
     * 擅长写作的体裁
     */
    @Column
    private List<String> genres;
}

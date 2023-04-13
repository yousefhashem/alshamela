package com.dzyacode.almaktabatalshaamila.models;

public class Book {
    private String book_author;
    private String book_category;
    private String book_description;
    private String book_first_batch;
    private String book_name;
    private String book_pages;
    private String book_publisher;
    private String book_short_description;
    private String book_url;

    public Book() {}

    public Book(String book_author, String book_category, String book_description, String book_first_batch, String book_name, String book_pages, String book_publisher, String book_short_description, String book_url) {
        this.book_author = book_author;
        this.book_category = book_category;
        this.book_description = book_description;
        this.book_first_batch = book_first_batch;
        this.book_name = book_name;
        this.book_pages = book_pages;
        this.book_publisher = book_publisher;
        this.book_short_description = book_short_description;
        this.book_url = book_url;
    }

    public String getBook_author() {
        return book_author;
    }

    public void setBook_author(String book_author) {
        this.book_author = book_author;
    }

    public String getBook_category() {
        return book_category;
    }

    public void setBook_category(String book_category) {
        this.book_category = book_category;
    }

    public String getBook_description() {
        return book_description;
    }

    public void setBook_description(String book_description) {
        this.book_description = book_description;
    }

    public String getBook_first_batch() {
        return book_first_batch;
    }

    public void setBook_first_batch(String book_first_batch) {
        this.book_first_batch = book_first_batch;
    }

    public String getBook_name() {
        return book_name;
    }

    public void setBook_name(String book_name) {
        this.book_name = book_name;
    }

    public String getBook_pages() {
        return book_pages;
    }

    public void setBook_pages(String book_pages) {
        this.book_pages = book_pages;
    }

    public String getBook_publisher() {
        return book_publisher;
    }

    public void setBook_publisher(String book_publisher) {
        this.book_publisher = book_publisher;
    }

    public String getBook_short_description() {
        return book_short_description;
    }

    public void setBook_short_description(String book_short_description) {
        this.book_short_description = book_short_description;
    }

    public String getBook_url() {
        return book_url;
    }

    public void setBook_url(String book_url) {
        this.book_url = book_url;
    }
}

package ru.igorryb.spring.data.jdbc.demo.dtos;

import java.util.List;

public class PageDto {

    private List<DetailedBookDto> detailedBookDtos;

    private int pageSize;
    private int pageNumber;


    public List<DetailedBookDto> getDetailedBookDtos() {
        return detailedBookDtos;
    }

    public void setDetailedBookDtos(List<DetailedBookDto> detailedBookDtos) {
        this.detailedBookDtos = detailedBookDtos;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public PageDto() {
    }

    public PageDto(List<DetailedBookDto> detailedBookDtos, int pageSize, int pageNumber) {
        this.detailedBookDtos = detailedBookDtos;
        this.pageSize = pageSize;
        this.pageNumber = pageNumber;
    }
}

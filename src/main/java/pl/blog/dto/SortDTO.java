package pl.blog.dto;

public class SortDTO {
    private Integer sort;

    public SortDTO(Integer sort) {
        this.sort = sort;
    }

    public SortDTO() {
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }
}

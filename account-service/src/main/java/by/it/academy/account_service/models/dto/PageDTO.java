package by.it.academy.account_service.models.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Objects;

public class PageDTO<T> {

    @JsonProperty("number")
    private int number;
    @JsonProperty("size")
    private int size;
    @JsonProperty("total_pages")
    private int totalPages;
    @JsonProperty("total_elements")
    private int totalElements;
    @JsonProperty("first")
    private boolean first;
    @JsonProperty("number_of_elements")
    private int numberOfElements;
    @JsonProperty("last")
    private boolean last;
    @JsonProperty("content")
    private List<T> content;

    public PageDTO(int number, int size, int totalPages, int totalElements, boolean first, int numberOfElements,
                   boolean last, List<T> content) {
        this.number = number;
        this.size = size;
        this.totalPages = totalPages;
        this.totalElements = totalElements;
        this.first = first;
        this.numberOfElements = numberOfElements;
        this.last = last;
        this.content = content;
    }

    public PageDTO() {
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(int totalElements) {
        this.totalElements = totalElements;
    }

    public boolean isFirst() {
        return first;
    }

    public void setFirst(boolean first) {
        this.first = first;
    }

    public int getNumberOfElements() {
        return numberOfElements;
    }

    public void setNumberOfElements(int numberOfElements) {
        this.numberOfElements = numberOfElements;
    }

    public boolean isLast() {
        return last;
    }

    public void setLast(boolean last) {
        this.last = last;
    }

    public List<T> getContent() {
        return content;
    }

    public void setContent(List<T> content) {
        this.content = content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PageDTO that = (PageDTO) o;
        return number == that.number
                && size == that.size
                && totalPages == that.totalPages
                && totalElements == that.totalElements
                && first == that.first
                && numberOfElements == that.numberOfElements
                && last == that.last
                && Objects.equals(content, that.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number, size, totalPages, totalElements, first, numberOfElements, last, content);
    }

    @Override
    public String toString() {
        return "PageOfAccount{" +
                "number=" + number +
                ", size=" + size +
                ", totalPages=" + totalPages +
                ", totalElements=" + totalElements +
                ", first=" + first +
                ", numberOfElements=" + numberOfElements +
                ", last=" + last +
                ", accounts=" + content +
                '}';
    }

    public static class Builder<T> {

        private int number;
        private int size;
        private int totalPages;
        private int totalElements;
        private boolean first;
        private int numberOfElements;
        private boolean last;
        private List<T> content;

        private Builder() {
        }

        public static <E> Builder<E> createBuilder(Class<E> eClass) {
            return new Builder<>();
        }

        public Builder<T> setNumber(int number) {
            this.number = number;
            return this;
        }

        public Builder<T> setSize(int size) {
            this.size = size;
            return this;
        }

        public Builder<T> setTotalPages(int totalPages) {
            this.totalPages = totalPages;
            return this;
        }

        public Builder<T> setTotalElements(int totalElements) {
            this.totalElements = totalElements;
            return this;
        }

        public Builder<T> setFirst(boolean first) {
            this.first = first;
            return this;
        }

        public Builder<T> setNumberOfElements(int numberOfElements) {
            this.numberOfElements = numberOfElements;
            return this;
        }

        public Builder<T> setLast(boolean last) {
            this.last = last;
            return this;
        }

        public Builder<T> setContent(List<T> content) {
            this.content = content;
            return this;
        }

        public PageDTO<T> build() {
            return new PageDTO<>(this.number, this.size, this.totalPages, this.totalElements, this.first,
                    this.numberOfElements, this.last, this.content);
        }
    }

}

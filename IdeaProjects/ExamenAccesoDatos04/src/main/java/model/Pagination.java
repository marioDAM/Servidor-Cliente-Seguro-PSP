package model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Pagination {
    public int total;
    public int pages;
    public int page;
    public int limit;
    public Links links;
}

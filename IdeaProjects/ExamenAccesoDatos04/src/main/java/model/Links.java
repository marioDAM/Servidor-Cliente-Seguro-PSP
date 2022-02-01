package model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Links {
    public Object previous;
    public String current;
    public String next;
}

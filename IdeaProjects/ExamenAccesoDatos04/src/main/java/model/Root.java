package model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
@Data
@AllArgsConstructor
public class Root {

    public Meta meta;
    public ArrayList<Users> data;
}

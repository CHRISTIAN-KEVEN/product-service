package com.example.productService.models;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;

@Entity(name = "t_item")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long lgId;

    @NotNull
    @Column(name = "str_name")
    private String strName;
    private double dbPrice;
    private int quantity;

    private String lgBusinessId;
    private String strImageUrl;

}

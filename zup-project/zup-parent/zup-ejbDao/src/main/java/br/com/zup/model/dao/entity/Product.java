package br.com.zup.model.dao.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the TB_PRODUCT database table.
 * 
 */
@Entity
@Table(name="TB_PRODUCT")
public class Product implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name="TB_PRODUCT_IDPRODUCT_GENERATOR", sequenceName="SQ_PRODUCT", allocationSize=1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TB_PRODUCT_IDPRODUCT_GENERATOR")
    @Column(name="ID_PRODUCT", unique=true, nullable=false, precision=11)
    private Long idProduct;

    @Column(name="DS_DESCRIPTION", nullable=false, length=1000)
    private String dsDescription;

    @Column(name="NM_CATEGORY", nullable=false, length=150)
    private String nmCategory;

    @Column(name="NM_NAME", nullable=false, length=150)
    private String nmName;

    @Column(name="VL_PRICE", nullable=false, precision=11)
    private Long vlPrice;

    public Product() {
    }

    public Long getIdProduct() {
        return this.idProduct;
    }

    public void setIdProduct(Long idProduct) {
        this.idProduct = idProduct;
    }

    public String getDsDescription() {
        return this.dsDescription;
    }

    public void setDsDescription(String dsDescription) {
        this.dsDescription = dsDescription;
    }

    public String getNmCategory() {
        return this.nmCategory;
    }

    public void setNmCategory(String nmCategory) {
        this.nmCategory = nmCategory;
    }

    public String getNmName() {
        return this.nmName;
    }

    public void setNmName(String nmName) {
        this.nmName = nmName;
    }

    public Long getVlPrice() {
        return this.vlPrice;
    }

    public void setVlPrice(Long vlPrice) {
        this.vlPrice = vlPrice;
    }

}
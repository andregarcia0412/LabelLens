package com.example.labellens;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class NutrientDetail {
    @JsonProperty("por_100g")
    private String por100g;
    @JsonProperty("por_porcao")
    private String porPorcao;
    @JsonProperty("vd_porcao")
    private String vdPorcao;

    public String getPor100g() {
        return por100g;
    }

    public void setPor100g(String por100g) {
        this.por100g = por100g;
    }

    public String getPorPorcao() {
        return porPorcao;
    }

    public void setPorPorcao(String porPorcao) {
        this.porPorcao = porPorcao;
    }

    public String getVdPorcao() {
        return vdPorcao;
    }

    public void setVdPorcao(String vdPorcao) {
        this.vdPorcao = vdPorcao;
    }

    @Override
    public String toString() {
        return "NutrientDetail{" +
                "por100g='" + por100g + '\'' +
                ", porPorcao='" + porPorcao + '\'' +
                ", vdPorcao='" + vdPorcao + '\'' +
                '}';
    }
}

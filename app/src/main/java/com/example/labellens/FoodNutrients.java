package com.example.labellens;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FoodNutrients {

    @JsonProperty("Acucares adicionados (g)")
    private NutrientDetail acucaresAdicionados;
    @JsonProperty("Acucares totais")
    private NutrientDetail acucaresTotais;
    @JsonProperty("Calcio (mg)")
    private NutrientDetail calcio;
    @JsonProperty("Carboidratos (g)")
    private NutrientDetail carboidratos;
    @JsonProperty("Fibras alimentares (g)")
    private NutrientDetail fibrasAlimentares;
    @JsonProperty("Gorduras saturadas (g)")
    private NutrientDetail gordurasSaturadas;
    @JsonProperty("Gorduras totais (g)")
    private NutrientDetail gordurasTotais;
    @JsonProperty("Gorduras trans (g)")
    private NutrientDetail gordurasTrans;
    @JsonProperty("Proteinas (g)")
    private NutrientDetail proteinas;
    @JsonProperty("Valor energetico (kcal)")
    private NutrientDetail valorEnergetico;
    @JsonProperty("Sodio (mg)")
    private NutrientDetail sodio;

    public NutrientDetail getAcucaresAdicionados() {
        return acucaresAdicionados;
    }

    public void setAcucaresAdicionados(NutrientDetail acucaresAdicionados) {
        this.acucaresAdicionados = acucaresAdicionados;
    }

    public NutrientDetail getAcucaresTotais() {
        return acucaresTotais;
    }

    public void setAcucaresTotais(NutrientDetail acucaresTotais) {
        this.acucaresTotais = acucaresTotais;
    }

    public NutrientDetail getCalcio() {
        return calcio;
    }

    public void setCalcio(NutrientDetail calcio) {
        this.calcio = calcio;
    }

    public NutrientDetail getCarboidratos() {
        return carboidratos;
    }

    public void setCarboidratos(NutrientDetail carboidratos) {
        this.carboidratos = carboidratos;
    }

    public NutrientDetail getFibrasAlimentares() {
        return fibrasAlimentares;
    }

    public void setFibrasAlimentares(NutrientDetail fibrasAlimentares) {
        this.fibrasAlimentares = fibrasAlimentares;
    }

    public NutrientDetail getGordurasSaturadas() {
        return gordurasSaturadas;
    }

    public void setGordurasSaturadas(NutrientDetail gordurasSaturadas) {
        this.gordurasSaturadas = gordurasSaturadas;
    }

    public NutrientDetail getGordurasTotais() {
        return gordurasTotais;
    }

    public void setGordurasTotais(NutrientDetail gordurasTotais) {
        this.gordurasTotais = gordurasTotais;
    }

    public NutrientDetail getGordurasTrans() {
        return gordurasTrans;
    }

    public void setGordurasTrans(NutrientDetail gordurasTrans) {
        this.gordurasTrans = gordurasTrans;
    }

    public NutrientDetail getProteinas() {
        return proteinas;
    }

    public void setProteinas(NutrientDetail proteinas) {
        this.proteinas = proteinas;
    }

    public NutrientDetail getValorEnergetico() {
        return valorEnergetico;
    }

    public void setValorEnergetico(NutrientDetail valorEnergetico) {
        this.valorEnergetico = valorEnergetico;
    }

    public NutrientDetail getSodio() {
        return sodio;
    }

    public void setSodio(NutrientDetail sodio) {
        this.sodio = sodio;
    }

    @Override
    public String toString() {
        return "FoodNutrients{" +
                "acucaresAdicionados=" + acucaresAdicionados +
                ", acucaresTotais=" + acucaresTotais +
                ", calcio=" + calcio +
                ", carboidratos=" + carboidratos +
                ", fibrasAlimentares=" + fibrasAlimentares +
                ", gordurasSaturadas=" + gordurasSaturadas +
                ", gordurasTotais=" + gordurasTotais +
                ", gordurasTrans=" + gordurasTrans +
                ", proteinas=" + proteinas +
                ", valorEnergetico=" + valorEnergetico +
                ", sodio=" + sodio +
                '}';
    }
}


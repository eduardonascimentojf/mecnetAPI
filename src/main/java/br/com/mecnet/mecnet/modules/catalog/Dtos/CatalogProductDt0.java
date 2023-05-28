package br.com.mecnet.mecnet.modules.catalog.Dtos;

import java.util.ArrayList;

public record CatalogProductDt0(String name, String description, Float price, String brand, String manufacturer, Integer stock, ArrayList<String> image) {
}
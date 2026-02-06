package com.renault.gestionAuto.api.dto;

import com.renault.gestionAuto.domain.enums.AccessoryType;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public class AccessoryDto {
    private Long id;
    @NotBlank
    private String name;
    @NotBlank
    private String description;
    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal price;
    @NotNull
    private AccessoryType type;
    private Long vehicleId;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }
    public AccessoryType getType() { return type; }
    public void setType(AccessoryType type) { this.type = type; }
    public Long getVehicleId() { return vehicleId; }
    public void setVehicleId(Long vehicleId) { this.vehicleId = vehicleId; }
}

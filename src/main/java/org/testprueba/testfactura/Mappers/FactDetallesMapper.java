package org.testprueba.testfactura.Mappers;

import org.mapstruct.Mapper;
import org.testprueba.testfactura.DTO.FactDetalleDto;
import org.testprueba.testfactura.Entity.FactDetalle;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FactDetallesMapper {

    FactDetalleDto toDto(FactDetalle entity);

    FactDetalle toEntity(FactDetalleDto dto);

    List<FactDetalleDto> toDto(List<FactDetalle> entities);

    List<FactDetalle> toEntity(List<FactDetalleDto> dtos);

}

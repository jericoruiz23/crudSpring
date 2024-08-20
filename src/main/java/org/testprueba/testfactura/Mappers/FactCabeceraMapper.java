package org.testprueba.testfactura.Mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.testprueba.testfactura.DTO.FactCabeceraDto;
import org.testprueba.testfactura.DTO.FactDetalleDto;
import org.testprueba.testfactura.Entity.FactCabecera;
import org.testprueba.testfactura.Entity.FactDetalle;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FactCabeceraMapper {


    @Mapping(target = "detalles", source = "detalles")
    FactCabeceraDto toDto(FactCabecera entity);

    @Mapping(target = "detalles", source = "detalles")
    FactCabecera toEntity(FactCabeceraDto dto);

    FactDetalleDto toDto(FactDetalle entity);
    FactDetalle toEntity(FactDetalleDto dto);

    List<FactCabeceraDto> toDtoList(List<FactCabecera> detalles);
    List<FactCabecera> toEntityList(List<FactCabeceraDto> detallesDto);

}

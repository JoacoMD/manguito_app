package app.manguito.backend.services.impl;

import app.manguito.backend.EstadoPago;
import app.manguito.backend.dto.DonacionesDTO;
import app.manguito.backend.dto.EmprendimientoDTO;
import app.manguito.backend.entities.Emprendimiento;
import app.manguito.backend.entities.Suscripcion;
import app.manguito.backend.entities.TransaccionManguito;
import app.manguito.backend.mappers.EmprendimientoMapper;
import app.manguito.backend.mappers.TransaccionMapper;
import app.manguito.backend.repositories.EmprendimientoRepository;
import app.manguito.backend.repositories.SuscripcionRepository;
import app.manguito.backend.repositories.TransaccionManguitoRepository;
import app.manguito.backend.services.EmprendimientoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmprendimientoServiceImpl implements EmprendimientoService {

    @Autowired
    private EmprendimientoRepository emprendimientoRepository;

    @Autowired
    private TransaccionManguitoRepository manguitoRepository;

    @Autowired
    private SuscripcionRepository suscripcionRepository;

    @Autowired
    private EmprendimientoMapper emprendimientoMapper;

    @Autowired
    private TransaccionMapper transaccionMapper;

    @Override
    public EmprendimientoDTO findEmprendimientoByUrl(String url) {
        return emprendimientoMapper.toDTO(emprendimientoRepository.findByUrl(url));
    }

    @Override
    public Emprendimiento saveEmprendimiento(EmprendimientoDTO dto) {
        return emprendimientoRepository.save(emprendimientoMapper.toEntity(dto));
    }

    @Override
    public EmprendimientoDTO updateEmprendimiento(EmprendimientoDTO dto) {
        Emprendimiento emprendimiento = emprendimientoRepository.findByUrl(dto.getUrl());
        if (emprendimiento == null) return null;
        emprendimiento = emprendimientoMapper.update(emprendimiento, dto);
        emprendimiento = emprendimientoRepository.save(emprendimiento);
        return emprendimientoMapper.toDTO(emprendimiento);
    }

    @Override
    public DonacionesDTO getDonacionesByEmprendimientoUrl(String emprendimientoUrl) {
        if (!emprendimientoRepository.existsByUrl(emprendimientoUrl)) return null;

        List<TransaccionManguito> manguitos = manguitoRepository.findAllByDestinatario_UrlAndEstado(emprendimientoUrl, EstadoPago.APROBADO.getCodigo());
        List<Suscripcion> suscripciones = suscripcionRepository.findAllByDestinatario_UrlAndEstado(emprendimientoUrl, EstadoPago.APROBADO.getCodigo());
        DonacionesDTO dto = new DonacionesDTO();
        dto.setManguitos(transaccionMapper.toManguitoDTOList(manguitos));
        dto.setSuscripciones(transaccionMapper.toSuscripcionDTOList(suscripciones));
        return dto;
    }
}

package app.manguito.backend.services.impl;

import app.manguito.backend.dto.DonacionesDTO;
import app.manguito.backend.dto.EmprendimientoDTO;
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

    @Override
    public EmprendimientoDTO findEmprendimientoByUrl(String url) {
        return EmprendimientoMapper.INSTANCE.toDTO(emprendimientoRepository.findByUrl(url));
    }

    @Override
    public DonacionesDTO getDonacionesByEmprendimientoUrl(String emprendimientoUrl) {
        if (!emprendimientoRepository.existsByUrl(emprendimientoUrl)) return null;

        List<TransaccionManguito> manguitos = manguitoRepository.findAllByDestinatario_Url(emprendimientoUrl);
        List<Suscripcion> suscripciones = suscripcionRepository.findAllByDestinatario_Url(emprendimientoUrl);
        DonacionesDTO dto = new DonacionesDTO();
        dto.setManguitos(TransaccionMapper.INSTANCE.toManguitoDTOList(manguitos));
        dto.setSuscripciones(TransaccionMapper.INSTANCE.toSuscripcionDTOList(suscripciones));
        return dto;
    }
}

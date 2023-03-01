package app.manguito.backend.services.impl;

import app.manguito.backend.EstadoPago;
import app.manguito.backend.dto.DonacionesDTO;
import app.manguito.backend.dto.EmprendimientoDTO;
import app.manguito.backend.dto.UpdateEmprendimientoDTO;
import app.manguito.backend.entities.Emprendimiento;
import app.manguito.backend.entities.Suscripcion;
import app.manguito.backend.entities.TransaccionManguito;
import app.manguito.backend.entities.Usuario;
import app.manguito.backend.mappers.EmprendimientoMapper;
import app.manguito.backend.mappers.TransaccionMapper;
import app.manguito.backend.repositories.EmprendimientoRepository;
import app.manguito.backend.repositories.SuscripcionRepository;
import app.manguito.backend.repositories.TransaccionManguitoRepository;
import app.manguito.backend.repositories.UsuarioRepository;
import app.manguito.backend.services.EmprendimientoService;
import app.manguito.backend.services.R2Service;
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
    private UsuarioRepository usuarioRepository;

    @Autowired
    private EmprendimientoMapper emprendimientoMapper;

    @Autowired
    private TransaccionMapper transaccionMapper;

    @Autowired
    private R2Service r2Service;

    @Override
    public List<EmprendimientoDTO> findEmprendimientos() {
        return emprendimientoMapper.toDTOList(emprendimientoRepository.findAll());
    }

    @Override
    public EmprendimientoDTO findEmprendimientoByUrl(String url) {
        return emprendimientoMapper.toDTO(emprendimientoRepository.findByUrl(url));
    }

    @Override
    public EmprendimientoDTO findEmprendimientoByMail(String mail) {
        Usuario usuario = usuarioRepository.findByMail(mail).orElse(null);
        if (usuario == null) return null;
        return emprendimientoMapper.toDTO(usuario.getEmprendimiento());
    }

    @Override
    public Emprendimiento saveEmprendimiento(EmprendimientoDTO dto) {
        return emprendimientoRepository.save(emprendimientoMapper.toEntity(dto));
    }

    @Override
    public EmprendimientoDTO updateEmprendimiento(UpdateEmprendimientoDTO dto) {
        Emprendimiento emprendimiento = emprendimientoRepository.findByUrl(dto.getUrl());
        if (emprendimiento == null) return null;
        emprendimiento = emprendimientoMapper.update(emprendimiento, dto);
        if (dto.getNewBanner() != null) {
            emprendimiento.setBanner(r2Service.saveImage(dto.getNewBanner()));
        }
        if (dto.getNewImagenPerfil() != null) {
            emprendimiento.setImagenPerfil(r2Service.saveImage(dto.getNewImagenPerfil()));
        }
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
